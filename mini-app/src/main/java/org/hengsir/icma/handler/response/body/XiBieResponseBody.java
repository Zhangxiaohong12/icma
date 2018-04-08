package org.hengsir.icma.handler.response.body;

import org.hengsir.icma.entity.XiBie;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hengsir
 * @date 2018/4/4 上午11:59
 */
public class XiBieResponseBody {
    private Integer xiBieId;
    private String xiBieName;
    private List<XiBie> xiBieList = new ArrayList<>();

    public List<XiBie> getXiBieList() {
        return xiBieList;
    }

    public void setXiBieList(List<XiBie> xiBieList) {
        this.xiBieList = xiBieList;
    }

    public Integer getXiBieId() {
        return xiBieId;
    }

    public void setXiBieId(Integer xiBieId) {
        this.xiBieId = xiBieId;
    }

    public String getXiBieName() {
        return xiBieName;
    }

    public void setXiBieName(String xiBieName) {
        this.xiBieName = xiBieName;
    }
}
