package org.hengsir.icma.entity;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/3/19 上午10:21
 */
public class XiBie {

    private Integer id;
    private String xiBieName;
    private Integer schoolId;

    private School school;

    private List<Class> classList;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Class> getClassList() {
        return classList;
    }

    public void setClassList(List<Class> classList) {
        this.classList = classList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getXiBieName() {
        return xiBieName;
    }

    public void setXiBieName(String xieBieName) {
        this.xiBieName = xieBieName;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
}
