package org.hengsir.icma.manage.user.controller;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.SecurityManager;
import org.hengsir.icma.dao.RightRoleRelationDao;
import org.hengsir.icma.dao.RoleDao;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.dao.UserRelRoleDao;
import org.hengsir.icma.entity.*;
import org.hengsir.icma.manage.shiro.ShiroUser;
import org.hengsir.icma.service.*;
import org.hengsir.icma.utils.MD5Util;
import org.hengsir.icma.utils.PasswordUtil;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.List;

/**
 * @author hengsir
 * @date 2018/3/14 下午4:03
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    RoleDao roleDao;

    @Autowired
    RoleService roleService;

    @Autowired
    RightRoleRelationDao roleRelationDao;

    @Autowired
    RightRoleRelationService roleRelationService;

    @Autowired
    UserRelRoleService userRelRoleService;

    @Autowired
    UserRelRoleDao relRoleDao;

    @Autowired
    PersonService personService;


    @RequestMapping("/search")
    @RequiresPermissions("user:search")
    public ModelAndView search(UserVo userVo, @RequestParam(value = "pageNum", defaultValue = "1") int index,
                               @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        ModelAndView modelAndView = new ModelAndView();
        Page<User> page = userDao.findByUser(userVo, new Page<>(index, size));
        modelAndView.setViewName("/rights/user-list");
        //生成分页
        modelAndView.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        modelAndView.addObject("list", page.getResult());
        return modelAndView;
    }

    /**
     * 新增用户跳转页面。
     *
     * @return 新增页面链接
     */
    @RequestMapping(value = "/toadd", method = {RequestMethod.GET})
    @RequiresPermissions("user:add")
    public ModelAndView toadd() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/rights/user-add");
        return modelAndView;

    }

    /**
     * 增加用户。
     *
     * @param user 用户信息
     * @return modelAndView 返回页面数据
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public Object add(User user) {
        String userAccount = user.getUserAccount();
        User auser = userService.findUserByAccount(userAccount);
        JSONObject jsonObject = new JSONObject();
        if (auser != null) {
            jsonObject.accumulate("result", "a");
            return jsonObject;
        }

        user.setCreateTime(new Date());
        //对密码进行加密处理
        String password = user.getUserPassword();
        user.setUserStatus("1");
        String encodePass = MD5Util.encodeMD5(password, userAccount);
        user.setUserPassword(encodePass);

        Boolean result = userService.create(user);
        if (result) {
            Person p = new Person();
            p.setPersonName(user.getUserName());
            p.setClassId(user.getClassId());
            p.setUser(user);
            personService.create(p,null);
        }
        jsonObject.accumulate("result", result);
        return jsonObject;
    }

    /**
     * 跳转到修改页面
     *
     * @param userId
     * @return
     */
    @RequestMapping("/to-update")
    @RequiresPermissions("user:update")
    public ModelAndView toUpdate(Integer userId) {
        ModelAndView model = new ModelAndView();
        model.setViewName("/rights/user-update");
        User user = userDao.selectUserById(userId);
        model.addObject("user", user);
        return model;
    }

    /**
     * 修改用户信息
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(UserVo userVo, @RequestParam(value = "reSet", defaultValue = "0") Integer reSet) {
        //这里是如果遇到忘记密码了，要管理员重置密码
        if (reSet == 1) {
            String reSetPwd = MD5Util.encodeMD5("123456", userVo.getUserAccount());
            userVo.setUserPassword(reSetPwd);
        }
        userVo.setUpdateTime(new Date());
        boolean flag = userService.update(userVo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    @RequestMapping("/to-update-pwd")
    @RequiresPermissions("user:updatePwd")
    public ModelAndView toUpdatePwd() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/rights/user-update-pwd");
        return model;
    }

    @RequestMapping("/update-pwd")
    @ResponseBody
    public Object updatePwd(String oldPwd, String newPwd) {
        JSONObject jsonObject = new JSONObject();
        String result = "fail";
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        User user = userDao.selectUserByAccount(shiroUser.getUserAccount());
        String old = MD5Util.encodeMD5(oldPwd, shiroUser.getUserAccount());
        String npwd = MD5Util.encodeMD5(newPwd, shiroUser.getUserAccount());
        //当旧密码正确
        if (old.equals(user.getUserPassword())) {
            user.setUserPassword(npwd);
            boolean flag = userService.update(user);
            if (flag) {
                result = "success";
                shiroUser.setUserPassword(npwd);
            }
        } else {
            result = "notSame";
        }
        jsonObject.accumulate("result", result);
        return jsonObject;
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions("user:delete")
    @ResponseBody
    public Object delete(Integer userId) {
        boolean flag = userService.delete(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 查询角色。
     *
     * @return 角色对象
     */
    @RequestMapping(value = "/queryRole", method = {RequestMethod.GET})
    @ResponseBody
    public Object queryRole() {
        JSONObject jsonObject = new JSONObject();
        List<Role> roleList = roleDao.findAll();
        jsonObject.accumulate("result", roleList);
        return jsonObject;
    }

    /**
     * 保存用户和角色关联关系。
     *
     * @param userId 用户ID
     * @return modelAndView 返回页面数据
     */
    @RequestMapping(value = "/saveRoleRelUser", method = {RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions("user:giveRole")
    public Object saveRoleRelUser(Integer userId, String roleIds) {
        String[] roleIdArr = roleIds.split("[,]");
        int count = 0;
        //先删除之前的角色关系，再新增。
        userRelRoleService.deleteByUserId(userId);
        for (String roleId : roleIdArr) {
            UserRelRole urr = new UserRelRole();
            urr.setRoleId(Integer.parseInt(roleId));
            urr.setUserId(userId);
            Boolean flag = userRelRoleService.create(urr);
            if (!flag) {
                count++;
            }
        }
        Boolean result = true;
        if (count > 0) {
            result = false;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", result);
        return jsonObject;
    }

    /**
     * 根据用户Id查询角色。
     *
     * @param userId 用户ID
     * @return 角色对象
     */
    @RequestMapping(value = "/queryRoleByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public Object queryRoleByUserId(Integer userId) {
        JSONObject jsonObject = new JSONObject();
        List<Role> roleList = roleDao.findByUserId(userId);
        jsonObject.accumulate("result", roleList);
        return jsonObject;
    }

    /**
     * 根据用户账号查询用户信息。
     *
     * @param userAccount 用户账号
     * @return 用户信息
     */
    @RequestMapping(value = "/getUser", method = {RequestMethod.GET})
    public ModelAndView getUser(String userAccount) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/home"));
        User user = userDao.selectUserByAccount(userAccount);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    /**
     * 根据用户Id查询角色。
     *
     * @param userId 用户ID
     * @return 角色对象
     */
    @RequestMapping(value = "/queryRoleByUser/{userId}", method = {RequestMethod.GET},
            produces = {"application/text;charset=UTF-8"})
    @ResponseBody
    @RequiresPermissions("user:lookRole")
    public Object queryRoleByUser(@PathVariable("userId") Integer userId) {
        List<Role> roleList = roleDao.findByUserId(userId);
        JSONArray jsonData = new JSONArray();
        for (Role role : roleList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roleId", role.getRoleId());
            jsonObject.put("roleName", role.getRoleName());
            jsonObject.put("roleDesc", role.getRoleDesc());
            jsonObject.put("roleCode", role.getRoleCode());
            jsonData.add(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("rows", jsonData);//JSONArray
        return json.toString();
    }


}
