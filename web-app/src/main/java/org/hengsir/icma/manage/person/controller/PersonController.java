package org.hengsir.icma.manage.person.controller;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.ImageDao;
import org.hengsir.icma.dao.PersonDao;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.Image;
import org.hengsir.icma.entity.Person;
import org.hengsir.icma.entity.PersonVo;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.manage.shiro.ShiroUser;
import org.hengsir.icma.service.PersonService;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.Date;
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
            classLoaderPath.substring(0, classLoaderPath.indexOf("target")) + "src/main/resources/static" +
                    File.separatorChar + "imgs";

    @Autowired
    PersonDao personDao;

    @Autowired
    PersonService personService;

    @Autowired
    ImageDao imageDao;

    @Autowired
    UserDao userDao;

    /**
     * 分页查询，如果是学生，则进入详情页
     *
     * @param personVo
     * @param index
     * @param size
     * @return
     */
    @RequestMapping("/search")
    @RequiresPermissions("person:search")
    public ModelAndView search(PersonVo personVo, @RequestParam(value = "pageNum", defaultValue = "1") int index,
                               @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        ModelAndView model = new ModelAndView();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Person person = personDao.findByUserId(shiroUser.getUserId());
        model.setViewName("/rights/person-list");
        Page<Person> page = personDao.findAll(new Page<>(index, size), personVo);
        model.addObject("list", page.getResult());
        model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        if (person != null) {
            //已经存在，不显示新增按钮
            model.addObject("has", true);
        } else {
            model.addObject("has", false);
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
    public ModelAndView view(String personId) {
        ModelAndView modelAndView = new ModelAndView();
        Person person = personDao.findById(personId);
        User user = userDao.selectUserById(person.getUser().getUserId());
        person.setUser(user);
        List<Image> imgs = imageDao.findByPerson(person);
        modelAndView.setViewName("/rights/person-view");
        modelAndView.addObject("person", person);
        modelAndView.addObject("imgs", imgs);
        return modelAndView;
    }

    @RequestMapping("/to-add-person")
    @RequiresPermissions("person:add")
    public ModelAndView toAdd() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        ModelAndView model = new ModelAndView();
        model.setViewName("/rights/person-add");
        return model;
    }

    /**
     * 新增个体，第一次新增一定要上传一张照片
     *
     * @param photo 保存路径是static/imgs/用户名/
     * @return
     */
    @RequestMapping(value = "/add-person", method = RequestMethod.POST)
    @ResponseBody
    public Object addPerson(String personId, String userAccount, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        JSONObject jsonObject = new JSONObject();
        PersonVo personVo = new PersonVo();
        personVo.setPersonId(personId);
        personVo.setUserAccount(userAccount);
        User user = userDao.selectUserByAccount(userAccount);
        if (user == null) {
            //该帐号不存在
            jsonObject.accumulate("result", "notExits");
            return jsonObject;
        }
        personVo.setUser(user);
        String result = "fail";
        String fileName = "";
        Person p = personDao.findByUserId(user.getUserId());
        if (p != null) {
            //关联用户已经有person，无法添加
            jsonObject.accumulate("result", "userIsHas");
            return jsonObject;
        }
        Person p1 = personDao.findById(personVo.getPersonId());
        if (p1 != null) {
            //personId已经存在，无法添加
            jsonObject.accumulate("result", "personIsHas");
            return jsonObject;
        }
        //以帐号为dir，存放该用户的图片
        fileName = personVo.getUserAccount();
        String filePath = saveFile(fileName, photo);
        Image img = new Image();
        img.setImageUrl(filePath.substring(filePath.indexOf("/imgs/"), filePath.length()));
        img.setImagePath(filePath);
        img.setImageName(photo.getOriginalFilename());

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
        Person p = personDao.findById(personId);
        boolean result = personService.delete(personId);
        //如果成功，删除项目中的文件
        if (result) {
            File file = new File(imgPath + "/" + p.getUser().getUserAccount());
            if (file.exists() && file.isDirectory()) {
                file.delete();
            }
        }
        jsonObject.accumulate("result", result);
        return jsonObject;
    }

    /**
     * 跳转到新增人脸页面
     *
     * @param personId
     * @return
     */
    @RequestMapping("/to-add-img")
    @RequiresPermissions("image:add")
    public ModelAndView toAddImage(String personId) {
        ModelAndView model = new ModelAndView();
        model.setViewName("/rights/image-add");
        Person p = personDao.findById(personId);
        model.addObject("person", p);
        return model;
    }

    /**
     * 新增人脸
     *
     * @param personId
     * @param photo
     * @return
     */
    @RequestMapping(value = "/add-img", method = RequestMethod.POST)
    @ResponseBody
    public Object addImg(String personId, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        JSONObject jsonObject = new JSONObject();
        Person p = personDao.findById(personId);
        List<Image> imgs = imageDao.findByPerson(p);
        if (imgs != null && imgs.size() >= 6){
            //数量达到6张，无法添加
            jsonObject.accumulate("result","beyond6");
            return jsonObject;
        }
        String fileName = p.getUser().getUserAccount();
        String filePath = saveFile(fileName, photo);
        Image img = new Image();
        img.setImageUrl(filePath.substring(filePath.indexOf("/imgs/"), filePath.length()));
        img.setImagePath(filePath);
        img.setImageName(photo.getOriginalFilename());
        boolean flag = personService.addFace(personId, img);
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 删除人脸
     *
     * @param personId
     * @param faceId
     * @return
     */
    @RequestMapping("/delete-img")
    @RequiresPermissions("image:delete")
    @ResponseBody
    public Object deleteImg(String personId, String faceId) {
        JSONObject jsonObject = new JSONObject();
        Image img = imageDao.findByFaceId(faceId);
        boolean flag = personService.deleteFace(personId, faceId);
        if (flag) {
            File file = new File(img.getImagePath());
            if (file.exists()) {
                file.delete();
            }
        }
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    public String saveFile(String fileName, MultipartFile photo) {
        OutputStream out = null;
        File file = null;
        try {
            file = new File(imgPath + "/" + fileName);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            InputStream in = photo.getInputStream();
            out = new BufferedOutputStream(new FileOutputStream(file.getPath() + "/" + photo.getOriginalFilename()));
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
        return file.getPath() + "/" + photo.getOriginalFilename();
    }
}
