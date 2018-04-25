package org.hengsir.icma.manage.image.controller;

import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.service.IdentifyService;
import org.hengsir.icma.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hengsir
 * @date 2018/4/23 下午11:36
 */

@RequestMapping("/img")
@Controller
public class ImageController {

    @Autowired
    UserDao userDao;

    @Autowired
    IdentifyService identifyService;

    @RequestMapping("/to-test")
    public ModelAndView toTest() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/rights/test-image");
        return modelAndView;
    }

    /**
     * 校验入口
     *
     * @param imageFile
     * @param classId
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Object identy(@RequestParam(value = "imageFile", required = false) MultipartFile imageFile, int classId, HttpSession session) {
        Map<String, Object> map = null;
        JSONObject jsonObject = new JSONObject();
        if (imageFile.getSize() > 2*1048576){
            jsonObject.accumulate("result","toMax");
            return jsonObject;
        }
        try {
            //把图片存放在规定路径
            String photoPath = FileUploadUtils.saveFile("IDENTY", imageFile);
            //根据classId得到应有人数
            int count = userDao.studentCounts(classId);
            map = identifyService.identify(photoPath, classId, count);
            List<User> match = (List<User>) map.get("match");
            if (match.size() > 0) {
                jsonObject.accumulate("result", true);
                List<User> noMatch = (List<User>) map.get("noMatch");
                session.setAttribute("match",match);
                session.setAttribute("noMatch",noMatch);
                session.setAttribute("matchNum",match.size());
                session.setAttribute("noMatchNum",noMatch.size());
            } else {
                jsonObject.accumulate("result", false);
            }
        } catch (Exception e) {
            jsonObject.accumulate("result", false);
        }

        //返回map
        return jsonObject;

    }

    @RequestMapping("/result")
    public ModelAndView result(HttpSession session) {
        ModelAndView model = new ModelAndView();
        model.setViewName("/rights/test-result");
        model.addObject("match", session.getAttribute("match"));
        model.addObject("noMatch", session.getAttribute("noMatch"));
        model.addObject("matchNum", session.getAttribute("matchNum"));
        model.addObject("noMatchNum", session.getAttribute("noMatchNum"));
        return model;
    }

}

