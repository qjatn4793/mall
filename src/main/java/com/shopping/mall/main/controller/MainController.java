package com.shopping.mall.main.controller;

import com.shopping.mall.main.service.MainService;
import com.shopping.mall.main.vo.ChatBotVo;
import com.shopping.mall.main.vo.MainVo;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
@RestController
@ResponseBody
public class MainController {

    @Autowired
    private Environment env;

    @GetMapping("/manager/image/{contents}/{filename:.+}")
    public void getImage(@PathVariable String contents, @PathVariable String filename, HttpServletResponse response) {

        String uploadDir = env.getProperty("shared.image.upload-dir");
        Path path = Paths.get(uploadDir + "/" + contents + "/" + filename);

        try {
            InputStream inputStream = Files.newInputStream(path);
            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                response.setContentType("image/jpeg");
            } else if (filename.endsWith(".png")) {
                response.setContentType("image/png");
            } else if (filename.endsWith(".gif")) {
                response.setContentType("image/gif");
            } else if (filename.endsWith(".bmp")) {
                response.setContentType("image/bmp");
            } else {
                response.setContentType("application/octet-stream");
            }
            IOUtils.copy(inputStream, response.getOutputStream()); // 이미지 파일 전송
        } catch (IOException e) {
            // 예외 처리
        }
    }

    @PostMapping("/chatBot")
    public ChatBotVo chatBot(@RequestBody ChatBotVo request) {
        String userInput = request.getUserInput();

        // 챗봇 로직 수행
        String botResponse = processChatBotLogic(userInput);

        // 챗봇 응답 생성
        ChatBotVo response = new ChatBotVo();
        response.setMessage(botResponse);

        return response;
    }

    // 챗봇 로직을 수행하는 메소드
    private String processChatBotLogic(String userInput) {

        String response;

        if (userInput.contains("고객센터")) {
            response = "고객센터의 전화번호는 031-111-1234 입니다.";
        } else if (userInput.contains("환불")) {
            response = "고객님께서 주문하신 상품은 배송 준비 단계가 되면 환불이 어렵습니다. 환불을 원하신다면 고객센터로 문의 바랍니다.";
        } else if (userInput.contains("구매")) {
            response = "제품 구매는 로그인이 되어 있는 상태에서, 구매하실 상품을 클릭 하면 우측 상품 설명창 하단에 구매하기 버튼이 있습니다.";
        } else if (userInput.contains("회원가입")) {
            response = "회원가입은 상단의 Login 버튼을 클릭하시면 로그인 창 하단의 버튼을 클릭하셔서 진행이 가능합니다. 카카오톡으로 로그인을 진행하면, 이용약관 동의 후 간편회원가입 후 로그인이 완료 됩니다. 회원 활동을 정상적으로 진행하시려면 로그인 완료 후 우측 상단에 있는 마이페이지 에서 회원정보를 수정해 주세요!";
        } else if (userInput.contains("로그인") || userInput.contains("login")) {
            response = "로그인은 우측상단의 Login 버튼을 클릭하시면 이용이 가능하십니다.";
        } else if (userInput.contains("로그아웃") || userInput.contains("logout")) {
            response = "로그아웃은 로그인이 되어있는 상태에서 우측 상단의 Logout 버튼을 클릭 하시면 로그아웃이 됩니다.";
        } else if (userInput.contains("마이페이지")) {
            response = "마이페이지는 로그인이 되어있는 상태에서 상단에 있는 메뉴를 통해 이용이 가능합니다.";
        } else if (userInput.contains("최근 본 상품")) {
            response = "최근 본 상품은 로그인이 되어있는 상태에서 상단에 있는 메뉴를 통해 이용이 가능하십니다. 최근 본 상품은 고객님께서 최근 조회하신 상품을 10개까지 확인하실 수 있습니다.";
        } else if (userInput.contains("안녕") || userInput.contains("hello")) {
            response = "안녕하세요! 고객님. 무엇을 도와드릴까요?";
        } else if (userInput.contains("누구") || userInput.contains("뉘귀") || userInput.contains("who")) {
            response = "저는 당신의 문의사항을 해결해드릴 챗봇입니다!";
        } else if (userInput.contains("IQ") || userInput.contains("아이큐") ) {
            response = "안녕하세요! 고객님. 제 IQ 는 한 5000정도 되지 않을까요?";
        } else if (userInput.contains("윤수빈") || userInput.contains("구반")) {
            response = "윤수빈 금지!";
        } else if (userInput.contains("김범수") || userInput.contains("김밤수") || userInput.contains("김봄수")) {
            response = "윤수빈 금지!";
        } else if (userInput.contains("어디") || userInput.contains("where") || userInput.contains("ㅇㄷ")) {
            response = "저는 인터넷 공간속 어딘가에 있습니다.";
        } else if (userInput.contains("ID") || userInput.contains("PW") || userInput.contains("password") || userInput.contains("패스워드") || userInput.contains("암호") || userInput.contains("아이디") || userInput.contains("계정") ) {
            response = "ID / PW 는 우측 상단 Login 버튼을 클릭하시면 로그인 페이지에서 하단의 아이디 / 비밀번호 찾기 버튼을 클릭하시면 이용이 가능하십니다!";
        } else {
            response = "질문을 이해할 수 없습니다. 고객센터의 전화번호는 031-111-1234 입니다.";
        }

        return response;
    }
}