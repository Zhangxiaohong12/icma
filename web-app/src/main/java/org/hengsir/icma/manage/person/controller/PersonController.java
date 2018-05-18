package org.hengsir.icma.manage.person.controller;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
import org.hengsir.icma.service.IdentifyService;
import org.hengsir.icma.service.PersonService;
import org.hengsir.icma.service.UserService;
import org.hengsir.icma.utils.FileUploadUtils;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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
    @Value("${upload-path}")
    String imgPath;

    @Autowired
    PersonDao personDao;

    @Autowired
    PersonService personService;

    @Autowired
    ImageDao imageDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    IdentifyService identifyService;

    private Logger logger = LoggerFactory.getLogger(PersonController.class);

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
        boolean hasPerson = false;
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser.getRoles().size() == 1 && shiroUser.getRoles().contains("student")) {
            //学生不能查看别的个体，如果自己已有个体，则可以看到自己的个体，否则什么也没有
            personVo.setPersonId("0");
            User user = userService.findUserByAccount(shiroUser.getUserAccount());
            if (StringUtils.isNotEmpty(user.getPersonId())) {
                hasPerson = true;
                personVo.setPersonId(user.getPersonId());
            }
        }
        model.setViewName("/rights/person-list");
        Page<Person> page = personDao.findAll(new Page<>(index, size), personVo);
        model.addObject("list", page.getResult());
        model.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        model.addObject("hasPerson", hasPerson);

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
     * @param photo 保存路径是/用户名/
     * @return
     */
    @RequestMapping(value = "/add-person", method = RequestMethod.POST)
    @ResponseBody
    public Object addPerson(String personId, String userAccount, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        logger.info("-------------------------新增个体业务开始--------------------------");
        logger.info("-----------新增个体personId:{},绑定的帐户:{}------------",personId,userAccount);
        JSONObject jsonObject = new JSONObject();
        if (photo.getSize() > 2 * 1048576) {
            jsonObject.accumulate("result", "toMax");
            logger.warn("-----------新增个体上传的照片过大:{}------------",photo.getSize());
            return jsonObject;
        }
        try {
            PersonVo personVo = new PersonVo();
            personVo.setPersonId(personId);
            personVo.setUserAccount(userAccount);
            User user = userDao.selectUserByAccount(userAccount);
            if (user == null) {
                //该帐号不存在
                jsonObject.accumulate("result", "notExits");
                logger.warn("-----------新增个体所想绑定的帐号为:{},该帐号不存在------------",userAccount);
                return jsonObject;
            }
            personVo.setUser(user);
            String result = "fail";
            String fileName = "";
            Person p = personDao.findByUserId(user.getUserId());
            if (p != null) {
                //关联用户已经有person，无法添加
                jsonObject.accumulate("result", "userIsHas");
                logger.warn("-----------新增个体所想绑定的帐号为:{},该帐号已经绑定个体------------",userAccount);
                return jsonObject;
            }
            Person p1 = personDao.findById(personVo.getPersonId());
            if (p1 != null) {
                //personId已经存在，无法添加
                jsonObject.accumulate("result", "personIsHas");
                logger.warn("-----------新增个体所想绑定的帐号为:{},该帐号不存在------------",userAccount);
                return jsonObject;
            }
            //以帐号为dir，存放该用户的图片
            fileName = personVo.getUserAccount();
            String filePath = FileUploadUtils.saveFile(fileName, photo);
            Image img = new Image();
            img.setImageUrl(fileName + "/" + photo.getOriginalFilename());
            img.setImagePath(filePath);
            img.setImageName(photo.getOriginalFilename());

            boolean flag = personService.create(personVo, img);
            if (flag) {
                result = "success";
                logger.info("-----------新增个体成功------------");
            }
            jsonObject.accumulate("result", result);
            return jsonObject;
        } catch (Exception e) {
            jsonObject.accumulate("result", false);
            logger.error("-----------新增个体失败------------");
            return jsonObject;
        }
    }

    /**
     * 删除个体
     *
     * @param personId
     * @return
     */
    @RequestMapping("/delete-person")
    @RequiresPermissions("person:delete")
    @ResponseBody
    public Object deletePerson(String personId) {
        logger.info("-----------删除个体开始------------");
        logger.info("-----------删除个体personId:{}------------",personId);
        JSONObject jsonObject = new JSONObject();
        Person p = personDao.findById(personId);
        boolean result = personService.delete(personId);
        logger.info("-----------删除个体结果:{}------------",result);
        //如果成功，删除项目中的文件
        if (result) {
            File file = new File(imgPath + "/" + p.getUser().getUserAccount() + "/");
            if (file.exists() && file.isDirectory()) {
                FileUploadUtils.deleteDir(file);
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
        logger.info("-----------新增人脸开始------------");
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        logger.info("-----------用户：{} 在新增人脸,personId:{}------------",shiroUser.getUserName(),personId);
        JSONObject jsonObject = new JSONObject();
        if (photo.getSize() > 2 * 1048576) {
            jsonObject.accumulate("result", "toMax");
            logger.info("-----------用户：{} 在上传人脸过大:{}------------",shiroUser.getUserName(),photo.getSize());
            return jsonObject;
        }
        try {
            Person p = personDao.findById(personId);
            List<Image> imgs = imageDao.findByPerson(p);
            if (imgs != null && imgs.size() >= 6) {
                //数量达到6张，无法添加
                jsonObject.accumulate("result", "beyond6");
                logger.info("-----------用户：{} 上传的人脸已经达到最多------------",shiroUser.getUserName());
                return jsonObject;
            }
            String fileName = p.getUser().getUserAccount();
            String filePath = FileUploadUtils.saveFile(fileName, photo);
            Image img = new Image();
            img.setImageUrl(fileName + "/" + photo.getOriginalFilename());
            img.setImagePath(filePath);
            img.setImageName(photo.getOriginalFilename());
            boolean flag = personService.addFace(personId, img);
            jsonObject.accumulate("result", flag);
            logger.info("-----------用户：{} 新增人脸结果:{}------------",shiroUser.getUserName(),flag);
            return jsonObject;
        } catch (Exception e) {
            jsonObject.accumulate("result", false);
            logger.error("-----------用户：{} 新增人脸失败------------",shiroUser.getUserName());
            return jsonObject;
        }
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
        logger.info("-----------删除人脸业务开始------------");
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        logger.info("-----------用户：{} 正在删除人脸------------",shiroUser.getUserName());
        JSONObject jsonObject = new JSONObject();
        Image img = imageDao.findByFaceId(faceId);
        logger.info("-----------删除人脸:personId:{},faceId:{}------------",personId,faceId);
        boolean flag = personService.deleteFace(personId, faceId);
        if (flag) {
            File file = new File(img.getImagePath());
            if (file.exists()) {
                file.delete();
            }
        }
        logger.info("-----------删除人脸结果:{}------------",flag);
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 奖图片做为流写出去页面
     *
     * @param path
     * @param resp
     * @throws IOException
     */
    @RequestMapping("/loadImg")
    public void loadImg(String path, HttpServletResponse resp) throws IOException {
        resp.setContentType("image/jpg;charset=utf-8");
        try {
            OutputStream out = resp.getOutputStream();
            File file = new File(path);
            InputStream in = new FileInputStream(file);
            if (in != null) {
                int len;
                byte[] b = new byte[1024];
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
                out.flush();
                out.close();
            }
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 跳转到激活页面
     * @param personId
     * @return
     */
    @RequestMapping("/to-sensitize")
    @RequiresPermissions("person:sensitize")
    public ModelAndView toSensitize(String personId) {
        ModelAndView model = new ModelAndView();
        model.setViewName("/rights/person-sensitize");
        Person person = personDao.findById(personId);
        model.addObject("person", person);
        return model;
    }

    /**
     * 用户激活个体
     *
     * @param personId
     * @param photo
     * @return
     */
    @RequestMapping("/sensitize")
    @ResponseBody
    public Object sensitize(String personId, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        logger.info("-----------------激活个体业务开始----------------");
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        logger.info("-----------------用户：{} 正在激活个体----------------",shiroUser.getUserName());
        JSONObject jsonObject = new JSONObject();
        String result = "";
        if (photo.getSize() > 2 * 1048576) {
            jsonObject.accumulate("result", "toMax");
            logger.info("-----------------用户：{} 上传的图片过大：{}----------------",shiroUser.getUserName(),photo.getSize());
            return jsonObject;
        }

        Person person = personDao.findById(personId);
        User user = userDao.selectUserById(person.getUser().getUserId());
        person.setUser(user);
        //以帐号为dir，存放该用户的图片
        String fileName = person.getUser().getUserAccount();
        String filePath = FileUploadUtils.saveFile(fileName, photo);
        Image img = new Image();
        img.setImageUrl(fileName + "/" + photo.getOriginalFilename());
        img.setImagePath(filePath);
        img.setImageName(photo.getOriginalFilename());

        boolean flag = personService.sensitize(person, img);
        if (flag) {
            result = "success";
        }
        logger.info("-----------------用户：{} 激活个体结果：{}----------------",shiroUser.getUserName(),flag);
        jsonObject.accumulate("result", result);
        return jsonObject;

    }

}
