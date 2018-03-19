package org.hengsir.icma.manage.group.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.SXCDao;
import org.hengsir.icma.entity.School;
import org.hengsir.icma.service.SXCService;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/3/14 下午3:59
 */
@Controller()
@RequestMapping("/sxc")
public class GroupController {

    @Autowired
    private SXCDao sxcDao;

    @Autowired
    private SXCService sxcService;

    /**
     * 分页查看学校
     *
     * @param school
     * @param index
     * @param size
     * @return
     */
    @RequestMapping("/school/search")
    @RequiresPermissions("school:search")
    public ModelAndView search(
            School school, @RequestParam(value = "pageNum", defaultValue = "1") int index,
            @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        ModelAndView model = new ModelAndView();
        Page<School> page = sxcDao.findSchool(school, new Page<>(index, size));
        model.setViewName("/system-data/school-list");
        model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        model.addObject("list", page.getResult());
        return model;
    }


    @RequestMapping("/school/search-list")
    @ResponseBody
    public Object searchSchoolList(String schoolName, String schoolCode, String pageNumber, String pageSize) {
        School school = new School();
        school.setSchoolCode(schoolCode);
        school.setSchoolName(schoolName);
        Page<School> page = sxcDao.findSchool(school, new Page<>(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
        List<School> schools = page.getResult();
        JSONArray jsonData = new JSONArray();
        for (School s : schools){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",s.getId());
            jsonObject.put("schoolName",s.getSchoolName());
            jsonObject.put("schoolCode",s.getSchoolCode());
            jsonData.add(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("rows", jsonData);//JSONArray
        json.put("total", page.getTotal());//总记录数
        return json;
    }

}
