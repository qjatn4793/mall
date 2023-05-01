package com.shopping.mall.login.controller;

import com.shopping.mall.configuration.PasswordUtil;
import com.shopping.mall.configuration.SHA256;
import com.shopping.mall.login.service.LoginService;
import com.shopping.mall.login.vo.LoginVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

@AllArgsConstructor
@RestController
@ResponseBody
public class LoginController {

    LoginService loginService;


    @PostMapping("/login")
    public int loginCheck(@RequestBody LoginVo loginVo, HttpServletRequest request) throws NoSuchAlgorithmException {
        SHA256 sha256 = new SHA256();
        HttpSession session = request.getSession();
        int loginCheck = 0;
        String userPw = loginService.userSelectOne(loginVo.getUserId());

        if(userPw.equals(sha256.encrypt(loginVo.getUserPw()))) {
            loginVo.setUserPw(sha256.encrypt(loginVo.getUserPw()));
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

    @PostMapping("/userIdCheck")
    public int userIdCheck(@RequestBody LoginVo loginVo){

        String userId = loginVo.getUserId();

        final String checkString = "[a-zA-Z0-9ㄱ-힣\\s]"; // 특수문자 체크

        Matcher matchTest2;
        matchTest2 = Pattern.compile(checkString).matcher(userId); // ID 공백 포함 특수문자 없으면 true

        if (matchTest2.find() == true) {
            return loginService.userIdCheck(loginVo);
        }else {
            return 0;
        }
    }

    @PostMapping("/userRegister")
    public int userRegister(@RequestBody LoginVo loginVo){

        String userId = loginVo.getUserId();
        String userBirth = loginVo.getUserBirth();
        String userName = loginVo.getUserName();
        String userPhone = loginVo.getUserPhone();

        final String checkNum = "[0-9]+"; // 숫자만 있는지 체크
        final String checkString = "[a-zA-Z0-9ㄱ-힣\\s]"; // 특수문자 체크
        final String checkString2 = "[A-Za-z0-9]"; // 숫자 문자 포함 체크

        Matcher matchTest;
        Matcher matchTest2;
        Matcher matchTest3;
        matchTest = Pattern.compile(checkString).matcher(userName); // 이름 공백 포함 특수문자가 없으면 true
        matchTest2 = Pattern.compile(checkString).matcher(userId); // ID 공백 포함 특수문자 없으면 true
        matchTest3 = Pattern.compile(checkString2).matcher(userId); // ID 에 문자 숫자 포함일경우 true

        if (loginService.userIdCheck(loginVo) == 0) { // 유저 중복 체크 0 일경우 없음
            if (Pattern.matches(checkNum, userBirth) && Pattern.matches(checkNum, userPhone)) { // 생년월일 숫자만 있는지 체크 휴대폰번호 숫자만 있는지 체크
                if (matchTest.find() == true && matchTest2.find() == true && matchTest3.find() == true) { //이름 공백 포함 특수문자가 없으면 true, ID 공백 포함 특수문자 없으면 true, ID 에 문자 숫자 포함일경우 true

                    String encryptedPassword = PasswordUtil.sha256(loginVo.getUserPw()); // 패스워드 암호화

                    loginVo.setUserPw(encryptedPassword);

                    return loginService.userRegister(loginVo);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    @PutMapping("/userRegister")
    public int userUpdate(HttpServletRequest request, @RequestBody LoginVo loginVo){

        HttpSession session = request.getSession();
        SHA256 sha256 = new SHA256();

        if (loginService.userIdCheck(loginVo) == 1) { // 유저 중복 체크 1 일경우 있음

            try {
                loginVo.setUserPw(sha256.encrypt(loginVo.getUserPw()));
            }catch (Exception e) {
                System.out.println(e);
            }

            int userUpdate = loginService.userUpdate(loginVo);

            if (userUpdate == 1) {
                session.removeAttribute("loginVo");
                session.invalidate();
                return userUpdate;
            } else {
                return 0;
            }
        }else {
            return 0;
        }
    }
}
