package org.hengsir.icma.handler.request.body;

import javax.validation.constraints.NotNull;

/**
 * @author hengsir
 * @date 2018/4/4 上午11:54
 */
public class XiBieRequestBody {
    @NotNull
    private Integer schoolId;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
}
