package org.hengsir.icma.handler.request;

import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.body.SchoolRequestBody;

/**
 * @author hengsir
 * @date 2018/4/4 上午11:45
 */
public class SchoolRequest extends ParentRequest<SchoolRequestBody> {
    /**
     * 通过Json字符串初始化交易请求。
     *
     * @param requestJsonString json请求字符串。
     */
    public SchoolRequest(String requestJsonString) throws HandleException {
        super(requestJsonString);
        setBody(getBodyObject().toJavaObject(SchoolRequestBody.class));
    }
}
