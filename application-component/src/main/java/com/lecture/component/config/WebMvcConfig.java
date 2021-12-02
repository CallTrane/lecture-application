package com.lecture.component.config;

import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @className: CorsConfig
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/26 14:42
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer, WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                //allowedOrigins("https://www.dustyblog.cn") //允许跨域的域名，可以用*表示允许任何域名使用
                //.allowedMethods("GET", "POST")//允许任何方法（post、get等）
                .allowedMethods(CorsConfiguration.ALL)
                .allowedHeaders(CorsConfiguration.ALL) //允许任何请求头
                .allowCredentials(true) //带上cookie信息
                .exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
    }

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // 使用对应工厂类提供给我们的接口定制化我们的tomcat connector
        ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(connector -> {
            Http11NioProtocol protocol= (Http11NioProtocol) connector.getProtocolHandler();
            // 定制KeepAliveTimeout，设置30秒内没有请求则服务器自动断开keepalive连接
            protocol.setKeepAliveTimeout(30000);
            // 当客户端发送超过100个请求则自动断开keepalive连接
            protocol.setMaxKeepAliveRequests(100);
        });
    }
}
