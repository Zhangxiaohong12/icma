package org.hengsir.icma.manage.person.controller;

import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.ImageDao;
import org.hengsir.icma.dao.PersonDao;
import org.hengsir.icma.entity.Image;
import org.hengsir.icma.entity.Person;
import org.hengsir.icma.entity.PersonVo;
import org.hengsir.icma.manage.shiro.ShiroUser;
import org.hengsir.icma.service.PersonService;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.List;
import java.util.Set;

/**
 * @author hengsir
 * @date 2018/3/14 下午3:59
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    String classLoaderPath = this.getClass().getResource("").getPath();
    String imgPath =
            classLoaderPath.substring(0, classLoaderPath.indexOf("target")) + "src/main/resources/static/" +
                    File.separatorChar + "imgs";

    @Autowired
    PersonDao personDao;

    @Autowired
    PersonService personService;

    @Autowired
    ImageDao imageDao;

    /**
     * 分页查询，如果是学生，则进入详情页
     *
     * @param person
     * @param index
     * @param size
     * @return
     */
    @RequestMapping("/search")
    @RequiresPermissions("person:search")
    public ModelAndView search(Person person, @RequestParam(value = "pageNum", defaultValue = "1") int index,
                               @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        ModelAndView model = new ModelAndView();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Set<String> roleSet = shiroUser.getRoles();
        boolean isStu = false;
        for (String str : roleSet) {
            if (str.equals("student")) {
                isStu = true;
            }
        }
        //为学生,直接进入详情页
        if (isStu) {
            model.setViewName("/rights/person-view");
            Person p = personDao.findByUserId(shiroUser.getUserId());
            List<Image> imgs = imageDao.findByPerson(p);
            model.addObject("person", p);
            model.addObject("imgs",imgs);
        } else {
            model.setViewName("/rights/person-list");
            Page<Person> page = personDao.findAll(new Page<>(index, size), person);
            model.addObject("list", page.getResult());
            model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        }

        return model;
    }

    /**
     * 详情
     *
     * @param personId
     * @return
     */
    @RequestMapping("/view")
    public ModelAndView view(String personId){
        ModelAndView modelAndView = new ModelAndView();
        Person person = personDao.findById(personId);
        List<Image> imgs = imageDao.findByPerson(person);
        modelAndView.setViewName("/rights/person-view");
        modelAndView.addObject("person", person);
        modelAndView.addObject("imgs",imgs);
        return modelAndView;
    }

    @RequestMapping("/to-add-person")
    @RequiresPermissions("person:add")
    public ModelAndView toAdd(int userId) {
        ModelAndView model = new ModelAndView();
        Person person = personDao.findByUserId(userId);
        model.setViewName("/rights/person-add");
        if (person != null) {
            //已经存在，不显示新增按钮
            model.addObject("has", true);
        } else {
            model.addObject("has", false);
        }
        return model;
    }

    /**
     * 新增个体，第一次新增一定要上传一张照片
     *
     * @param personVo
     * @param photo    保存路径是static/imgs/用户名/
     * @return
     */
    @RequestMapping("/add-person")
    @ResponseBody
    public Object addPerson(PersonVo personVo, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        JSONObject jsonObject = new JSONObject();
        String result = "fail";
        String fileName = "";
        Person p = personDao.findByUserId(personVo.getUserId());
        if (p != null) {
            //已经存在，无法添加
            jsonObject.accumulate("result", "isHas");
            return jsonObject;
        }
        //以帐号为dir，存放该用户的图片
        fileName = personVo.getUserAccount();
        OutputStream out = null;
        try {
            File file = new File(fileName);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            InputStream in = photo.getInputStream();
            out = new BufferedOutputStream(new FileOutputStream(imgPath + "/" + fileName));
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        Image img = new Image();
        img.setImageUrl(imgPath + "/" + fileName);
        boolean flag = personService.create(personVo, img);
        if (flag) {
            result = "success";
        }
        jsonObject.accumulate("result", result);
        return jsonObject;
    }

    @RequestMapping("/delete-person")
    @RequiresPermissions("person:delete")
    @ResponseBody
    public Object deletePerson(String personId) {
        JSONObject jsonObject = new JSONObject();
        boolean result = personService.delete(personId);
        jsonObject.accumulate("result",result);
        return jsonObject;
    }


    @RequestMapping("/add-img")
    @RequiresPermissions("image:add")
    @ResponseBody
    public Object addImg(){
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }
}
