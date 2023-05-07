package com.shopping.mall.login.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {
    private int userSeq;
    private String userId;
    private String kakaoId;
    private String userPw;
    private String userName;
    private String userProfile;
    private String userBirth;
    private String userPhone;
    private String userEmail;
    private String regDate;
    private String loginDate;
    private int status;
    private String adYn;
}
