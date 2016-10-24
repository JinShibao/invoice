package com.mess.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jinshibao on 2016/10/14.
 */

@RestController
public class HomeController {
    @RequestMapping("/")
    public ModelAndView home() {
        return new ModelAndView("/index");
    }
}
