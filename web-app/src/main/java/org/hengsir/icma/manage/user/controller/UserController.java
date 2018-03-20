package org.hengsir.icma.manage.user.controller;

import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.entity.UserVo;
import org.hengsir.icma.manage.shiro.ShiroUser;
import org.hengsir.icma.service.UserService;
import org.hengsir.icma.utils.MD5Util;
import org.hengsir.icma.utils.PasswordUtil;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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


    @RequestMapping("/search")
    @RequiresPermissions("user:search")
    public ModelAndView search(UserVo userVo, @RequestParam(value = "pageNum", defaultValue = "1") int index,
                               @RequestParam(value = "pageSize", defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        Page<User> page =  userDao.findByUser(userVo,new Page<>(index,size));
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
        String encodePass = MD5Util.encodeMD5(password,userAccount);
        user.setUserPassword(encodePass);

        Boolean result = userService.create(user);
        jsonObject.accumulate("result", result);
        return jsonObject;
    }

}
