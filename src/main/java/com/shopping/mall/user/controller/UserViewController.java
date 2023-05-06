package com.shopping.mall.user.controller;

import com.shopping.mall.login.vo.LoginVo;
import com.shopping.mall.user.service.UserService;
import com.shopping.mall.user.vo.PayVo;
import com.shopping.mall.util.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@AllArgsConstructor
@Controller
public class UserViewController {

    @Autowired
    UserService userService;

    @GetMapping("/basket")
    public String basket(){

        return null;
    }

    @GetMapping("/userMypage")
    public String userMypage(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginVo") != null){
            model.addAttribute("loginVo", session.getAttribute("loginVo"));
            return "user/userMypage.html";
        }

        return "redirect:/";
    }

    @GetMapping("/userRegister")
    public String userRegister(HttpServletRequest request,
                               Model model,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String profile_nickname){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginVo") != null){
            return "redirect:/";
        }

        System.out.println("?");
        System.out.println(email);
        System.out.println(profile_nickname);
        System.out.println("?");

        model.addAttribute("email", email);
        model.addAttribute("profile_nickname", profile_nickname);
        return "user/userRegister.html";
    }

    @GetMapping("/userPassword")
    public String userPassword(HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("loginVo") == null){
            return "redirect:/";
        }

        return "user/userPassword.html";
    }

    @GetMapping("/paymentHistory")
    public String paymentHistory(@RequestParam(defaultValue = "1") int pageNum, Model model, HttpServletRequest request){

        HttpSession session = request.getSession();

        LoginVo loginVo = (LoginVo) session.getAttribute("loginVo");

        if(loginVo == null || loginVo.getUserId() == null){
            return "redirect:/";
        }

        int pageSize = 12; // 한 페이지에 보여줄 데이터 개수
        int totalItemCount = userService.getOrderTotalCount(loginVo.getUserId()); // 전체 데이터 개수
        int totalPages = (int) Math.ceil((double) totalItemCount / pageSize); // 전체 페이지 개수
        int startIndex = (pageNum - 1) * pageSize;

        List<PayVo> orderList = userService.getOrderList(startIndex, pageSize, loginVo.getUserId()); // pageNum 에 해당하는 페이지 데이터 가져오기

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setTotalPages(totalPages);

        model.addAttribute("orderList", orderList);
        model.addAttribute("pageInfo", pageInfo);

        return "user/paymentHistory.html";
    }

    @GetMapping("/basketHistory")
    public String basketHistory(@RequestParam(defaultValue = "1") int pageNum, Model model, HttpServletRequest request){

        HttpSession session = request.getSession();

        LoginVo loginVo = (LoginVo) session.getAttribute("loginVo");

        if(loginVo == null || loginVo.getUserId() == null){
            return "redirect:/";
        }

        int pageSize = 12; // 한 페이지에 보여줄 데이터 개수
        int totalItemCount = userService.getOrderTotalCount(loginVo.getUserId()); // 전체 데이터 개수
        int totalPages = (int) Math.ceil((double) totalItemCount / pageSize); // 전체 페이지 개수
        int startIndex = (pageNum - 1) * pageSize;

        List<PayVo> orderList = userService.getOrderList(startIndex, pageSize, loginVo.getUserId()); // pageNum 에 해당하는 페이지 데이터 가져오기

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setTotalPages(totalPages);

        model.addAttribute("orderList", orderList);
        model.addAttribute("pageInfo", pageInfo);

        return "user/basketHistory.html";
    }

    @GetMapping("/viewHistory")
    public String viewHistory(@RequestParam(defaultValue = "1") int pageNum, Model model, HttpServletRequest request){

        HttpSession session = request.getSession();

        LoginVo loginVo = (LoginVo) session.getAttribute("loginVo");

        if(loginVo == null || loginVo.getUserId() == null){
            return "redirect:/";
        }

        int pageSize = 12; // 한 페이지에 보여줄 데이터 개수
        int totalItemCount = userService.getOrderTotalCount(loginVo.getUserId()); // 전체 데이터 개수
        int totalPages = (int) Math.ceil((double) totalItemCount / pageSize); // 전체 페이지 개수
        int startIndex = (pageNum - 1) * pageSize;

        List<PayVo> orderList = userService.getOrderList(startIndex, pageSize, loginVo.getUserId()); // pageNum 에 해당하는 페이지 데이터 가져오기

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setTotalPages(totalPages);

        model.addAttribute("orderList", orderList);
        model.addAttribute("pageInfo", pageInfo);

        return "user/viewHistory.html";
    }
}
