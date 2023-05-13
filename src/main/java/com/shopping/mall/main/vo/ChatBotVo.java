package com.shopping.mall.main.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotVo {

    // 사용자 입력 값
    private String userInput;

    // 챗봇 응답 값
    private String message;
}
