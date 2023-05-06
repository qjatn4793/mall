package com.shopping.mall.user.controller;

import com.shopping.mall.configuration.PasswordUtil;
import com.shopping.mall.configuration.SHA256;
import com.shopping.mall.login.vo.LoginVo;
import com.shopping.mall.main.service.MainService;
import com.shopping.mall.main.vo.MainVo;
import com.shopping.mall.user.service.UserService;
import com.shopping.mall.user.vo.PayVo;
import com.shopping.mall.user.vo.TermsVo;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@RestController
@ResponseBody
public class UserController {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Autowired
    private MainService mainService;

    @PostMapping("/userRegister")
    public int userRegister(@RequestBody LoginVo loginVo){

        String userId = loginVo.getUserId();
        String userBirth = loginVo.getUserBirth();
        String userName = loginVo.getUserName();
        String userPhone = loginVo.getUserPhone();

        final String checkNum = "[0-9]+"; // 숫자만 있는지 체크
        final String checkString = "[a-zA-Z0-9ㄱ-힣\\s]"; // 특수문자 체크
        final String checkString2 = "[A-Za-z0-9]"; // 숫자 문자 포함 체크

        Matcher matchTest;
        Matcher matchTest2;
        Matcher matchTest3;
        matchTest = Pattern.compile(checkString).matcher(userName); // 이름 공백 포함 특수문자가 없으면 true
        matchTest2 = Pattern.compile(checkString).matcher(userId); // ID 공백 포함 특수문자 없으면 true
        matchTest3 = Pattern.compile(checkString2).matcher(userId); // ID 에 문자 숫자 포함일경우 true

        if (userService.userIdCheck(loginVo) == 0) { // 유저 중복 체크 0 일경우 없음
            if (Pattern.matches(checkNum, userBirth) && Pattern.matches(checkNum, userPhone)) { // 생년월일 숫자만 있는지 체크 휴대폰번호 숫자만 있는지 체크
                if (matchTest.find() == true && matchTest2.find() == true && matchTest3.find() == true) { //이름 공백 포함 특수문자가 없으면 true, ID 공백 포함 특수문자 없으면 true, ID 에 문자 숫자 포함일경우 true

                    String encryptedPassword = PasswordUtil.sha256(loginVo.getUserPw()); // 패스워드 암호화

                    loginVo.setUserPw(encryptedPassword);

                    return userService.userRegister(loginVo);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    @PutMapping("/userRegister")
    public int userUpdate(HttpServletRequest request, @RequestBody LoginVo loginVo){

        HttpSession session = request.getSession();
        SHA256 sha256 = new SHA256();

        if (userService.userIdCheck(loginVo) == 1) { // 유저 중복 체크 1 일경우 있음

            try {
                loginVo.setUserPw(sha256.encrypt(loginVo.getUserPw()));
            }catch (Exception e) {
                System.out.println(e);
            }

            int userUpdate = userService.userUpdate(loginVo);

            if (userUpdate == 1) {
                session.removeAttribute("loginVo");
                session.invalidate();
                return userUpdate;
            } else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    @PostMapping("/userIdCheck")
    public int userIdCheck(@RequestBody LoginVo loginVo){

        String userId = loginVo.getUserId();

        final String checkString = "[a-zA-Z0-9ㄱ-힣\\s]"; // 특수문자 체크

        Matcher matchTest2;
        matchTest2 = Pattern.compile(checkString).matcher(userId); // ID 공백 포함 특수문자 없으면 true

        if (matchTest2.find() == true) {
            return userService.userIdCheck(loginVo);
        }else {
            return 0;
        }
    }

    @PostMapping("/uploadProfileImg")
    public ResponseEntity<String> uploadProfileImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        LoginVo loginVo = new LoginVo();

        String uuid = UUID.randomUUID().toString();
        String filename = uuid + "_" + file.getOriginalFilename();

        Path path = Paths.get("/manager/image/user" + "/" + filename);

        LoginVo sessionLoginVo = (LoginVo)request.getSession().getAttribute("loginVo");

        if(sessionLoginVo == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }

        loginVo.setUserProfile(path.toString());
        loginVo.setUserId(sessionLoginVo.getUserId());

        userService.updateUserImg(loginVo);

        return uploadFile(file, filename, request);
    }

    /*파일업로드 공통 메소드*/
    private ResponseEntity<String> uploadFile(MultipartFile file, String filename, HttpServletRequest request) {
        try {
            String uploadDir = env.getProperty("shared.image.upload-dir");
            String extension = FilenameUtils.getExtension(filename);
            if (!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("png")) {
                request.getSession().invalidate();
                return ResponseEntity.badRequest().body("Only jpg or png files are allowed");
            }
            Path path = Paths.get(uploadDir + "/user/" + filename);
            Files.write(path, file.getBytes());
            request.getSession().invalidate();
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            request.getSession().invalidate();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }

    @PostMapping("/buy")
    @ResponseBody
    public int buy(@RequestBody PayVo payVo, HttpServletRequest request) {
        LoginVo loginVo = (LoginVo)request.getSession().getAttribute("loginVo");
        if(loginVo == null || loginVo.getUserId() == null) {
            return 0; // 로그인정보 비어있음
        }else {
            //System.out.println(loginVo.getUserId());
            //System.out.println(payVo.getProductSeq());
            //System.out.println(payVo.getOrderCount());


            MainVo mainVo = mainService.getMainDetail(payVo.getProductSeq());
            String orderUserId = loginVo.getUserId();
            int productCount = mainVo.getProductCount();
            int orderCount = payVo.getOrderCount();
            int productPrice = mainVo.getProductPrice();
            int realPrice = 0;

            if (orderCount > 0 || productPrice > 0) {
                realPrice = orderCount * productPrice;
            }

            if(orderCount > productCount) {
                return 2; // 재고 부족
            }else {
                payVo.setProductCount(productCount);
                payVo.setOrderUserId(orderUserId);
                payVo.setOrderPrice(realPrice);

                int result = userService.insertOrder(payVo);

                if(result > 0) {
                    return 1; // 구매 성공
                }else {
                    return 3; // 구매 실패
                }
            }
        }
    }

    @PutMapping("/cancleOrder")
    public ResponseEntity<String> cancleOrder(@RequestParam int orderSeq, @RequestParam int orderStatus) {

        PayVo payVo = new PayVo();
        payVo.setOrderSeq(orderSeq);
        payVo.setOrderStatus(orderStatus);

        int orderResult = userService.cancleOrder(payVo);

        if (orderResult > 0) {
            return ResponseEntity.ok().body("주문 취소 성공");
        }else {
            return ResponseEntity.ok().body("주문 취소 실패");
        }
    }

    @GetMapping("/terms")
    @ResponseBody
    public TermsVo terms(@RequestParam("termsSeq") int termsSeq) {
        TermsVo termsVo = userService.getTerms(termsSeq);
        return termsVo;
    }
}