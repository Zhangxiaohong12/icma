package org.hengsir.icma.handler.request.body;

import javax.validation.constraints.NotNull;

/**
 * @author hengsir
 * @date 2018/4/4 上午11:55
 */
public class ClassRequestBody {
    @NotNull
    private Integer xiBieId;

    public Integer getXiBieId() {
        return xiBieId;
    }

    public void setXiBieId(Integer xiBieId) {
        this.xiBieId = xiBieId;
    }
}
