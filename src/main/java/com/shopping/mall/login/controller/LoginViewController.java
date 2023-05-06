package com.shopping.mall.login.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String login(HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginVo") != null){
            return "redirect:/";
        }else {
            session.removeAttribute("loginCheck");
            session.removeAttribute("userId");
            return "login/login.html";
        }
    }
}
