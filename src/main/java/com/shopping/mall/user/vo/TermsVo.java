package com.shopping.mall.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TermsVo {
    private int termsSeq;
    private String termsTitle;
    private String termsContents;
}
