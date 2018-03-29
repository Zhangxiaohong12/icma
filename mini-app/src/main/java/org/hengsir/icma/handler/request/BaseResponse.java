package org.hengsir.icma.handler.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.hengsir.icma.handler.response.ResponseHeader;

import java.io.Serializable;

/**
 * 响应参数。
 * RSA签名时JSON字典序时使用。
 *
 * @param <T> 泛型
 * @author lijiguang 2018年1月23日
 */
public class BaseResponse<T> implements Serializable {
    /**
     * 响应头header。
     */
    @JSONField(name = "header")
    private ResponseHeader header;

    /**
     * 响应体body。
     */
    @JSONField(name = "body")
    private T body;

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
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
