package org.hengsir.icma.service.impl;

import org.hengsir.icma.dao.SXCDao;
import org.hengsir.icma.entity.Class;
import org.hengsir.icma.entity.Youtu;
import org.hengsir.icma.service.YoutuService;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHelper;
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
 * @date 2018/5/2 下午2:38
 */
@Service
public class YoutuServiceImpl implements YoutuService {

    @Autowired
    private SXCDao sxcDao;

    @Autowired
    private Youtu youtu;

    @Override
    public Page<Class> getGroupIds(Page<Class> page) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        JSONObject jsonObject = youtu.GetGroupIds();
        List<String> list = new ArrayList<>();
        if (jsonObject.getInt("errorcode") == 0){
            JSONArray jsonArray = jsonObject.getJSONArray("group_ids");
            for (int i = 0;i < jsonArray.length();i++){
                list.add((String) jsonArray.get(i));
            }
        }
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        sxcDao.findByIds(list);
        return PageHelper.endPage();
    }

    @Override
    public List<String> getPersons(Page<String> page,String groupId) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        JSONObject jsonObject = youtu.GetPersonIds(groupId);
        List<String> list = new ArrayList<>();
        if (jsonObject.getInt("errorcode") == 0){
            JSONArray jsonArray = jsonObject.getJSONArray("person_ids");
            for (int i = 0;i < jsonArray.length();i++){
                list.add(jsonArray.getString(i));
            }
        }
        return list;
    }

    @Override
    public boolean deletePersonId(String personId) {
        try {
            JSONObject response = youtu.DelPerson(personId);
            return (response.getInt("errorcode") == 0);
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
