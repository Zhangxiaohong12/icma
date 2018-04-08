package org.hengsir.icma.controller;

import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.service.IdentifyService;
import org.hengsir.icma.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/4/8 下午5:15
 */
@Controller
@RequestMapping("/api")
public class IdentyController {

    @Autowired
    UserDao userDao;

    @Autowired
    IdentifyService identifyService;

    /**
     * 校验入口
     *
     * @param file
     * @param classId
     * @return
     */
    @RequestMapping(value = "/identy", method = RequestMethod.POST)
    @ResponseBody
    public List<User> identy(@RequestParam("imageFile") MultipartFile file, int classId){
        //把图片存放在规定路径
        String photoPath = FileUploadUtils.saveFile("IDENTY",file);
        //根据classId得到应有人数
        int count = userDao.studentCounts(classId);
        //返回list
        return identifyService.identify(photoPath,classId,count);
    }
}
