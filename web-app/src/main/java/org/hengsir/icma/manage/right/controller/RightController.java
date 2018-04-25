package org.hengsir.icma.manage.right.controller;

import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.hengsir.icma.dao.LeftMenuDao;
import org.hengsir.icma.dao.RightDao;
import org.hengsir.icma.dao.RoleDao;
import org.hengsir.icma.entity.*;
import org.hengsir.icma.manage.shiro.ShiroUser;
import org.hengsir.icma.service.RightRoleRelationService;
import org.hengsir.icma.service.RightService;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author hengsir
 * @date 2018/3/14 下午3:58
 */
@Controller
@RequestMapping("/right")
public class RightController {

    @Autowired
    RightDao rightDao;

    @Autowired
    LeftMenuDao leftMenuDao;

    @Autowired
    RightService rightService;

    @Autowired
    RoleDao roleDao;

    @Autowired
    RightRoleRelationService roleRelationService;

    /**
     * 查询权限列表。
     *
     * @param right 权限信息
     * @param index 开始页面
     * @param size  每页条数
     * @return 查询页面信息
     */
    @RequestMapping(value = "/search")
    @RequiresPermissions("right:search")
    public ModelAndView search(
            Right right, @RequestParam(value = "pageNum", defaultValue = "1") int index,
            @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        //分页查询
        Page<Right> page = rightDao.findByRight(right, new Page<>(index, size));
        List<Right> list = page.getResult();
        String menuName = "";
        for (Right rig : list) {
            if (rig.getParentMenuId() == null || "".equals(rig.getParentMenuId())) {
                //父级菜单
                LeftMenu menu = leftMenuDao.findLeftMenuById(rig.getMenuId());
                menuName = menu.getMenuName();
            } else {
                Integer integer = new Integer(rig.getParentMenuId());
                LeftMenu leftMenu = leftMenuDao.findLeftMenuById(
                        integer);
                if (leftMenu != null) {
                    menuName = leftMenu.getMenuName();
                }
            }
            rig.setMenuName(menuName);
        }
        ModelAndView modelAndView = new ModelAndView();
        List<LeftMenu> leftMenusList = leftMenuDao.findAll();

        modelAndView.addObject("leftMenus", leftMenusList);
        modelAndView.setViewName("/rights/right-list");
        //生成分页
        modelAndView.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        modelAndView.addObject("list", list);
        return modelAndView;
    }

    /**
     * 新增权限跳转页面。
     *
     * @return 新增页面链接
     */
    @RequestMapping(value = "/toadd", method = {RequestMethod.GET})
    @RequiresPermissions("right:add")
    public ModelAndView toAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/rights/right-add");
        return modelAndView;
    }

    /**
     * 增加权限。
     *
     * @return modelAndView 返回页面数据
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(String rightName, String rightCode, String rightDesc, String parentMenuId) {
        Right right = new Right();
        right.setRightName(rightName);
        right.setRightCode(rightCode);
        right.setRightDesc(rightDesc);
        right.setParentMenuId(parentMenuId);
        String result = "fail";
        right.setCreateTime(new Date());
        //根据parentMenuId查询menu权限ID
        Right rigResult = rightDao.findRightByMenuId(Integer.parseInt(right.getParentMenuId()));
        String parentRightId = rigResult.getRightId() + "";
        right.setParentRightId(parentRightId);
        right.setIsMenuRight("0");
        right.setMenuId(null);
        Right rg = rightDao.findByCode(right.getRightCode());
        if (rg == null) {
            Boolean flag = rightService.create(right);
            if (flag) {
                result = "success";
                Right r = rightDao.findByCode(right.getRightCode());
                RightRoleRelation rr = new RightRoleRelation();
                rr.setRightId(r.getRightId());
                rr.setRoleId(400);
                roleRelationService.create(rr);
            }
        } else {
            result = "isHas";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", result);
        return jsonObject;
    }

    /**
     * 查询需要修改的修改。
     *
     * @param rightId 需要查询的权限的id
     * @return 权限信息的视图
     */
    @RequestMapping(value = "/toUpdate", method = {RequestMethod.GET})
    @RequiresPermissions("right:update")
    public ModelAndView toUpdate(@Param("rightId") String rightId) {
        ModelAndView modelAndView = new ModelAndView();
        Right right = rightDao.findById(Integer.parseInt(rightId));
        modelAndView.setViewName("/rights/right-update");
        modelAndView.addObject("right", right);
        return modelAndView;
    }

    /**
     * 修改权限。
     *
     * @return modelAndView 返回页面数据
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public Object update(String rightId, String rightName, String rightCode, String rightDesc, String parentMenuId) {
        Right right = new Right();
        right.setRightId(Integer.parseInt(rightId));
        right.setRightName(rightName);
        right.setRightCode(rightCode);
        right.setRightDesc(rightDesc);
        right.setParentMenuId(parentMenuId);
        right.setUpdateTime(new Date());
        JSONObject jsonObject = new JSONObject();
        //根据parentMenuId查询menu权限ID
        Right rigResult = rightDao.findRightByMenuId(Integer.parseInt(right.getParentMenuId()));
        right.setParentRightId(rigResult.getRightId() + "");
        right.setMenuId(null);
        Boolean result = rightService.update(right);
        jsonObject.accumulate("result", result);
        return jsonObject;
    }

    /**
     * 删除权限。
     *
     * @param rightId 权限信息id
     * @return modelAndView 返回页面数据
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions("right:delete")
    public Object delete(String rightId) {
        JSONObject jsonObject = new JSONObject();
        Boolean result = rightService.delete(Integer.parseInt(rightId));
        jsonObject.accumulate("result", result);
        return jsonObject;
    }

    /**
     * 查询权限树。
     *
     * @return 权限树JSON数据
     */
    @ResponseBody
    @RequestMapping("/getRightTree")
    public List<Map<String, Object>> getRightTree() {
        Subject user = SecurityUtils.getSubject();
        Object object = user.getPrincipal();
        boolean isAdmin = false;
        Set<String> rights = null;
        if (null != object) {
            ShiroUser shiroUser = (ShiroUser) object;
            rights = shiroUser.getRights();
            isAdmin = shiroUser.isAdmin();

        }
        List<Right> rigAllList = rightDao.findAllRight();


        List<Right> rigList = new ArrayList<>();
        //过滤当前用户权限下数据,超级管理员除外
        if (isAdmin) {
            rigList.addAll(rigAllList);

        } else {

            for (Right rig : rigAllList) {
                if (rights.contains(rig.getRightCode())) {

                    rigList.add(rig);
                }
            }
        }
        Map<String, Object> map = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Right rig : rigList) {
            //ParentId = 0 表示顶级【文档项】
            if ("0".equals(rig.getParentRightId())) {
                map = new HashMap<String, Object>();
                int rightId = rig.getRightId();
                map.put("id", rightId);
                map.put("text", "ICMA平台");
                map.put("rig", rig);
                this.getSonTree(map, rigList);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 查看权限。
     *
     * @param roleId 角色主键
     * @return 权限树JSON数据
     */
    @ResponseBody
    @RequestMapping("/viewRightTree/{roleId}")
    public List<Map<String, Object>> viewRightTree(@PathVariable("roleId") String roleId) {
        List<Right> list = rightDao.findRightByRoleId(Integer.parseInt(roleId));

        Map<String, Object> map = null;
        List<Map<String, Object>> listM = new ArrayList<Map<String, Object>>();
        for (Right rig : list) {
            //ParentId = 0 表示顶级【文档项】
            if ("0".equals(rig.getParentRightId())) {
                map = new HashMap<String, Object>();
                int rightId = rig.getRightId();
                map.put("id", rightId);
                map.put("text", "ICMA平台");
                map.put("rig", rig);
                this.getSonTree(map, list);
                listM.add(map);
            }
        }
        return listM;
    }

    /**
     * 查询子节点。
     *
     * @param map     父节点信息
     * @param rigList 权限列表
     * @return 子节点信息
     */
    private Map<String, Object> getSonTree(Map<String, Object> map, List<Right> rigList) {
        List<Map<String, Object>> sonList = new ArrayList<Map<String, Object>>();
        Map<String, Object> sonMap = null;
        for (Right rig : rigList) {
            if (map.get("id").toString().equals(rig.getParentRightId().toString())) {
                sonMap = new HashMap<>();
                int rightId = rig.getRightId();
                sonMap.put("id", rightId);
                if (rig.getMenuId() == null) {
                    sonMap.put("text", rig.getRightName());
                } else {
                    sonMap.put("text", leftMenuDao.findLeftMenuById(
                            rig.getMenuId()).getMenuName());
                }
                sonMap.put("rig", rig);
                sonList.add(sonMap);
                this.getSonTree(sonMap, rigList);
            }
        }
        map.put("children", sonList);
        return map;
    }
}
