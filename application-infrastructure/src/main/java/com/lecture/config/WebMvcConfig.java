package com.lecture.config;

import com.lecture.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @className: WebMvcConfig
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 15:53
 */
@Configuration
public class WebMvcConfig {

    @Autowired
    LoginInterceptor loginInterceptor;
}
