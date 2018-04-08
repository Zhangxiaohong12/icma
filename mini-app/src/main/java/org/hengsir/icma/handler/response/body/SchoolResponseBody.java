package org.hengsir.icma.handler.response.body;

import org.hengsir.icma.entity.School;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hengsir
 * @date 2018/4/4 上午11:51
 */
public class SchoolResponseBody {
    private Integer schoolId;

    private String schoolName;

    private String schoolCode;

    private List<School> schoolList = new ArrayList<>();

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
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

    public List<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }
}
