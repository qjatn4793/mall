package com.shopping.mall.login.mapper;

import com.shopping.mall.login.vo.LoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LoginMapper {

    int loginCheck(LoginVo loginVo);

    String userSelectOne(String userId);

    LoginVo userInfo(String userId);

    int updateLoginDate(LoginVo loginVo);

    LoginVo emailCheck(String userEmail);
    
}
