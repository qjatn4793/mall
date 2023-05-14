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
            response = "ID / PW 는 우측 상단 Login 버튼을 클릭하시면 접근할 수 있는 로그인 페이지에서 하단의 아이디 / 비밀번호 찾기 버튼을 클릭하시면 이용이 가능하십니다!";
        } else if (userInput.contains("배송")) {
            response = "배송은 주문 접수 후 일반 상품은 1~3일(영업일 기준) 내에, 대형 상품은 3~5일(영업일 기준) 내에 도착할 예정입니다. 배송 상태를 확인하려면 마이페이지에서 주문 내역을 확인하실 수 있습니다.";
        } else if (userInput.contains("할인")) {
            response = "할인은 상품마다 다르며, 특정 기간에는 할인 행사를 진행하기도 합니다. 현재 진행 중인 할인 정보를 확인하려면 홈페이지 상단에 배너나 이벤트 섹션을 확인해 주세요.";
        } else if (userInput.contains("교환")) {
            response = "상품 교환을 원하실 경우, 상품 수령 후 7일 이내에 고객센터로 문의해 주시면 도움을 드리겠습니다. 단, 상품의 불량이나 오배송 등의 경우에 한하여 교환이 가능합니다.";
        } else if (userInput.contains("반품")) {
            response = "상품 반품을 원하실 경우, 상품 수령 후 7일 이내에 고객센터로 문의해 주시면 반품 절차에 대해 안내해 드리겠습니다. 반품 사유에 따라 반품 비용이 발생할 수 있으니 주의해 주세요.";
        } else if (userInput.contains("재고")) {
            response = "재고는 상품마다 다를 수 있으며, 품절된 상품의 경우에는 구매가 불가능합니다. 상품 페이지에서 재고 여부를 확인하실 수 있습니다. 또한, 재고 보충이 예정되어 있는 경우에는 홈페이지 상단에 공지가 게시됩니다.";
        } else if (userInput.contains("결제")) {
            response = "결제는 상품 구매 시에 주로 신용카드, 계좌이체, 휴대폰 소액결제 등 다양한 결제 수단을 이용할 수 있습니다. 상품을 선택한 후에는 결제 페이지에서 원하시는 결제 방법을 선택하시고 안내에 따라 진행해 주세요.";
        } else if (userInput.contains("반환금")) {
            response = "상품 반품 시에는 환불 절차가 진행되며, 해당 상품의 결제 금액에서 반품 처리 후 환불되는 금액을 제공해 드립니다. 자세한 환불 절차와 시일은 고객센터로 문의해 주시면 안내해 드리겠습니다.";
        } else if (userInput.contains("야옹") || userInput.contains("냐옹") || userInput.contains("냐옹이") || userInput.contains("야옹이")) {
            response = "야옹! 고양이가 귀여워요~ 고양이를 키우고 계시거나, 고양이 상품에 관심이 있으신가요? 궁금한 점이 있으시면 언제든지 물어보세요!";
        } else if (userInput.contains("멍멍") || userInput.contains("왈왈") || userInput.contains("강아지") || userInput.contains("멍멍이")) {
            response = "멍멍! 강아지가 좋아요~ 강아지를 키우고 계시거나, 강아지 상품에 관심이 있으신가요? 궁금한 점이 있으시면 언제든지 물어보세요!";
        } else if (userInput.contains("봄") || userInput.contains("봄날") || userInput.contains("꽃")) {
            response = "봄은 꽃이 피는 아름다운 계절입니다. 따뜻한 날씨와 함께 다양한 꽃들이 만개하여 경치가 아름답습니다. 봄에는 튤립, 벚꽃, 진달래 등 다양한 꽃 구경을 즐길 수 있습니다. 봄에 어울리는 상품을 찾으시거나 봄날을 기리는 이벤트에 참여해 보세요!";
        } else if (userInput.contains("여름") || userInput.contains("더운") || userInput.contains("수영")) {
            response = "여름은 더운 날씨와 함께 시원한 바다나 수영장에서 즐기기 좋은 계절입니다. 여름 휴가를 맞이하여 해변용품, 수영복, 선크림 등 여름에 필요한 상품들이 인기를 끌고 있습니다. 여름 휴가를 떠나기 전에 필요한 준비물을 확인해 보세요!";
        } else if (userInput.contains("가을") || userInput.contains("단풍") || userInput.contains("산책")) {
            response = "가을은 단풍이 아름다운 계절로, 시원한 날씨에는 산책이나 등산을 즐기기에 딱 좋습니다. 가을은 또한 수확의 계절로서 각종 과일이 풍부해집니다. 가을에는 단풍 구경이나 가을 축제에 참여하여 풍성한 가을을 즐겨보세요!";
        } else if (userInput.contains("겨울") || userInput.contains("추운") || userInput.contains("눈")) {
            response = "겨울은 추운 날씨와 함께 눈이 내리는 멋진 계절입니다. 스키나 스노보드와 같은 동계 스포츠를 즐기기에 딱 좋은 계절이기도 합니다. 겨울에는 따뜻한 옷, 목도리, 장갑 등 보온용품이 필요합니다. 겨울을 즐기는 다양한 상품을 확인해 보세요!";
        } else {
            response = "질문을 이해할 수 없습니다. 고객센터의 전화번호는 031-111-1234 입니다.";
        }

        return response;
    }
}