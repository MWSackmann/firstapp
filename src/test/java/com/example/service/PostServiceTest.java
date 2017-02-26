/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.service;

import com.example.model.Post;
import com.example.model.Quote;
import com.example.model.Value;
import com.example.repository.PostRepository;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockResourceResponse;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @InjectMocks
    PostService postService = new PostService();

    @Mock
    private PostRepository repository;

    @Mock
    private RestTemplate restTemplate;

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

    // we test how to mock exchange method from restTemplate which is autowired in productive code
    @Test
    public void testCallSomething() {

        //Mockito.when(restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class)).thenReturn(new Quote("success", new Value(1L,"test")));
        //Mockito.when(restTemplate.getForObject(Matchers.anyString(), Matchers.eq(Quote.class))).thenReturn(new Quote("success", new Value(1L,"test")));

        ResponseEntity<Quote> responseEntity = new ResponseEntity<Quote>(new Quote("success", new Value(1L,"test")), HttpStatus.ACCEPTED);
        Mockito.when(restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class),Matchers.eq(Quote.class))).thenReturn(responseEntity);

        Quote quote = postService.callSomething();

        Assert.assertEquals("success", quote.getType());
        Assert.assertEquals("1", quote.getValue().getId().toString());
        Assert.assertEquals("test", quote.getValue().getQuote());
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }


}
