package org.hengsir.icma.entity;



import org.hengsir.icma.model.Model;

import java.util.Date;

/**
 * 权限管理实体类。
 * @author hengsir
 * @createTime 2017年11月23日
 * @version 1.0.0
 */
public class Right implements Model<Integer> {
    private static final long serialVersionUID = 1L;
    /**
     * 主键。
     */
    private Integer rightId;

    /**
     * 权限编码。
     */
    private String rightCode;

    /**
     * 权限描述。
     */
    private String rightDesc;

    /**
     * 创建时间。
     */
    private Date createTime;

    /**
     * 更新时间。
     */
    private Date updateTime;

    /**
     * 权限名称。
     */
    private String rightName;

    /**
     * 菜单主键。
     */
    private Integer menuId;

    /**
     * 是否菜单权限(1是菜单权限)。
     */
    private String isMenuRight;

    /**
     * 权限的父ID。
     */
    private String parentRightId;

    /**
     * 权限的父菜单ID
     * 所属父菜单的权限ID。
     */
    private String parentMenuId;

    /**
     * 菜单名称
     */
    private String menuName;

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getRightId() {
        return rightId;
    }

    public String getRightCode() {
        return rightCode;
    }

    public void setRightCode(String rightCode) {
        this.rightCode = rightCode;
    }

    public String getRightDesc() {
        return rightDesc;
    }

    public void setRightDesc(String rightDesc) {
        this.rightDesc = rightDesc;
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

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getIsMenuRight() {
        return isMenuRight;
    }

    public void setIsMenuRight(String isMenuRight) {
        this.isMenuRight = isMenuRight;
    }

    public String getParentRightId() {
        return parentRightId;
    }

    public void setParentRightId(String parentRightId) {
        this.parentRightId = parentRightId;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    @Override
    public Integer getKey() {
        return this.rightId;
    }
}
