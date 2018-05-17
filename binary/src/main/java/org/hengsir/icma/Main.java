package org.hengsir.icma;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hengsir
 * @date 2018/3/1 下午3:22
 */
@SpringBootApplication
@MapperScan("org.hengsir.icma.dao.mapper")
@EnableScheduling
public class Main {

    @Value("${http.port}")
    private Integer port;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("-----------------嘤嘤嘤嘤嘤~~~~启动成功！！！！！------------------" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }

    @Bean
    public ServletWebServerFactory servletContainer(){
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    // 配置http
    private Connector createStandardConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(port);
        connector.setSecure(false);
        connector.setRedirectPort(443);
        return connector;
    }
}
