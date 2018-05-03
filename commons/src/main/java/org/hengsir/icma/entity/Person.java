package org.hengsir.icma.entity;

import org.hengsir.icma.model.Model;

import java.util.ArrayList;
import java.util.List;

public class Person implements Model<String>{
    /**
     * 专属的personId在人脸库中
     */
    private String personId;

    /**
     * 名字
     */
    private String personName;

    /**
     * 所属机构
     */
    private Integer classId;

    private Integer userId;

    private List<Image> imageList = new ArrayList<>();

    private User user;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String getKey() {
        return this.personId;
    }
}
