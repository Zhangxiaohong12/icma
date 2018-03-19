package org.hengsir.icma.entity;

/**
 * @author hengsir
 * @date 2018/3/19 上午10:19
 */
public class School {
    private int id;
    private String schoolName;
    private String schoolCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
}
