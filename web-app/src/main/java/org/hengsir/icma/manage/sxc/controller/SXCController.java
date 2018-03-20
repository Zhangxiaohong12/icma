package org.hengsir.icma.manage.sxc.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.SXCDao;
import org.hengsir.icma.entity.*;
import org.hengsir.icma.entity.Class;
import org.hengsir.icma.service.SXCService;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/3/19 上午11:35
 */
@Controller
@RequestMapping("/sxc")
public class SXCController {

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
    public ModelAndView schoolSearch(
            School school, @RequestParam(value = "pageNum", defaultValue = "1") int index,
            @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        ModelAndView model = new ModelAndView();
        Page<School> page = sxcDao.findSchool(school, new Page<>(index, size));
        model.setViewName("/system-data/school-list");
        model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        model.addObject("list", page.getResult());
        return model;
    }


    @RequestMapping(value = "/school/search-list",method = RequestMethod.GET)
    @ResponseBody
    public Object searchSchoolList(@RequestParam(defaultValue = "0")Integer schoolId,String schoolName, String schoolCode, String pageNumber, String pageSize) {
        School school = new School();
        school.setSchoolCode(schoolCode);
        school.setSchoolName(schoolName);
        Page<School> page = sxcDao.findSchool(school, new Page<>(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
        List<School> schools = page.getResult();
        JSONArray jsonData = new JSONArray();
        for (School s : schools){
            boolean flag = false;
            if(s.getId() == schoolId){
                flag = true;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("schoolId",s.getId());
            jsonObject.put("schoolName",s.getSchoolName());
            jsonObject.put("schoolCode",s.getSchoolCode());
            jsonObject.put("checked", flag);
            jsonData.add(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("rows", jsonData);//JSONArray
        json.put("total", page.getTotal());//总记录数
        return json;
    }

    /**
     * 分页查看系别
     *
     * @param xiBieVo
     * @param index
     * @param size
     * @return
     */
    @RequestMapping("/xiBie/search")
    @RequiresPermissions("xiBie:search")
    public ModelAndView xiBieSearch(
            XiBieVo xiBieVo, @RequestParam(value = "pageNum", defaultValue = "1") int index,
            @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        ModelAndView model = new ModelAndView();
        Page<XiBie> page = sxcDao.findXiBie(xiBieVo, new Page<>(index, size));
        model.setViewName("/system-data/xiBie-list");
        model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        model.addObject("list", page.getResult());
        return model;
    }

    @RequestMapping(value = "/xiBie/search-list",method = RequestMethod.GET)
    @ResponseBody
    public Object searchXiBieList(@RequestParam(defaultValue = "0")Integer schoolId,@RequestParam(defaultValue = "0")Integer xiBieId,String xiBieName, String pageNumber, String pageSize) {
        XiBieVo xi = new XiBieVo();
        xi.setSchoolId(schoolId);
        xi.setXiBieName(xiBieName);
        Page<XiBie> page = sxcDao.findXiBie(xi, new Page<>(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
        List<XiBie> xiBies = page.getResult();
        JSONArray jsonData = new JSONArray();
        for (XiBie x : xiBies){
            boolean flag = false;
            if(x.getId() == xiBieId){
                flag = true;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("xiBieId",x.getId());
            jsonObject.put("xiBieName",x.getXiBieName());
            jsonObject.put("schoolName",x.getSchool().getSchoolName());
            jsonObject.put("checked", flag);
            jsonData.add(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("rows", jsonData);//JSONArray
        json.put("total", page.getTotal());//总记录数
        return json;
    }

    /**
     * 分页查看班级
     *
     * @param classVo
     * @param index
     * @param size
     * @return
     */
    @RequestMapping("/class/search")
    @RequiresPermissions("class:search")
    public ModelAndView classSearch(
            ClassVo classVo, @RequestParam(value = "pageNum", defaultValue = "1") int index,
            @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        ModelAndView model = new ModelAndView();
        Page<Class> page = sxcDao.findClass(classVo, new Page<>(index, size));
        model.setViewName("/system-data/class-list");
        model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        model.addObject("list", page.getResult());
        return model;
    }

    @RequestMapping(value = "/class/search-list",method = RequestMethod.GET)
    @ResponseBody
    public Object searchClassList(@RequestParam(defaultValue = "0")Integer schoolId,@RequestParam(defaultValue = "0")Integer xiBieId, @RequestParam(defaultValue = "0")Integer classId,String className, String pageNumber, String pageSize) {
        ClassVo classVo = new ClassVo();
        classVo.setSchoolId(schoolId);
        classVo.setXiBieId(xiBieId);
        classVo.setId(classId);
        classVo.setClassName(className);
        Page<Class> page = sxcDao.findClass(classVo, new Page<>(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
        List<Class> classes = page.getResult();
        JSONArray jsonData = new JSONArray();
        for (Class x : classes){
            boolean flag = false;
            if(x.getId() == xiBieId){
                flag = true;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("classId",x.getId());
            jsonObject.put("className",x.getClassName());
            jsonObject.put("xiBieName",x.getXiBie().getXiBieName());
            jsonObject.put("schoolName",x.getXiBie().getSchool().getSchoolName());
            jsonObject.put("checked", flag);
            jsonData.add(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("rows", jsonData);//JSONArray
        json.put("total", page.getTotal());//总记录数
        return json;
    }

    /**
     * 跳转到学校添加页面
     * @return
     */
    @RequestMapping("/school/to-add")
    @RequiresPermissions("school:add")
    public ModelAndView toAddSchool(){
        ModelAndView model = new ModelAndView();
        model.setViewName("/system-data/school-add");
        return model;
    }

    /**
     * 对学校代码的唯一性验证。
     *
     * @param schoolCode 菜单编码
     * @return json数据
     */
    @RequestMapping(value = "/school/validSchoolCode", method = {RequestMethod.POST})
    @ResponseBody
    public Object validLeftMenuCode(@Param("schoolCode") String schoolCode) {
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        School school = new School();
        school.setSchoolCode(schoolCode);
        List<School> list = sxcDao.findSchool(school);
        if (list.size() > 0) {
            flag = true;
        }
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

}
