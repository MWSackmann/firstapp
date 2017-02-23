/*
 * Copyright (c) 2016 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.service;

import com.example.model.Post;
import com.example.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

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


    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    public <List>Iterable<Post> findAll(){
        LOGGER.info("METHOD CALLED: get");

        logAuth();


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

    public void logAuth() {

        LOGGER.info("Test Authorization: " + request.getHeader(HttpHeaders.AUTHORIZATION));

    }
}
