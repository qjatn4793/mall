package com.shopping.mall.contact.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class ContactViewController {

    @GetMapping("/contact")
    public String contact(){

        return "contact/contact.html";
    }

}
