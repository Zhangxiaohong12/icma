package org.hengsir.icma.configuration;

import org.hengsir.icma.handler.InCommingHandler;
import org.hengsir.icma.handler.dispatcher.InCommingDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Spring bean 配置类。
 * Created by hengsir
 */
@Configuration
public class ApiConfigure {

    /**
     *匹配器装置初始化处理器。
     * @param handlers 处理器
     *@return 处理器
     */
    @Bean
    public InCommingDispatcher getInCommingDispatcher(List<InCommingHandler> handlers) {
        InCommingDispatcher dispatcher = new InCommingDispatcher();
        dispatcher.setHandlers(handlers);
        return dispatcher;
    }


}
