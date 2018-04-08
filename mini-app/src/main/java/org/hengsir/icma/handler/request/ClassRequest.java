package org.hengsir.icma.handler.request;

import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.body.ClassRequestBody;

/**
 * @author hengsir
 * @date 2018/4/4 上午11:58
 */
public class ClassRequest extends ParentRequest<ClassRequestBody>{

    /**
     * 通过Json字符串初始化交易请求。
     *
     * @param requestJsonString json请求字符串。
     */
    public ClassRequest(String requestJsonString) throws HandleException {
        super(requestJsonString);
        setBody(getBodyObject().toJavaObject(ClassRequestBody.class));
    }
}
