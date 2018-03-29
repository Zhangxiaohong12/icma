package org.hengsir.icma.handler.response;

import com.alibaba.fastjson.JSONObject;

/**
 * 通用响应。
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @author leaves chen[leaves615@gmail.com]
 */
public class ParentResponse<T> {

    /**
     * 响应头。
     */
    private ResponseHeader header;

    /**
     * 响应体。
     */
    private T body;

    public ParentResponse() {
    }

    public ParentResponse(ResponseHeader header) {
        this.header = header;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }

    public void setBody(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }
}
