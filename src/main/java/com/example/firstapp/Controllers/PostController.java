package com.example.firstapp.controllers;

import com.example.firstapp.domain.Post;
import com.example.firstapp.domain.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by sackmann on 02.05.2016.
 */
@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository repository;

    // selects all posts, loads list.hmtl
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listPost(Model model) {
        model.addAttribute("posts", repository.findAll());
        return "posts/list";
    }

    // deletes single post via id, via redirect reloads list view again
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        repository.delete(id);
        return new ModelAndView("redirect:/posts");
    }

    // new post, loads corresponding view
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProject() {
        return "posts/new";
    }

    // this is triggered via submit button on create.html, does save, reloads list.hmtl
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("message") String comment) {
        repository.save(new Post(comment));
        return new ModelAndView("redirect:/posts");
    }

    // this is triggered via submit button on edit.html, does save, reloads list.html
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("post_id") long id,
                               @RequestParam("message") String message) {
        Post post = repository.findOne(id);
        post.setMessage(message);
        repository.save(post);
        return new ModelAndView("redirect:/posts");
    }

    // edit existing post, loads corresponding edit.html view
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id,
                       Model model) {
        Post post = repository.findOne(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }
}
