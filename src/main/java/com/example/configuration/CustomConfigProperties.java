/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */
/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.sql.DataSourceDefinition;

@ConfigurationProperties("custom.config")
@Component
public class CustomConfigProperties {

    private String param1;
    private String param2;

    public CustomConfigProperties() {
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
}
