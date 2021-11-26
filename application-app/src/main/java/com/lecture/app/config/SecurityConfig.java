package com.lecture.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @className: SecurityConfig
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/22 15:29
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll().and().logout().permitAll()    // 配置不需要登录验证
                .and().csrf().disable();    // 关闭CSRF保护
    }
}
