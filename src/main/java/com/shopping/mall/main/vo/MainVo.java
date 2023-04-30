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
    private String productContents;
    private String thumbContents;

    /*category_contents*/
    private int categorySeq;
    private String categoryName;
}
