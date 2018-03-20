package org.hengsir.icma.entity;

/**
 * @author hengsir
 * @date 2018/3/20 下午4:20
 */
public class ClassVo extends Class {
    private String xiBieName;
    private String schoolName;
    private Integer schoolId;
    private String schoolCode;

    public String getXiBieName() {
        return xiBieName;
    }

    public void setXiBieName(String xiBieName) {
        this.xiBieName = xiBieName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
}
