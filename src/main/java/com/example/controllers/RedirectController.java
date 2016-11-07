/*
 * Copyright (c) 2016 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/v1/posts")
public class RedirectController {

    // we redirect all "/v1/posts" request to "/postscd "
    @RequestMapping(value = "")
    public ModelAndView redirectWithUsingForwardPrefix(ModelMap model) {
        model.addAttribute("attribute", "forwardWithForwardPrefix");
        return new ModelAndView("forward:/posts/", model);
    }

    @RequestMapping(value = "/{id}")
    public ModelAndView redirectWithIdUsingForwardPrefix(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("attribute", "forwardWithForwardPrefix");
        return new ModelAndView("forward:/posts/" + id, model);
    }
}
