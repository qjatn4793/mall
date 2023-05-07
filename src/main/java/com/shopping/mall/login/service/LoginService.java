package com.shopping.mall.login.service;

import com.shopping.mall.login.mapper.LoginMapper;
import com.shopping.mall.login.vo.LoginVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service("loginService")
public class LoginService {

    LoginMapper loginMapper;

    public int loginCheck(LoginVo loginVo){

        return loginMapper.loginCheck(loginVo);
    }

    public String userSelectOne(String userId){

        return loginMapper.userSelectOne(userId);
    }

    public LoginVo userInfo(String userId){

        return loginMapper.userInfo(userId);
    }

    public int updateLoginDate(LoginVo loginVo) {

        return loginMapper.updateLoginDate(loginVo);
    }

    public LoginVo emailCheck(String email){

        return loginMapper.emailCheck(email);
    }

    public LoginVo kakaoCheck(LoginVo loginVo){

        return loginMapper.kakaoCheck(loginVo);
    }

    public int updateKakaoId(LoginVo loginVo){

        return loginMapper.updateKakaoId(loginVo);
    }

    public int insertKakao(LoginVo loginVo){

        return loginMapper.insertKakao(loginVo);
    }

    public int updateKakaoLoginDate(LoginVo loginVo) {

        return loginMapper.updateKakaoLoginDate(loginVo);
    }


}
