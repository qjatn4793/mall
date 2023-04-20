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
    private int mainSeq;
    private String mainTitle;
    private String mainRegDate;
    private int mainViews;
    private String mainContents;
    private String thumbContents;

}
