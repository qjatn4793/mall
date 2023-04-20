package com.shopping.mall.contents.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentsVo {
    private int userSeq;
    private String userId;
    private String userPw;
    private String userName;
    private String userProfile;
    private String userBirth;
    private String userPhone;
    private int status;
}
