/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // have different users with different access (actuator -> only admin, business data -> only user)
    // actuator-health can be accessed from anybody (without user)
    // see also http://stackoverflow.com/questions/23252128/spring-boot-management-end-points-basic-security
    // important disable security.basic.enabled in application.profile

    @Value("${security.user.name}")
    private String user;
    @Value("${security.user.password}")
    private String password;

    private String adminUser = "admin";
    private String adminPW = "admin";

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Ignore health request i.e. no authority required
        web.ignoring()
                .antMatchers("/admin/health");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        // basic auth for app and actuator access
        http.authorizeRequests()
                .antMatchers("/posts/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .httpBasic();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser(user).password(password).roles("USER")
                .and()
                .withUser(adminUser).password(adminPW).roles("ADMIN");

    }
}