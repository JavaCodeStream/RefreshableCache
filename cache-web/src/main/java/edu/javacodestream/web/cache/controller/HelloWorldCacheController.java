package edu.javacodestream.web.cache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Sandip on 17-08-2016.
 */
@Controller
@RequestMapping("/")
public class HelloWorldCacheController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView helloWorld(){

        ModelAndView model = new ModelAndView("HelloWorldCache");
        model.addObject("msg", "Welcome to the Cache Manager");

        return model;
    }
}
