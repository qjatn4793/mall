package com.shopping.mall.about.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class AboutViewController {

    @GetMapping("/about")
    public String about(){

        return "about/about.html";
    }

}
