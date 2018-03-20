package org.hengsir.icma.entity;


/**
 * 用户实体类。
 * Created by hengsir on 2017/6/13.
 */
public class UserVo extends User {
    private static final long serialVersionUID = 1L;
    private String schoolName;

    private String className;

    private String xiBieName;

    private Integer schoolId;

    private Integer classId;

    private Integer xiBieId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getXiBieName() {
        return xiBieName;
    }

    public void setXiBieName(String xiBieName) {
        this.xiBieName = xiBieName;
    }

    @Override
    public Integer getSchoolId() {
        return schoolId;
    }

    @Override
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public Integer getClassId() {
        return classId;
    }

    @Override
    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public Integer getXiBieId() {
        return xiBieId;
    }

    @Override
    public void setXiBieId(Integer xiBieId) {
        this.xiBieId = xiBieId;
    }
}
