package org.hengsir.icma.service;

import org.hengsir.icma.entity.User;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/3/29 下午4:48
 * 用于进行多人脸检索功能业务处理
 */
public interface IdentifyService {

    /**
     * 识别图片中存在的人脸，针对识别出来的结果，获取本班符合的list
     *
     * @param imgPath 小程序上传的图片保存路径
     * @param classId 班级id，对应groupId
     * @param num 需要识别的人数
     * @return 符合的用户对象
     */
    List<User> identify(String imgPath,int classId,int num);



}
