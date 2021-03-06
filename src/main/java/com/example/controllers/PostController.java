/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.controllers;

import com.example.configuration.CustomConfigProperties;
import com.example.model.Post;
import com.example.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private CustomConfigProperties customConfigProperties;
    //***************************************************
    //  API methods using json
    //***************************************************

    // method returns all posts available
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity get() {
        return ResponseEntity.ok((List<Post>) postService.findAll());
    }

    // method returns single post via its id (key)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity getById(@PathVariable("id") long id) {
        final Post readPost = postService.findById(id);
        if (readPost == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(readPost);
    }

    // method creates new post
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity post(@RequestBody Post post) {
        if (post.getId() != null) {
            return new ResponseEntity<String>("Defining an ID is not allowed in POST request", HttpStatus.BAD_REQUEST);
        }
        postService.create(post);

        // we do not return the created entity in body, instead we return the URL to the created entity in response header
        final URI uriOfCreatedPost = ServletUriComponentsBuilder.fromCurrentContextPath().path("posts/{id}").buildAndExpand(post.getId()).toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriOfCreatedPost);
        return new ResponseEntity<String>(String.format("id: %s", post.getId()), responseHeaders, HttpStatus.CREATED);
    }

    // method updates a single post via its id
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity put(@PathVariable("id") long id, @RequestBody Post post) {
        final Post readPost = postService.findById(id);
        if (readPost == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (id != post.getId()) {
            return new ResponseEntity<String>("ID in Path and Body differ", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        postService.update(post);
        //http://stackoverflow.com/a/827045
        //200 or 204
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // method deletes single post via its id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") long id) {
        LOGGER.info("METHOD CALLED: deleteById with id {}", id);
        final Post readPost = postService.findById(id);
        if (readPost == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        postService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // *************************************************
    // UI methods using views in resources -> templates -> posts
    // *************************************************

    // method selects all posts, loads list.hmtl
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPost(Model model) {
        LOGGER.info("UI METHOD CALLED: list");
        model.addAttribute("posts", postService.findAllByOrderByCreatedAtDesc());
        return "posts/list";
    }

    // method deletes single post via id, via redirect reloads list view again
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        LOGGER.info("UI METHOD CALLED: delete with id {}", id);
        postService.delete(id);
        return new ModelAndView("redirect:/posts/list");
    }

    // method loads view for new post
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProject() {
        LOGGER.info("UI METHOD CALLED: new");
        return "posts/new";
    }

    // method is triggered via submit button on create.html, does save, reloads list.hmtl
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("message") String comment) {
        Post post = new Post(comment);
        postService.create(post);
        LOGGER.info("UI METHOD CALLED: new post saved with id {}", post.getId());
        return new ModelAndView("redirect:/posts/list");
    }

    // method loads view for editing existing post
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model) {
        LOGGER.info("UI METHOD CALLED: edit with id {}", id);
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    // method is triggered via submit button on edit.html, does save, reloads list.html
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("post_id") long id,
                               @RequestParam("message") String message) {
        LOGGER.info("UI METHOD CALLED: update with id {}", id);
        Post post = postService.findById(id);
        post.setMessage(message);
        postService.update(post);
        return new ModelAndView("redirect:/posts/list");
    }
}
