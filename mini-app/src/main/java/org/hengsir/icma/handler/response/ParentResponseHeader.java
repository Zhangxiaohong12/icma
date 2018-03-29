package org.hengsir.icma.handler.response;


/**
 * 通用响应头实现。
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @author leaves chen[leaves615@gmail.com]
 */
public class ParentResponseHeader implements ResponseHeader {

    private String code;
    private String desc;

    public ParentResponseHeader(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public ParentResponseHeader() {
        this.code = "00";
        this.desc = "操作成功";
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
