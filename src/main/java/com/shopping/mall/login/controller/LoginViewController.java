package com.shopping.mall.login.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String login(HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginCheck") == "success"){
            return "/main/main.html";
        }else {
            session.removeAttribute("loginCheck");
            session.removeAttribute("userId");
            return "login/login.html";
        }
    }

    @GetMapping("/userMypage")
    public String userMypage(HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginCheck") != "success"){
            return "/main/main.html";
        }

        return "login/userMypage.html";
    }

    @GetMapping("/userRegister")
    public String userRegister(HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginCheck") == "success"){
            return "/main/main.html";
        }

        return "login/userRegister.html";
    }

    @GetMapping("/userPassword")
    public String userPassword(HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginCheck") == "success"){
            return "/main/main.html";
        }

        return "login/userPassword.html";
    }
}
