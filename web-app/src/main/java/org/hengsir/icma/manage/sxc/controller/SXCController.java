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
        model.setViewName("/sxc/school-list");
        model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        model.addObject("list", page.getResult());
        return model;
    }


    @RequestMapping(value = "/school/search-list", method = RequestMethod.GET)
    @ResponseBody
    public Object searchSchoolList(@RequestParam(defaultValue = "0") Integer schoolId, String schoolName, String schoolCode, String pageNumber, String pageSize) {
        School school = new School();
        school.setSchoolCode(schoolCode);
        school.setSchoolName(schoolName);
        Page<School> page = sxcDao.findSchool(school, new Page<>(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
        List<School> schools = page.getResult();
        JSONArray jsonData = new JSONArray();
        for (School s : schools) {
            boolean flag = false;
            if (s.getId() == schoolId) {
                flag = true;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("schoolId", s.getId());
            jsonObject.put("schoolName", s.getSchoolName());
            jsonObject.put("schoolCode", s.getSchoolCode());
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
        model.setViewName("/sxc/xiBie-list");
        model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        model.addObject("list", page.getResult());
        return model;
    }

    @RequestMapping(value = "/xiBie/search-list", method = RequestMethod.GET)
    @ResponseBody
    public Object searchXiBieList(@RequestParam(defaultValue = "0") Integer schoolId, @RequestParam(defaultValue = "0") Integer xiBieId, String xiBieName, String pageNumber, String pageSize) {
        XiBieVo xi = new XiBieVo();
        xi.setSchoolId(schoolId);
        xi.setXiBieName(xiBieName);
        Page<XiBie> page = sxcDao.findXiBie(xi, new Page<>(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
        List<XiBie> xiBies = page.getResult();
        JSONArray jsonData = new JSONArray();
        for (XiBie x : xiBies) {
            boolean flag = false;
            if (x.getId() == xiBieId) {
                flag = true;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("xiBieId", x.getId());
            jsonObject.put("xiBieName", x.getXiBieName());
            jsonObject.put("schoolName", x.getSchool().getSchoolName());
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
        model.setViewName("/sxc/class-list");
        model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        model.addObject("list", page.getResult());
        return model;
    }

    @RequestMapping(value = "/class/search-list", method = RequestMethod.GET)
    @ResponseBody
    public Object searchClassList(@RequestParam(defaultValue = "0") Integer schoolId, @RequestParam(defaultValue = "0") Integer xiBieId, @RequestParam(defaultValue = "0") Integer classId, String className, String pageNumber, String pageSize) {
        ClassVo classVo = new ClassVo();
        classVo.setSchoolId(schoolId);
        classVo.setXiBieId(xiBieId);
        classVo.setId(classId);
        classVo.setClassName(className);
        Page<Class> page = sxcDao.findClass(classVo, new Page<>(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
        List<Class> classes = page.getResult();
        JSONArray jsonData = new JSONArray();
        for (Class x : classes) {
            boolean flag = false;
            if (x.getId() == classId) {
                flag = true;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("classId", x.getId());
            jsonObject.put("className", x.getClassName());
            jsonObject.put("xiBieName", x.getXiBie().getXiBieName());
            jsonObject.put("schoolName", x.getXiBie().getSchool().getSchoolName());
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
     *
     * @return
     */
    @RequestMapping("/school/to-add")
    @RequiresPermissions("school:add")
    public ModelAndView toAddSchool() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/sxc/school-add");
        return model;
    }


    /**
     * 新增学校
     *
     * @param school
     * @return
     */
    @RequestMapping(value = "/school/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addSchool(School school) {
        boolean flag = sxcService.createSchool(school);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 跳转学校修改页面
     *
     * @return
     */
    @RequestMapping("/school/to-update")
    @RequiresPermissions("school:update")
    public ModelAndView toUpdateSchool(Integer id) {
        ModelAndView model = new ModelAndView();
        School school = sxcDao.findSchoolById(id);
        model.setViewName("/sxc/school-update");
        model.addObject("school", school);
        return model;
    }

    @RequestMapping(value = "/school/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSchool(School school) {
        boolean flag = sxcService.updateSchool(school);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    @RequestMapping("/school/delete")
    @ResponseBody
    public Object deleteSchool(Integer id) {
        boolean flag = sxcService.deleteSchool(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 跳转到系别添加页面
     *
     * @return
     */
    @RequestMapping("/xiBie/to-add")
    @RequiresPermissions("xiBie:add")
    public ModelAndView toAddXiBie() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/sxc/xiBie-add");
        return model;
    }


    /**
     * 新增系别
     *
     * @param xiBieVo
     * @return
     */
    @RequestMapping(value = "/xiBie/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addXiBie(XiBieVo xiBieVo) {
        boolean flag = sxcService.createXiBie(xiBieVo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 跳转系别修改页面
     *
     * @return
     */
    @RequestMapping("/xiBie/to-update")
    @RequiresPermissions("xiBie:update")
    public ModelAndView toUpdateXiBie(Integer id) {
        ModelAndView model = new ModelAndView();
        XiBie xiBie = sxcDao.findXiBieById(id);
        model.setViewName("/sxc/xiBie-update");
        model.addObject("xiBie", xiBie);
        return model;
    }

    @RequestMapping(value = "/xiBie/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateXiBie(XiBieVo xiBieVo) {
        boolean flag = sxcService.updateXiBie(xiBieVo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    @RequestMapping("/xiBie/delete")
    @ResponseBody
    public Object deleteXiBie(Integer id) {
        boolean flag = sxcService.deleteXiBie(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 跳转到班级添加页面
     *
     * @return
     */
    @RequestMapping("/class/to-add")
    @RequiresPermissions("class:add")
    public ModelAndView toAddClass() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/sxc/class-add");
        return model;
    }


    /**
     * 新增系别
     *
     * @param classVo
     * @return
     */
    @RequestMapping(value = "/class/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addClass(ClassVo classVo) {
        boolean flag = sxcService.createClass(classVo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 跳转班级修改页面
     *
     * @return
     */
    @RequestMapping("/class/to-update")
    @RequiresPermissions("class:update")
    public ModelAndView toUpdateClass(Integer id) {
        ModelAndView model = new ModelAndView();
        Class c = sxcDao.findClassById(id);
        model.setViewName("/sxc/class-update");
        model.addObject("class", c);
        return model;
    }

    @RequestMapping(value = "/class/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateClass(ClassVo classVo) {
        boolean flag = sxcService.updateClass(classVo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    @RequestMapping("/class/delete")
    @ResponseBody
    public Object deleteClass(Integer id) {
        boolean flag = sxcService.deleteClass(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
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
