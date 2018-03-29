package org.hengsir.icma.handler.request;

import com.alibaba.fastjson.JSONObject;
import org.hengsir.icma.handler.constant.ErrCode;
import org.hengsir.icma.handler.exception.HandleException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用请求。
 *
 */
public class ParentRequest<T> {
    /**
     * 原始请求json数据。
     */
    private String originData;

    /**
     * 请求头JSON字符串。
     */
    private String headerData;

    /**
     * 请求头。
     */
    private RequestHeader header;

    /**
     * 请求体JSON数据。
     */
    private String bodyData;

    /**
     * 请求体JSON对象。
     */
    private JSONObject bodyObject;

    /**
     * 请求体实体。
     */
    private T body;

    private transient Map<String, Object> attributies = new HashMap<>();

    /**
     * 通过Json字符串初始化交易请求。
     *
     * @param requestJsonString json请求字符串。
     */
    public ParentRequest(String requestJsonString) throws HandleException {

        try {
            JSONObject requestObject = JSONObject.parseObject(requestJsonString);
            originData = requestJsonString;
            JSONObject headerObject = requestObject.getJSONObject("header");
            Object type = headerObject.get("type");
            //判断交易类型字段是否存在
            if (type == null || "".equals(type.toString())) {
                throw new HandleException(ErrCode.ERRC_MISSING_ERROR.getCode(),
                    ErrCode.ERRC_MISSING_ERROR.getDescription());
            }
            headerData = headerObject.toJSONString();
            header = requestObject.getObject("header", ParentRequestHeader.class);
            bodyObject = requestObject.getJSONObject("body");
            bodyData = bodyObject.toJSONString();
        } catch (Exception ex) {
            if (!(ex instanceof HandleException)) {
                throw new HandleException(ErrCode.ERRC_FORMAT_ERROR.getCode(),
                                          ErrCode.ERRC_FORMAT_ERROR.getDescription());
            } else {
                throw ex;
            }
        }
    }

    public String getOriginData() {
        return originData;
    }

    @Valid
    public RequestHeader getHeader() {
        return header;
    }

    public String getBodyData() {
        return bodyData;
    }

    public JSONObject getBodyObject() {
        return bodyObject;
    }

    @Valid
    public T getBody() {
        return body;
    }

    protected void setBody(T body) {
        this.body = body;
    }

    public Map<String, Object> getAttributies() {
        return attributies;
    }

    public String supportTxnId() {
        return "all";
    }

    public String getHeaderData() {
        return headerData;
    }

    @Override
    public String toString() {
        return "GenericRequest{" + "originData='" + originData + '\'' + ", headerData='" +
               headerData + '\'' + ", header=" + header + ", bodyData='" + bodyData + '\'' +
               ", bodyObject=" + bodyObject + ", body=" + body + ", attributies=" + attributies +
               '}';
    }
}
