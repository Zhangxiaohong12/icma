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
    private Group group;

    private int groupId;

    private Group parentGroup;

    private int parentGroupId;

    private Group grandGroup;

    private int grandGroupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(int parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public int getGrandGroupId() {
        return grandGroupId;
    }

    public void setGrandGroupId(int grandGroupId) {
        this.grandGroupId = grandGroupId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(Group parentGroup) {
        this.parentGroup = parentGroup;
    }

    public Group getGrandGroup() {
        return grandGroup;
    }

    public void setGrandGroup(Group grandGroup) {
        this.grandGroup = grandGroup;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public String getIdCard() {
        return userIdCard;
    }

    public void setIdCard(String idCard) {
        this.userIdCard = idCard;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getKey() {
        return userId+"";
    }
}
