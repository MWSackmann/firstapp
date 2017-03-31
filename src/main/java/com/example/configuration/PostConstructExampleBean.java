/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PostConstructExampleBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostConstructExampleBean.class);

    // this will be called at start-up of spring-boot application (only once)
    @PostConstruct
    public void init() {
        LOGGER.info("PostConstuctExample Called");
    }
}
