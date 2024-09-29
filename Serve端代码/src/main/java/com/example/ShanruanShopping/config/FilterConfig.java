package com.example.ShanruanShopping.config;

import com.example.ShanruanShopping.util.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private static final Logger log = LoggerFactory.getLogger(FilterConfig.class);

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistrationBean() {
        log.info("Registering JwtFilter");
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/*"); // 拦截所有请求
        registrationBean.addInitParameter("exclude", "/user/login,/user/register,/sell/login,/sell/register,/admin/login"); // 排除特定路径
        return registrationBean;
    }
}
