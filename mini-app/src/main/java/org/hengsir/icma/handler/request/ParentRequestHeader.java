package org.hengsir.icma.handler.request;


/**
 * 请求头。
 *
 * @author lijiguang 2018年1月31日
 */
public class ParentRequestHeader implements RequestHeader {

    private String type;

    public ParentRequestHeader(String type) {
        this.type = type;
    }


    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
