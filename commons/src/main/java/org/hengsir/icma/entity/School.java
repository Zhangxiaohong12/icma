package org.hengsir.icma.entity;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/3/19 上午10:19
 */
public class School {
    private Integer id;
    private String schoolName;
    private String schoolCode;

    private List<XiBie> xiBieList;

    public List<XiBie> getXiBieList() {
        return xiBieList;
    }

    public void setXiBieList(List<XiBie> xiBieList) {
        this.xiBieList = xiBieList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
