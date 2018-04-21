package org.hengsir.icma.service.impl;

import org.hengsir.icma.dao.ImageDao;
import org.hengsir.icma.dao.PersonDao;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.Image;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.entity.Youtu;
import org.hengsir.icma.service.IdentifyService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String,Object> identify(String imgPath, int classId, int num) {
        List<String> personIds = new ArrayList<>();
        /*List<String> classIds = new ArrayList<>();
        classIds.add(classId);*/
        Map<String,Object> returnMap = new HashMap<>();
        try {
            //1、先进行youtu中的人脸检索得出结果
            JSONObject resp = youtu.MultiFaceIdentify(imgPath, classId+"", null, num, 40);
            if (resp.getInt("errorcode") == 0) {
                //2、从结果的results中获取所有的candidates里的person_id
                JSONArray results = resp.getJSONArray("results");
                JSONArray candidates = results.getJSONArray(0);
                for (int i = 0; i < candidates.length(); i++) {
                    JSONObject o = (JSONObject) candidates.get(i);
                    String personId = o.getString("person_id");
                    String faceId = o.getString("face_id");
                    User u = new User();
                    u.setPersonId(personId);
                    u.setClassId(classId);
                    User user = userDao.find(u);
                    if(user != null){
                        //是这个班的学生，然后看学生是否存在这faceId，如果存在，说明检测成功，不在则反之
                        Image image = new Image();
                        image.setPersonId(personId);
                        image.setFaceId(faceId);
                        Image img = imageDao.find(image);
                        if (img != null){
                            //能查出图片，说明比对成功
                            personIds.add(personId);
                        }
                    }
                }
                //从数据库中校验
                Map<String,Object> map = new HashMap<>();
                map.put("personIds",personIds);
                map.put("classId",classId);
                //获取可能到了的人
                List<User> matchUsers = userDao.identify(map);
                //获取可能没到的人
                List<User> noMatchs = userDao.noMatch(map);
                returnMap.put("match",matchUsers);
                returnMap.put("noMatch",noMatchs);
                returnMap.put("matchNum",matchUsers.size());
                returnMap.put("noMatchNum",noMatchs.size());
                returnMap.put("studentNum",num);
                return returnMap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
