package com.shopping.mall.contents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class ContentsViewController {

    /*@GetMapping("/contents")
    public String contents(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        model.addAttribute("loginVo", session.getAttribute("loginVo"));

        return "contents/contents.html";
    }*/
}
