package org.hengsir.icma.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.hengsir.icma.manage.scan.ShiroRealService;
import org.hengsir.icma.manage.shiro.CredentialsMatcher;
import org.hengsir.icma.manage.shiro.ShiroReal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;

/**
 * @author hengsir
 * @date 2018/2/27 上午10:09
 * 集成shiro配置
 */

@Configuration
@PropertySource(value = {"classpath:redis.properties"})
public class ShiroConfig {


    @Autowired
    private ShiroRealService shiroRealService;


    //配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager(ShiroReal shiroReal) {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(shiroReal);
        return manager;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/home");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/metronic/**","anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/select/**","anon");
        filterChainDefinitionMap.put("/jsondata/**","anon");
        filterChainDefinitionMap.put("/manage/login","anon");
        filterChainDefinitionMap.put("/manage/genCaptcha","anon");
        filterChainDefinitionMap.put("/manage/logout","logout");
        filterChainDefinitionMap.put("/unauth","anon");
        filterChainDefinitionMap.put("/error/notfound","anon");
        filterChainDefinitionMap.put("/error/innererror","anon");
        filterChainDefinitionMap.put("/**", "user");//表示需要认证才可以访问
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    @Bean
    public ShiroReal shiroReal(CredentialsMatcher matcher){
        ShiroReal shiroReal = new ShiroReal();
        shiroReal.setCredentialsMatcher(matcher);
        return shiroReal;
    }

    /**
     * rememberMe管理器,cipherKey生成见{@code Base64Test.java}
     *
     * @return
     */

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);
        return simpleCookie;
    }

    /**
     * rememberMe管理器
     *
     * @return
     */

    @Bean
    public CookieRememberMeManager cookieRememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode("empodDEyMwAAAAAAAAAAAA=="));
        cookieRememberMeManager.setCookie(rememberMeCookie);
        return cookieRememberMeManager;
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持; Controller才能使用@RequiresPermissions
     *
     * @param securityManager
     * @return
     */

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 会话管理器
     *
     * @return
     */

    @Bean
    public DefaultWebSessionManager sessionManager(EnterpriseCacheSessionDAO sessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置全局会话超时时间 半小时 session共享将取决与sessionDao中cacheManager缓存存储时长
        sessionManager.setGlobalSessionTimeout(3 * 1000 * 60);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookie(new SimpleCookie("ICMA_SHIRO_SESSIONID"));
        return sessionManager;
    }

    /**
     * 会话DAO 用于会话的CRUD
     *
     * @return
     */

    @Bean
    public EnterpriseCacheSessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("manager-activeSessionCache");
        return sessionDAO;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}