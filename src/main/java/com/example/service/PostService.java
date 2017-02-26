/*
 * Copyright (c) 2016 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.service;

import com.example.model.Post;
import com.example.model.Quote;
import com.example.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by sackmann on 18.10.2016.
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    public <List>Iterable<Post> findAll(){
        LOGGER.info("METHOD CALLED: get");
        logAuth();
        callSomething();
        return repository.findAll();
    }

    public <List>Iterable<Post> findAllByOrderByCreatedAtDesc(){
        LOGGER.info("METHOD CALLED: get with findAllByOrderByCreatedAtDesc");
        return repository.findAllByOrderByCreatedAtDesc();
    }
    public Post findById(Long id){
        LOGGER.info("METHOD CALLED: getById with id {}", id);
        return repository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void create(Post post){
        repository.save(post);
        LOGGER.info("METHOD CALLED: post with id {}", post.getId());

        // test to provoke rollback when creating post number 7
        if(post.getId()==7L){
            throw new RuntimeException("trigger rollback");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(Post post){
        repository.save(post);
        LOGGER.info("METHOD CALLED: put with id {}", post.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(Long id){
        repository.delete(id);
        LOGGER.info("METHOD CALLED: deleteById with id {}", id);
    }

    // some sample method which is using autowired request. Used for mocking sample in testclass
    public void logAuth() {
        LOGGER.info("Test Authorization: " + request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    // some sample method which is using autowired request template. Used for mocking sample in testclass
    public Quote callSomething(){

    //    Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<Quote> result = restTemplate.exchange("http://gturnquist-quoters.cfapps.io/api/random", HttpMethod.GET, entity, Quote.class);
        Quote quote = result.getBody();

        LOGGER.info("Quote called with response: " + quote.toString());
        return quote;
    }
}
