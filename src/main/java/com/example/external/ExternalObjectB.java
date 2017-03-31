/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.external;

import org.springframework.stereotype.Service;

@Service
public class ExternalObjectB implements ExternalServiceInterface {

    @Override
    public String getName() {
        return "ObjectB";
    }

    @Override
    public String readObject(String id) {
        return "Frank";
    }
}
