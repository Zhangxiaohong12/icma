package org.hengsir.icma.manage.user.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hengsir
 * @date 2018/3/14 下午4:03
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;



    @RequestMapping("/search")
    @RequiresPermissions("user:search")
    public ModelAndView search(User user,@RequestParam(value = "pageNum", defaultValue = "1") int index,
                               @RequestParam(value = "pageSize", defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        Page<User> page =  userDao.findByUser(user,new Page<>(index,size));
        modelAndView.setViewName("/rights/user-list");
        //生成分页
        modelAndView.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        modelAndView.addObject("list", page.getResult());
        return modelAndView;
    }
}
