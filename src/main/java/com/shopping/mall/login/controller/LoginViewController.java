package com.shopping.mall.login.controller;

import com.shopping.mall.login.service.LoginService;
import com.shopping.mall.login.vo.LoginVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class LoginViewController {

    @Autowired
    LoginService loginService;

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
