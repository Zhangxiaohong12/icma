package org.hengsir.icma.service;

import org.hengsir.icma.entity.Class;
import org.hengsir.icma.utils.pageHelper.Page;
import org.json.JSONException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author hengsir
 * @date 2018/5/2 下午2:36
 */
public interface YoutuService {
    Page<Class> getGroupIds(Page<Class> page) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException;

    List<String> getPersons(Page<String> page, String groupId) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException;

    boolean deletePersonId(String personId);
}
