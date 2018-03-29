package org.hengsir.icma.handler.dispatcher;


import org.hengsir.icma.handler.request.ParentRequest;
import org.hengsir.icma.handler.response.ParentResponse;

import java.util.List;

/**
 * 业务处理分发器。
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @author leaves chen[leaves615@gmail.com]
 */
public interface Dispatcher<T> {

    /**
     * 分发器初始化。
     */
    void init();

    /**
     * 业务处理。
     *
     * @param request 请求数据。
     * @return 响应数据。
     */
    ParentResponse handle(ParentRequest request);

    /**
     * 设置处理器集合。
     *
     * @param handlers 处理器
     */
    void setHandlers(List<T> handlers);
}
