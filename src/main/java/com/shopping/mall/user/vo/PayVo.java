package com.shopping.mall.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayVo {
    private int orderSeq;
    private String orderUserId;
    private String orderKakaoId;
    private String orderRegDate;
    private int orderStatus;
    private int orderCount;
    private int orderPrice;
    private int productCount;
    private int productSeq;

    private String productTitle;
    private int productPrice;
    private int categorySeq;
    private String categoryName;
}
