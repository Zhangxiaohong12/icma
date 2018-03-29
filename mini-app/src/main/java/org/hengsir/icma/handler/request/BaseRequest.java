package org.hengsir.icma.handler.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 请求参数。
 * RSA验签时JSON字典序时使用。
 *
 * @param <T> 泛型
 * @author lijiguang 2018年1月23日
 */
public class BaseRequest<T> implements Serializable {
    /**
     * 请求头header。
     */
    @JSONField(name = "header")
    private RequestHeader header;

    /**
     * 请求体body。
     */
    @JSONField(name = "body")
    private T body;

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
