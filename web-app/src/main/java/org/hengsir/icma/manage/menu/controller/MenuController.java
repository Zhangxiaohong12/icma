package org.hengsir.icma.manage.menu.controller;

import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.LeftMenuDao;
import org.hengsir.icma.entity.LeftMenu;
import org.hengsir.icma.service.LeftMenuService;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hengsir
 * @date 2018/3/14 下午3:56
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private LeftMenuDao leftMenuDao;

    @Autowired
    private LeftMenuService leftMenuService;

    //存放子菜单的集合
    static List<LeftMenu> childs = new ArrayList<>();

    /**
     * 分页查询菜单。
     *
     * @param leftMenu 菜单参数
     * @param index    当前页数
     * @param size     每页显示的数据量
     * @return 按条件查询出的菜单视图
     */
    @RequestMapping(value = "/search")
    @RequiresPermissions("lMenu_search")
    public ModelAndView search(
            LeftMenu leftMenu, @RequestParam(value = "pageNum", defaultValue = "1") int index,
            @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        Page<LeftMenu> page = leftMenuDao.findLeftMenusBy(leftMenu, new Page<>(index, size));
        List<LeftMenu> list = page.getResult();

        List<LeftMenu> leftMenuSurperCodesList = leftMenuDao.findFatherMenu();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/system-data/menu-value-list");

        modelAndView.addObject("leftMenuSurperCodes", leftMenuSurperCodesList);
        if (null != leftMenu.getMenuSuperCode() && !"".equals(leftMenu.getMenuSuperCode())) {
            modelAndView.addObject("superCode", leftMenu.getMenuSuperCode());
        }

        modelAndView.addObject("leftMenus", list);
        modelAndView.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        return modelAndView;
    }

    /**
     * 对菜单编码的唯一性验证。
     *
     * @param code 菜单编码
     * @return json数据
     */
    @RequestMapping(value = "/valid-left-menu-Code", method = {RequestMethod.POST})
    @ResponseBody
    public Object validLeftMenuCode(@Param("code") String code) {
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        List<LeftMenu> list = leftMenuDao.findMenuByCodeExit(code);
        if (list.size() > 0) {
            flag = true;
        }
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 通过菜单对象的编码字段查询对应的权限。
     *
     * @param code 编码
     * @return json数据
     *//*
    @RequestMapping(value = "/find-left-menu-sysApp", method = {RequestMethod.POST})
    @ResponseBody
    public Object findLeftMenuSysId(@Param("code") String code) {
        JSONObject jsonObject = new JSONObject();
        LeftMenu leftMenu = leftMenuDao.findLeftMenuBySuperCode(code);
        SysApp sysApp = sysAppDao.findById(leftMenu.getSysId());
        jsonObject.accumulate("id", sysApp.getId());
        return jsonObject;
    }
*/

    /**
     * 递归查询所有子菜单。
     *
     * @param leftMenus 菜单列表
     * @return List 返回子菜单
     */
    public List<LeftMenu> findAllSonMenus(List<LeftMenu> leftMenus) {
        List<LeftMenu> childList = new ArrayList<>();
        for (LeftMenu menu : leftMenus) {
            // 遍历所有节点，将所有子菜单添加到集合中
            childList.add(menu);
        }
        // 把子菜单的子菜单再循环一遍
        for (LeftMenu menu : childList) {
            List<LeftMenu> sonMenus = leftMenuDao.findSonMenus(menu.getMenuCode());
            //将查询出来的子菜单放入集合中存储
            childs.addAll(sonMenus);
            this.findAllSonMenus(sonMenus);
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childs;
    }

    /**
     * 获取菜单树父节点。
     *
     * @return 树列表
     */
    @ResponseBody
    @RequestMapping("/getMenuTree")
    public List<Map<String, Object>> getMenuTree() {
        List<LeftMenu> menuList = leftMenuDao.findAllLoseHome();
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        //查询出系统作为根节点
        map.put("id", "0000");
        map.put("code", "icma");
        map.put("text", "ICMA平台");
        map.put("state", "close");
        //map.put("leftMenu", leftMenu);
        this.getFirstTree(map, menuList);
        list.add(map);


        return list;
    }

    /**
     * 获取菜单树的叶子节点。
     *
     * @param map      父节点对象
     * @param menuList 整个菜单列表
     * @return 子节点对象
     */
    private Map<String, Object> getSonTree(Map<String, Object> map, List<LeftMenu> menuList) {
        List<Map<String, Object>> sonList = new ArrayList<Map<String, Object>>();
        Map<String, Object> sonMap = null;
        for (LeftMenu leftMenu : menuList) {
            if (map.get("code").toString().equals(leftMenu.getMenuSuperCode())) {
                sonMap = new HashMap<String, Object>();
                sonMap.put("id", leftMenu.getMenuId());
                sonMap.put("code", leftMenu.getMenuCode());
                sonMap.put("text", leftMenu.getMenuName());
                sonMap.put("leftMenu", leftMenu);
                sonList.add(sonMap);
                this.getSonTree(sonMap, menuList);
            }
        }
        map.put("children", sonList);
        return map;
    }

    /**
     * 获取系统下第一级菜单树的叶子节点。
     *
     * @param map      父节点对象
     * @param menuList 整个菜单列表
     * @return 子节点对象
     */
    private Map<String, Object> getFirstTree(Map<String, Object> map, List<LeftMenu> menuList) {
        List<Map<String, Object>> sonList = new ArrayList<Map<String, Object>>();
        Map<String, Object> sonMap = null;
        for (LeftMenu leftMenu : menuList) {
            if (map.get("id").equals("0000")) {

                if (leftMenu.getMenuSuperCode() == null || "".equals(leftMenu.getMenuSuperCode())) {
                    sonMap = new HashMap<String, Object>();
                    sonMap.put("id", leftMenu.getMenuId());
                    sonMap.put("code", leftMenu.getMenuCode());
                    sonMap.put("text", leftMenu.getMenuName());
                    sonMap.put("leftMenu", leftMenu);
                    sonList.add(sonMap);
                    this.getSonTree(sonMap, menuList);
                }
            }
        }
        map.put("children", sonList);
        return map;
    }

}
