package org.hengsir.icma.manage.menu.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.hengsir.icma.dao.LeftMenuDao;
import org.hengsir.icma.dao.RightDao;
import org.hengsir.icma.entity.LeftMenu;
import org.hengsir.icma.entity.LeftMenuVo;
import org.hengsir.icma.entity.Right;
import org.hengsir.icma.service.LeftMenuService;
import org.hengsir.icma.service.RightRoleRelationService;
import org.hengsir.icma.service.RightService;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    @Autowired
    private RightService rightService;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private RightRoleRelationService rightRoleRelationService;

    //存放子菜单的集合
    static List<LeftMenu> childs = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(MenuController.class);

    /**
     * 分页查询菜单。
     *
     * @param leftMenu 菜单参数
     * @param index    当前页数
     * @param size     每页显示的数据量
     * @return 按条件查询出的菜单视图
     */
    @RequestMapping(value = "/search")
    @RequiresPermissions("menu:search")
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

    /**
     * 查询菜单增加页面所需要的条件。
     *
     * @return 需要的条件视图
     */
    @RequestMapping(value = "/toadd-left-menu", method = {RequestMethod.GET})
    @RequiresPermissions("menu:add")
    public ModelAndView toaddLeftMenu() {
        List<LeftMenu> leftMenusList = leftMenuDao.findFatherMenu();

        ModelAndView modelAndView = new ModelAndView();
        List<LeftMenuVo> leftMenus = new ArrayList<LeftMenuVo>();
        for (LeftMenu menu : leftMenusList) {
            LeftMenuVo vo = new LeftMenuVo();
            BeanUtils.copyProperties(menu, vo);
            leftMenus.add(vo);
        }
        modelAndView.addObject("leftMenus", leftMenus);
        modelAndView.setViewName("/system-data/menu-value-add");
        return modelAndView;
    }

    /**
     * 增加菜单的方法。
     *
     * @param leftMenu 需要增加的菜单对象
     * @return json数据
     */
    @RequestMapping(value = "/add-left-menu", method = {RequestMethod.POST})
    @ResponseBody
    public Object addLeftMenu(LeftMenu leftMenu) {
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        JSONObject jsonObject = new JSONObject();
        leftMenu.setCreateTime(new Date());
        boolean flag = leftMenuService.create(leftMenu);
        if (flag) {
            Right right = new Right();
            right.setRightDesc(leftMenu.getMenuName());
            right.setCreateTime(new Date());
            right.setUpdateTime(new Date());
            right.setRightName(leftMenu.getMenuName());
            right.setMenuId(leftMenuDao.findMenuByName(leftMenu.getMenuName()).getMenuId());
            right.setIsMenuRight("1");
            Right right1 = this.converRight(leftMenuDao.findMenuByName(leftMenu.getMenuName()), right);
            Boolean rightFlag = rightService.create(right1);
            if (rightFlag) {
                flag = true;
            }
        }
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 分情况封装right的某些属性。
     *
     * @param menu  菜单对象
     * @param right 权限对象
     * @return 封装完成的权限对象
     */
    public Right converRight(LeftMenu menu, Right right) {
        if (null == menu.getMenuSuperCode() || "".equals(menu.getMenuSuperCode())) {
            right.setParentRightId("500");
            right.setParentMenuId("");
            right.setRightCode(menu.getMenuCode());
        } else {
            LeftMenu superMenu = leftMenuDao.findLeftMenuBySuperCode(menu.getMenuSuperCode());
            Right needRight = rightDao.findRightByMenuId(superMenu.getMenuId());
            right.setRightCode(menu.getMenuSuperCode() + ":" + menu.getMenuCode());
            right.setParentRightId(needRight.getRightId() + "");
            right.setParentMenuId(superMenu.getMenuId().toString());
        }
        return right;
    }

    /**
     * 查询需要修改的菜单信息。
     *
     * @param id 需要查询的菜单的id
     * @return 菜单信息的视图
     */
    @RequestMapping(value = "/toupdate-left-menu", method = {RequestMethod.GET})
    @RequiresPermissions("menu:update")
    public ModelAndView toupdateLeftMenu(@Param("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        LeftMenu leftMenu = leftMenuDao.findLeftMenuById(id);
        List<LeftMenu> leftMenus = leftMenuDao.findFatherMenu();
        modelAndView.setViewName("/system-data/menu-value-update");
        modelAndView.addObject("leftMenu", leftMenu);
        modelAndView.addObject("leftMenuSuper", leftMenus);
        modelAndView.addObject("superCode", leftMenu.getMenuSuperCode());
        return modelAndView;
    }

    /**
     * 修改指定菜单的方法。
     *
     * @param leftMenu 需要修改的菜单信息
     * @return json数据
     */
    @RequestMapping(value = "/update-left-menu", method = {RequestMethod.POST})
    @ResponseBody
    public Object updateLeftMenu(LeftMenu leftMenu) {
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        Boolean flag = false;
        if ("".equals(leftMenu.getMenuSuperCode())) {
            leftMenu.setMenuSuperCode(null);
        }
        if ("".equals(leftMenu.getMenuHref())) {
            leftMenu.setMenuHref(null);
        }
        leftMenu.setUpdateBy(username);
        leftMenu.setUpdateTime(new Date());
        Boolean menuFlag = leftMenuService.update(leftMenu);
        if (menuFlag) {
            Right right = new Right();
            right.setMenuId(leftMenu.getMenuId());
            right.setUpdateTime(new Date());
            right.setRightName(leftMenu.getMenuName());
            right.setRightDesc(leftMenu.getMenuName());
            Right right2 = this.converRight(leftMenu, right);
            boolean mesFlag = rightService.updateRightByMenuId(right2);
            if (mesFlag) {
                flag = true;
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 查询此菜单是否有子菜单。
     *
     * @param id 菜单的id
     * @return json数据
     */
    @RequestMapping(value = "/todelete-left-menu", method = {RequestMethod.GET})
    @ResponseBody
    public Object todeleteLeftMenu(@Param("id") int id) {
        LeftMenu leftMenu = leftMenuDao.findLeftMenuById(id);
        JSONArray jsonArray = new JSONArray();
        List<LeftMenu> list = leftMenuDao.findSonMenus(leftMenu.getMenuCode());
        if (list.size() > 0) {
            for (LeftMenu menu : list) {
                jsonArray.add(menu);
            }
        } else {
            jsonArray.add(null);
        }
        return jsonArray.toString();
    }

    /**
     * 删除菜单。
     *
     */
    @RequestMapping(value = "/delete-left-menu", method = {RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions("menu:delete")
    public Object deleteLeftMenu(@Param("id") int id) {
        LeftMenu leftMenu = leftMenuDao.findLeftMenuById(id);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        List<LeftMenu> list = leftMenuDao.findSonMenus(leftMenu.getMenuCode());
        //当前删除菜单的下一级菜单的子集合:list
        if (list.size() > 0) {
            List<LeftMenu> allSonMenus = this.findAllSonMenus(list);
            //下一级无数子菜单的集合子菜单集合:allSonMenus
            if (allSonMenus.size() > 0) {
                this.deleteSonLeftMenuList(allSonMenus);
            }
            this.deleteSonLeftMenuList(list);
            Boolean flags = leftMenuService.delete(leftMenu.getMenuId());
            if (flags) {
                Right right = rightDao.findRightByMenuId(id);
                if (null != right) {
                    boolean rightRoleFlag = rightRoleRelationService.deleteByRightId(
                            right.getRightId());
                    if (rightRoleFlag) {
                        flag = rightService.deleteRightByMenuId(leftMenu.getMenuId());
                    }
                }

            }
        } else {
            //无子菜单
            Boolean menuFlag = leftMenuService.delete(id);
            if (menuFlag) {
                Right right = rightDao.findRightByMenuId(id);
                if (null != right) {
                    boolean rightRoleFlag = rightRoleRelationService.deleteByRightId(
                            right.getRightId());
                    if (rightRoleFlag) {
                        flag = rightService.deleteRightByMenuId(id);
                    }
                }

            }
        }
        if (flag) {
            jsonObject.accumulate("result", true);
        } else {
            jsonObject.accumulate("result", false);
        }
        return jsonObject;
    }

    /**
     * 删除菜单和菜单权限对象。
     *
     * @param leftMenus 菜单集合
     * @return 总共删除的数目
     */
    public Integer deleteSonLeftMenuList(List<LeftMenu> leftMenus) {
        Integer num = 0;
        try {
            for (LeftMenu sonMenu : leftMenus) {
                //通过菜单id查询权限id
                Right right = rightDao.findRightByMenuId(sonMenu.getMenuId());
                if (null != right) {
                    //通过权限id查询该权限下面的子权限，比如增加，删除，修改。。等等
                    List<Right> rights = rightDao.findByParentRightId(right.getRightId());
                    if (rights.size() > 0) {
                        for (Right rig : rights) {
                            //删除权限和角色关联表信息
                            rightRoleRelationService.deleteByRightId(rig.getRightId());
                            //
                            rightService.deleteRightByMenuId(sonMenu.getMenuId());
                        }
                    } else {
                        rightRoleRelationService.deleteByRightId(right.getRightId());
                        rightService.deleteRight(right.getRightId());
                    }
                    leftMenuService.delete(sonMenu.getMenuId());
                }
            }
            childs = new ArrayList<>();
        } catch (Exception ex) {
            logger.error("删除菜单失败", ex);
        }
        return num;
    }

}
