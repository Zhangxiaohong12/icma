package org.hengsir.icma.service;

import org.hengsir.icma.entity.Image;
import org.hengsir.icma.entity.Person;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/1/10 上午11:09
 */
public interface PersonService {

    /**
     * 新增个体
     * @param person
     * @return
     */
    Boolean create(Person person, Image image);

    /**
     * 删除个体
     * @param personId
     * @return
     */
    Boolean delete(String personId);



    /**
     * 新增人脸
     * @param personId
     * @return
     */
    boolean addFace(String personId, Image img);

    /**
     * 删除人脸
     * @param personId
     * @return
     */
    boolean deleteFace(String personId, String faceId);

    /**
     * 激活个体
     * @param person
     * @param image
     * @return
     */
    boolean sensitize(Person person,Image image);
}
