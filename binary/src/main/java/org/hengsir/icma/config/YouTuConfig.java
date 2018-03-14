/*
package org.hengsir.icma.config;

import com.youtu.Youtu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

*/
/**
 * @author hengsir
 * @date 2018/1/29 下午3:44
 *//*

@Configuration
@PropertySource(value = {"classpath:youtu.properties"})
public class YouTuConfig {

    @Value("${appId}")
    private String appId;

    @Value("${secretId}")
    private String secretId;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${userId}")
    private String userId;

    @Bean
    public Youtu youtu(){
        Youtu youtu = new Youtu(appId, secretId, secretKey,Youtu.API_YOUTU_END_POINT,userId);
        return youtu;
    }
}
*/
