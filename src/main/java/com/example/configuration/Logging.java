/*
 * Copyright (c) 2016 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class Logging {

    @Bean
    public FilterRegistrationBean requestLoggingFilter() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();

        final CRLoggingFilter commonsRequestLoggingFilter = new CRLoggingFilter();
        commonsRequestLoggingFilter.setIncludeClientInfo(true);
        commonsRequestLoggingFilter.setIncludeQueryString(true);
        commonsRequestLoggingFilter.setIncludePayload(true);
        commonsRequestLoggingFilter.setIncludeHeaders(false);
        commonsRequestLoggingFilter.setMaxPayloadLength(10000);

        registration.setFilter(commonsRequestLoggingFilter);
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }

    public static class CRLoggingFilter extends CommonsRequestLoggingFilter {

        @Override
        protected boolean shouldLog(HttpServletRequest request) {
            // do not log actuator health requests
            if (request.getRequestURI().contains("/admin/health") || (request.getRequestURI().contains("/system/health"))) {
                return false;
            } else {
                return super.shouldLog(request);
            }
        }

    }
}

