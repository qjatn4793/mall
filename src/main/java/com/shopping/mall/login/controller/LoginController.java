package com.shopping.mall.login.controller;

import com.shopping.mall.configuration.SHA256;
import com.shopping.mall.login.service.LoginService;
import com.shopping.mall.login.vo.LoginVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String kakaoLogin(@RequestParam(required = false) String email,
                             @RequestParam(required = false) String userId,
                             @RequestParam(required = false) String profile_nickname,
                             HttpServletRequest request) {

        HttpSession session = request.getSession();

        LoginVo loginVo = new LoginVo();

        loginVo.setKakaoId(userId);
        loginVo.setUserEmail(email);

        loginVo = loginService.kakaoCheck(loginVo);

        if (loginVo == null) {

            loginVo = loginService.emailCheck(email);

            if (loginVo != null){
                loginVo.setKakaoId(userId);
                int result = loginService.updateKakaoId(loginVo);

                if (result > 0) {
                    // 로그인 시간 기록
                    loginService.updateLoginDate(loginVo);
                    session.setAttribute("loginVo", loginVo);
                    return "2"; // 가입된 이메일이 존재함
                }else {
                    return "3"; // 알 수 없는 오류
                }

            }else {

                LoginVo kakaoLoginVo = new LoginVo();

                kakaoLoginVo.setUserId(userId);
                kakaoLoginVo.setKakaoId(userId);
                kakaoLoginVo.setUserEmail(email);
                kakaoLoginVo.setUserName(profile_nickname);

                loginService.insertKakao(kakaoLoginVo);
                // 로그인 시간 기록
                loginService.updateKakaoLoginDate(kakaoLoginVo);
                kakaoLoginVo = loginService.kakaoCheck(kakaoLoginVo);

                System.out.println(kakaoLoginVo);

                session.setAttribute("loginVo", kakaoLoginVo);
                return "1"; // 회원정보없음 자동 회원가입 후 로그인 email, profile_nickname, kakaoId 가지고 가야함
            }

        } else {
            session.setAttribute("loginVo", loginVo);
            // 로그인 시간 기록
            loginService.updateLoginDate(loginVo);
            return "1"; // 로그인 성공
        }
    }
}
