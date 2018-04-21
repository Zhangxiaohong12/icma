package org.hengsir.icma.manage.controller;

import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.manage.shiro.ShiroUser;
import org.hengsir.icma.service.UserService;
import org.hengsir.icma.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hengsir
 * @date 2018/4/21 上午10:58
 */
@Controller
@RequestMapping("/to-update-pwd")
public class UpdatePasswordController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @RequestMapping("/update")
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
}
