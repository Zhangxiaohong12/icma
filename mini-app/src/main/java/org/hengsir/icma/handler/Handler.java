package org.hengsir.icma.handler;


import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.ParentRequest;

/**
 * 业务处理器。
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @author leaves chen[leaves615@gmail.com]
 */
public interface Handler<R, S> {

    /**
     * 处理业务逻辑。
     *
     * @param request 请求数据。
     * @return 响应数据。
     */
    S handle(ParentRequest request) throws HandleException;

    /**
     * 判断Handler 是否支持处理当前请求。
     *
     * @param request 请求数据。
     * @return true-支持，false-不支持。
     */
    boolean isSupported(ParentRequest request);
}
