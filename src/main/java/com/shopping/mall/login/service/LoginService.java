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

    public int userIdCheck(LoginVo loginVo){

        return loginMapper.userIdCheck(loginVo);
    }

    public int userRegister(LoginVo loginVo){

        return loginMapper.userRegister(loginVo);
    }

    public LoginVo userInfo(String userId){

        return loginMapper.userInfo(userId);
    }

    public int userUpdate(LoginVo loginVo){

        return loginMapper.userUpdate(loginVo);
    }

    public int updateUserImg(LoginVo loginVo) {

        return loginMapper.updateUserImg(loginVo);
    }

}
