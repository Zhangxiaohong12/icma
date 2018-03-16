package org.hengsir.icma.manage.group.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.entity.Group;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hengsir
 * @date 2018/3/14 下午3:59
 */
@Controller()
@RequestMapping("/group")
public class GroupController {

    @RequestMapping("/search")
    @RequiresPermissions("group:search")
    public ModelAndView search(
            Group group,@RequestParam(value = "pageNum", defaultValue = "1") int index,
                               @RequestParam(value = "pageSize", defaultValue = "10") int size){



        return null;
    }
}
