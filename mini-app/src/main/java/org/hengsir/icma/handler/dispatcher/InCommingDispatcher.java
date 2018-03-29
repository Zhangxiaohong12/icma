package org.hengsir.icma.handler.dispatcher;

import org.hengsir.icma.handler.InCommingHandler;
import org.hengsir.icma.handler.constant.ErrCode;
import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.ParentRequest;
import org.hengsir.icma.handler.response.ParentResponse;
import org.hengsir.icma.handler.response.ParentResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接口请求分发器。
 *
 */
public class InCommingDispatcher extends AbstractDispatcher<InCommingHandler> {
    private static final Logger logger = LoggerFactory.getLogger(InCommingDispatcher.class);

    @Override
    public ParentResponse handle(ParentRequest request) {
        for (InCommingHandler<ParentRequest, ParentResponse> handler : handlers) {
            if (handler.isSupported(request)) {
                try {
                    return handler.handle(request);
                } catch (HandleException ex) {
                    logger.error(ex.getCode(), ex.getMessage());
                    return new ParentResponse(
                        new ParentResponseHeader(ex.getCode(), ex.getMessage()));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    return new ParentResponse(
                        new ParentResponseHeader(ErrCode.ERRC_SYSTEM_ERROR.getCode(),
                                                 ErrCode.ERRC_SYSTEM_ERROR.getDescription()));
                }
            }
        }
        return new ParentResponse(
            new ParentResponseHeader(ErrCode.ERRC_FAIL.getCode(),
                                     ErrCode.ERRC_FAIL.getDescription()));
    }
}
