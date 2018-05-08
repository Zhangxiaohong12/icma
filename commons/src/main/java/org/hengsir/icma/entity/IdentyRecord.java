package org.hengsir.icma.entity;

import java.util.Date;

/**
 * 识别记录实体
 * @author hengsir
 * @date 2018/5/8 上午11:15
 */
public class IdentyRecord {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 可能在座的人数
     */
    private Integer matchNum;

    /**
     * 可能缺席的人数
     */
    private Integer noMatchNum;

    /**
     * 上传的图片路径
     */
    private String imagePath;

    /**
     * 班id
     */
    private Integer classId;

    private String className;

    private Date createTime;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(Integer matchNum) {
        this.matchNum = matchNum;
    }

    public Integer getNoMatchNum() {
        return noMatchNum;
    }

    public void setNoMatchNum(Integer noMatchNum) {
        this.noMatchNum = noMatchNum;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}
