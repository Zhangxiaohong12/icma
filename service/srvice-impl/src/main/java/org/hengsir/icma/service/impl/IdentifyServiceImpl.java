package org.hengsir.icma.service.impl;

import org.hengsir.icma.dao.IdentifyDao;
import org.hengsir.icma.dao.ImageDao;
import org.hengsir.icma.dao.PersonDao;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.IdentyRecord;
import org.hengsir.icma.entity.Image;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.entity.Youtu;
import org.hengsir.icma.service.IdentifyService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author hengsir
 * @date 2018/4/1 下午4:51
 */
@Service
public class IdentifyServiceImpl implements IdentifyService {


    @Autowired
    UserDao userDao;

    @Autowired
    PersonDao personDao;

    @Autowired
    ImageDao imageDao;

    @Autowired
    Youtu youtu;

    @Autowired
    IdentifyDao identifyDao;

    Logger logger = LoggerFactory.getLogger(IdentifyServiceImpl.class);

    @Override
    public Map<String, Object> identify(String imgPath, int classId, int num) {
        logger.info("--------------多人脸识别开始----------------");
        logger.info("--------------多人脸识别：imagePath:{},classId:{},num:{}----------------",imgPath,classId,num);
        List<String> personIds = new ArrayList<>();
        /*List<String> classIds = new ArrayList<>();
        classIds.add(classId);*/
        Map<String, Object> returnMap = new HashMap<>();
        try {
            //1、先进行youtu中的人脸检索得出结果
            JSONObject resp = youtu.MultiFaceIdentify(imgPath, classId + "", null, num, 40);
            if (resp.getInt("errorcode") == 0) {
                logger.info("--------------多人脸识别---成功----------------");
                //2、从结果的results中获取所有的candidates里的person_id
                JSONArray results = resp.getJSONArray("results");
                for (int j = 0;j < results.length();j ++ ){
                    JSONObject candidateObject = results.getJSONObject(j);
                    JSONArray candidates = candidateObject.getJSONArray("candidates");
                    for (int i = 0; i < candidates.length(); i++) {
                        JSONObject o = (JSONObject) candidates.get(i);
                        //获取置信度
                        int cond = o.getInt("confidence");
                        if (cond >= 65) {
                            String personId = o.getString("person_id");
                            String faceId = o.getString("face_id");
                            User u = new User();
                            u.setPersonId(personId);
                            u.setClassId(classId);
                            User user = userDao.find(u);
                            if (user != null) {
                                //是这个班的学生，然后看学生是否存在这faceId，如果存在，说明检测成功，不在则反之
                                Image image = new Image();
                                image.setPersonId(personId);
                                image.setFaceId(faceId);
                                Image img = imageDao.find(image);
                                if (img != null) {
                                    //能查出图片，说明比对成功
                                    personIds.add(personId);
                                }
                            }
                        }

                    }
                }

                //从数据库中校验
                Map<String, Object> map = new HashMap<>();
                //说明比对失败
                if (personIds.size() == 0) {
                    personIds.add("00000");
                }
                map.put("personIds", personIds);
                map.put("classId", classId);
                //获取可能到了的人
                List<User> matchUsers = userDao.identify(map);
                //获取可能没到的人
                List<User> noMatchs = userDao.noMatch(map);
                returnMap.put("match", matchUsers);
                returnMap.put("noMatch", noMatchs);
                returnMap.put("matchNum", matchUsers.size());
                returnMap.put("noMatchNum", noMatchs.size());
                returnMap.put("studentNum", num);
                logger.info("--------------多人脸识别，识别出:{} 人----------------",returnMap.get("matchNum"));
                //存进数据库
                IdentyRecord identyRecord = new IdentyRecord();
                identyRecord.setClassId(classId);
                identyRecord.setImagePath(imgPath);
                identyRecord.setMatchNum(matchUsers.size());
                identyRecord.setNoMatchNum(noMatchs.size());
                identyRecord.setCreateTime(new Date());
                String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1,imgPath.length());
                identyRecord.setImgName(imgName);
                identifyDao.record(identyRecord);
            } else {
                logger.error("--------------------多人脸识别---失败，错误码为：{}--------------------",resp.getInt("errorcode"));
                returnMap.put("respCode", resp.getInt("errorcode"));
                returnMap.put("respDesc", resp.getString("errormsg"));
            }
        } catch (IOException e) {
            returnMap.put("exception", e.getMessage());
        } catch (JSONException e) {
            returnMap.put("exception", e.getMessage());
        } catch (KeyManagementException e) {
            returnMap.put("exception", e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            returnMap.put("exception", e.getMessage());
        }
        return returnMap;
    }


}
