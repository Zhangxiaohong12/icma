package org.hengsir.icma.controller;

import com.alibaba.fastjson.JSONObject;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.handler.response.ParentResponseHeader;
import org.hengsir.icma.handler.response.ResultResponse;
import org.hengsir.icma.handler.response.body.ResultResponseBody;
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
import java.util.Map;

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
    public String identy(@RequestParam("imageFile") MultipartFile file, int classId){

        ResultResponse resultResponse = new ResultResponse();
        ResultResponseBody responseBody = new ResultResponseBody();
        ParentResponseHeader parentResponseHeader = new ParentResponseHeader();

        //把图片存放在规定路径
        String photoPath = FileUploadUtils.saveFile("IDENTY",file);
        //根据classId得到应有人数
        int count = userDao.studentCounts(classId);
        //返回map
        Map<String,Object> map =  identifyService.identify(photoPath,classId,count);
        List<User> match = (List<User>) map.get("match");
        if (match.size() > 0){
            parentResponseHeader.setCode("00");
            parentResponseHeader.setDesc("分析成功");
            List<User> noMatch = (List<User>) map.get("noMatch");
            Integer matchNum = (Integer) map.get("matchNum");
            Integer noMatchNum = (Integer) map.get("noMatchNum");
            responseBody.setMatch(match);
            responseBody.setNoMatch(noMatch);
            responseBody.setMatchNum(matchNum);
            responseBody.setNoMatchNum(noMatchNum);
            resultResponse.setHeader(parentResponseHeader);
            resultResponse.setBody(responseBody);

        } else {
            parentResponseHeader.setCode("1");
            parentResponseHeader.setDesc("分析失败");
            resultResponse.setHeader(parentResponseHeader);
        }
        String json = JSONObject.toJSONString(resultResponse);
        return json;
    }
}
