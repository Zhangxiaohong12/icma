package org.hengsir.icma.entity;


import org.hengsir.icma.model.Model;

import java.util.Date;

public class User implements Model<String> {
    /**
     * 主键。
     */
    private int userId;
    /**
     * 用户名。
     */
    private String userName;
    /**
     * 用户账号。
     */
    private String userAccount;
    /**
     * 用户名密码。
     */
    private String userPassword;
    /**
     * personId。
     */
    private String personId;

    /**
     * 创建时间。
     */
    private Date createTime;
    /**
     * 修改时间。
     */
    private Date updateTime;
    /**
     * 用户状态。
     */
    private String userStatus;
    /**
     * 性别。
     */
    private String userSex;
    /**
     * 身份证号。
     */
    private String userIdCard;

    /**
     * 所属机构
     */
    private School school;

    private int schoolId;

    private XiBie xiBie;

    private int xiBieId;

    private Class theClass;

    private int classId;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public XiBie getXiBie() {
        return xiBie;
    }

    public void setXiBie(XiBie xiBie) {
        this.xiBie = xiBie;
    }

    public int getXiBieId() {
        return xiBieId;
    }

    public void setXiBieId(int xiBieId) {
        this.xiBieId = xiBieId;
    }

    public Class getTheClass() {
        return theClass;
    }

    public void setTheClass(Class theClass) {
        this.theClass = theClass;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String getKey() {
        return userId+"";
    }
}
