package com.shopping.mall.login.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.mall.configuration.SHA256;
import com.shopping.mall.login.service.LoginService;
import com.shopping.mall.login.vo.LoginVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

@AllArgsConstructor
@RestController
@ResponseBody
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    private Environment env;


    @PostMapping("/login")
    public int loginCheck(@RequestBody LoginVo loginVo, HttpServletRequest request) throws NoSuchAlgorithmException {
        SHA256 sha256 = new SHA256();
        HttpSession session = request.getSession();
        int loginCheck = 0;
        String userPw = loginService.userSelectOne(loginVo.getUserId());

        if (session == null || userPw == null) {
            return loginCheck;
        }

        if(userPw.equals(sha256.encrypt(loginVo.getUserPw()))) {
            try {
                loginVo.setUserPw(sha256.encrypt(loginVo.getUserPw()));
            }catch (Exception e) {
                //System.out.println(e);
            }
            // 로그인 시간 기록
            loginService.updateLoginDate(loginVo);
            loginCheck = loginService.loginCheck(loginVo);
            loginVo = loginService.userInfo(loginVo.getUserId());

            int status = loginVo.getStatus();

            if (status == 1) { // 일반 사용자 일경우
                if (loginCheck == 1) {
                    session.setAttribute("loginVo", loginVo);
                    return loginCheck;
                } else {
                    loginCheck = 0;
                    return loginCheck;
                }
            }else { // 블랙 리스트 일경우
                return 2;
            }
        }else {
            loginCheck = 0;
            return loginCheck;
        }
    }

    @DeleteMapping("/login")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();
        session.removeAttribute("loginVo");
        session.invalidate();

        return "logout";
    }

    @PostMapping("/kakaoLogin")
    public String kakaoLogin(@RequestParam String accessToken,
                             @RequestParam String refreshToken,
                             @RequestParam String tokenType,
                             @RequestParam Long expiresIn,
                             @RequestParam Long refreshTokenExpiresIn,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String profile_image,
                             @RequestParam(required = false) String birthday,
                             @RequestParam(required = false) String profile_nickname) {

        // 받아온 데이터 처리

        System.out.println(email);
        System.out.println(profile_image);
        System.out.println(birthday);
        System.out.println(profile_nickname);

        return "success";
    }
}
