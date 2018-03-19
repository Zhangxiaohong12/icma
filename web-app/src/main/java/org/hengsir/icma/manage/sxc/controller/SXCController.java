package org.hengsir.icma.manage.sxc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hengsir
 * @date 2018/3/19 上午11:35
 */
@Controller
@RequestMapping("/sxc")
public class SXCController {

    @RequestMapping("/school-list")
    public Object schoolList(String schoolId, String pageNumber, String pageSize, String schoolName){

        return null;
    }
}
