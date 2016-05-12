package com.example.firstapp.Controllers;

import com.example.firstapp.model.Post;
import com.example.firstapp.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by sackmann on 02.05.2016.
 */
@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    //***************************************************
    //  API methods using json
    //***************************************************

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public ResponseEntity get() {
        LOGGER.info("get Method called");
        return ResponseEntity.ok((List<Post>) repository.findAll());
    }

    // method returns single post via its id (key)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getById(@PathVariable("id") long id) {
        LOGGER.info("getById Method called with id "+id);
        final Post readPost = repository.findOne(id);
        if (readPost == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(repository.findOne(id));
    }

    // method creates new post
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity post(@RequestBody Post post) {
        LOGGER.info("post Method called");
        repository.save(post);
        String response = String.format("id: %s", post.getId());
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    // method updates a single post via its id
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity put(@PathVariable("id") long id, @RequestBody Post post) {
        LOGGER.info("put Method called with id "+id);
        final Post readPost = repository.findOne(id);
        if (readPost == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        repository.save(post);
        //http://stackoverflow.com/a/827045
        //200 or 204
        String response = String.format("id: %s", post.getId());
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    // method deletes single post via its id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") long id) {
        LOGGER.info("deleteById Method called with id "+id);
        final Post readPost = repository.findOne(id);
        if (readPost == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        repository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // *************************************************
    // UI methods using views in resources -> templates -> posts
    // *************************************************

    // method selects all posts, loads list.hmtl
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPost(Model model) {
        LOGGER.info("UI list Method called");
        model.addAttribute("posts", repository.findAll());
        return "posts/list";
    }

    // method deletes single post via id, via redirect reloads list view again
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        LOGGER.info("UI delete Button executed with id "+id);
        repository.delete(id);
        return new ModelAndView("redirect:/posts/list");
    }

    // method loads view for new post
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProject() {
        LOGGER.info("create Button executed");
        return "posts/new";
    }

    // method is triggered via submit button on create.html, does save, reloads list.hmtl
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("message") String comment) {
        LOGGER.info("UI new post created");
        repository.save(new Post(comment));
        return new ModelAndView("redirect:/posts/list");
    }

    // method loads view for editing existing post
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model) {
        LOGGER.info("edit Button executed");
        Post post = repository.findOne(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    // method is triggered via submit button on edit.html, does save, reloads list.html
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("post_id") long id,
                               @RequestParam("message") String message) {
        LOGGER.info("UI post with id "+id+" changed");
        Post post = repository.findOne(id);
        post.setMessage(message);
        repository.save(post);
        return new ModelAndView("redirect:/posts/list");
    }
}
