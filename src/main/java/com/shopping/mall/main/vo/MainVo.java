package com.shopping.mall.main.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainVo {
    /*product_contents*/
    private int productSeq;
    private String productTitle;
    private String productRegDate;
    private int productViews;
    private int productCount;
    private String productContents;
    private String productExplain;
    private Integer productPrice;
    private String thumbContents;

    /*category_contents*/
    private int categorySeq;
    private String categoryName;
}
