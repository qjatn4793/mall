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
            return "redirect:" + request.getHeader("Referer");
        }else {
            session.removeAttribute("loginCheck");
            session.removeAttribute("userId");
            return "login/login.html";
        }
    }

    @GetMapping("/userMypage")
    public String userMypage(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginVo") != null){
            model.addAttribute("loginVo", session.getAttribute("loginVo"));
            return "login/userMypage.html";
        }

        return "redirect:/";
    }

    @GetMapping("/userRegister")
    public String userRegister(HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginVo") != null){
            return "/main/main.html";
        }

        return "login/userRegister.html";
    }

    @GetMapping("/userPassword")
    public String userPassword(HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginVo") != null){
            return "/main/main.html";
        }

        return "login/userPassword.html";
    }
}
