package org.hengsir.icma.service.impl;

import com.youtu.Youtu;
import org.hengsir.icma.dao.ImageWriteDao;
import org.hengsir.icma.dao.PersonWriteDao;
import org.hengsir.icma.dao.UserDao;
import org.hengsir.icma.entity.Image;
import org.hengsir.icma.entity.Person;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.service.PersonService;
import org.hengsir.icma.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hengsir
 * @date 2018/1/10 上午11:47
 */

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonWriteDao personWriteDao;

    @Autowired
    private ImageWriteDao imageWriteDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Youtu youtu;

    @Override
    public Boolean create(Person person, Image image) {
        List<String> list = new ArrayList<>();
        list.add(person.getUser().getClassId() + "");
        try {
            if (image != null) {
                JSONObject response = youtu.NewPerson(image.getImagePath(), person.getPersonId(), list);
                if (response.getInt("errorcode") == 0) {
                    image.setFaceId((String) response.get("face_id"));
                    image.setPersonId(person.getPersonId());
                    imageWriteDao.addImg(image);
                    User needBindUser = userDao.selectUserById(person.getUser().getUserId());
                    needBindUser.setPersonId(person.getPersonId());
                    userService.bindPerson(needBindUser);
                    return personWriteDao.create(person);
                }
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
        return false;
    }

    @Override
    public Boolean delete(String personId) {
        try {
            JSONObject response = youtu.DelPerson(personId);
            if (response.getInt("errorcode") == 0) {
                imageWriteDao.deleteByPersonId(personId);
                Person dp = personWriteDao.findById(personId);
                User needDeBind = userDao.selectUserByAccount(dp.getUser().getUserAccount());
                userService.deBindPerson(needDeBind.getUserId());
                return personWriteDao.delete(personId);
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
        return false;
    }

    @Override
    public boolean addFace(String personId, Image img) {
        List<Image> imageList = new ArrayList<>();
        imageList.add(img);
        List<String> list = new ArrayList<String>();
        for (Image i : imageList) {
            list.add(i.getImagePath());
        }
        try {
            JSONObject resp = youtu.AddFace(personId, list);
            if (resp.getInt("errorcode") == 0) {
                JSONArray jsonArray = (JSONArray) resp.get("face_ids");
                int index = 0;
                for (int i = 0; i < imageList.size(); i++) {
                    imageList.get(i).setFaceId(jsonArray.get(i).toString());
                    imageList.get(i).setPersonId(personId);
                    index += imageWriteDao.addImg(imageList.get(i));
                }
                if (index > 0) {
                    return true;
                }
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
        return false;
    }

    @Override
    public boolean deleteFace(String personId, String faceId) {
        List<String> list = new ArrayList<>();
        list.add(faceId);
        try {
            JSONObject resp = youtu.DelFace(personId, list);
            if (resp.getInt("errorcode") == 0) {
                int index = imageWriteDao.deleteImg(faceId);
                if (index > 0) {
                    return true;
                }
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
        return false;
    }
}
