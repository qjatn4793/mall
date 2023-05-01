package com.shopping.mall.about.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class AboutViewController {

    @GetMapping("/about")
    public String about(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        model.addAttribute("loginVo", session.getAttribute("loginVo"));

        return "about/about.html";
    }

}
