package org.hengsir.icma.handler.response;

/**
 * 响应头。
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @author leaves chen[leaves615@gmail.com]
 */
public interface ResponseHeader {
    /**
     * 获取交易结果。
     *
     * @return 返回结果码
     */
    String getCode();

    /**
     * 获取交易结果描述。
     *
     * @return 返回结果描述
     */
    String getDesc();
}
