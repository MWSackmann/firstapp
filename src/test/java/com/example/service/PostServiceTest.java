/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.service;

import com.example.model.Post;
import com.example.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sackmann on 23.02.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @InjectMocks
    PostService postService = new PostService();

    @Mock
    private PostRepository repository;

    @Spy
    private MockHttpServletRequest request;

    @org.junit.Before
    public void setUp() throws Exception {

    }

    // we test how to mock an http request which is autowired in productive code
    @Test
    public void testLog() {

        request.addHeader(HttpHeaders.AUTHORIZATION, "test22");
        postService.logAuth();
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }


}
