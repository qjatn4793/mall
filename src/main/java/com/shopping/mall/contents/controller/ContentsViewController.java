package com.shopping.mall.contents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class ContentsViewController {

    @GetMapping("/contents")
    public String contents(){

        return "contents/contents.html";
    }
}
