package com.example.Controllers;

import com.example.model.Post;
import com.example.repository.PostRepository;
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
        LOGGER.info("METHOD CALLED: get");
        return ResponseEntity.ok((List<Post>) repository.findAllByOrderByIdAsc());
    }

    // method returns single post via its id (key)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getById(@PathVariable("id") long id) {
        LOGGER.info("METHOD CALLED: getById with id " + id);
        final Post readPost = repository.findOne(id);
        if (readPost == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(repository.findOne(id));
    }

    // method creates new post
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity post(@RequestBody Post post) {
        repository.save(post);
        LOGGER.info("METHOD CALLED: post with id " + post.getId());
        return new ResponseEntity(repository.findOne(post.getId()), HttpStatus.CREATED);
    }

    // method updates a single post via its id
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity put(@PathVariable("id") long id, @RequestBody Post post) {
        LOGGER.info("METHOD CALLED: put with id " + id);
        final Post readPost = repository.findOne(id);
        if (readPost == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (id != post.getId()) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        repository.save(post);
        //http://stackoverflow.com/a/827045
        //200 or 204
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // method deletes single post via its id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") long id) {
        LOGGER.info("METHOD CALLED: deleteById with id " + id);
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
        LOGGER.info("UI METHOD CALLED: list");
        model.addAttribute("posts", repository.findAllByOrderByIdAsc());
        return "posts/list";
    }

    // method deletes single post via id, via redirect reloads list view again
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        LOGGER.info("UI METHOD CALLED: delete with id " + id);
        repository.delete(id);
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
        repository.save(post);
        LOGGER.info("UI METHOD CALLED: new post saved with id " + post.getId());
        return new ModelAndView("redirect:/posts/list");
    }

    // method loads view for editing existing post
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model) {
        LOGGER.info("UI METHOD CALLED: edit with id " + id);
        Post post = repository.findOne(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    // method is triggered via submit button on edit.html, does save, reloads list.html
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("post_id") long id,
                               @RequestParam("message") String message) {
        LOGGER.info("UI METHOD CALLED: update with id " + id);
        Post post = repository.findOne(id);
        post.setMessage(message);
        repository.save(post);
        return new ModelAndView("redirect:/posts/list");
    }
}
