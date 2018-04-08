package org.hengsir.icma.handler.request;

import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.body.XiBieRequestBody;

/**
 * @author hengsir
 * @date 2018/4/4 上午11:56
 */
public class XiBieRequest extends ParentRequest<XiBieRequestBody> {
    /**
     * 通过Json字符串初始化交易请求。
     *
     * @param requestJsonString json请求字符串。
     */
    public XiBieRequest(String requestJsonString) throws HandleException {
        super(requestJsonString);
        setBody(getBodyObject().toJavaObject(XiBieRequestBody.class));
    }
}
