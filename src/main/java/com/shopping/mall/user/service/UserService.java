package com.shopping.mall.user.service;

import com.shopping.mall.configuration.PasswordUtil;
import com.shopping.mall.login.vo.LoginVo;
import com.shopping.mall.main.mapper.MainMapper;
import com.shopping.mall.main.vo.MainVo;
import com.shopping.mall.user.mapper.UserMapper;
import com.shopping.mall.user.vo.PayVo;
import com.shopping.mall.user.vo.TermsVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@AllArgsConstructor
@Transactional
@Service("userService")
public class UserService {

    UserMapper userMapper;

    MainMapper mainMapper;

    public int userIdCheck(LoginVo loginVo){

        return userMapper.userIdCheck(loginVo);
    }

    public int userRegister(LoginVo loginVo){

        return userMapper.userRegister(loginVo);
    }

    public int userUpdate(LoginVo loginVo){

        return userMapper.userUpdate(loginVo);
    }

    public int updateUserImg(LoginVo loginVo) {

        return userMapper.updateUserImg(loginVo);
    }

    public int insertOrder(PayVo payVo) {

        int productResult = mainMapper.updateProductCount(payVo);

        if(productResult > 0) {

            int updateUserTotalPrice = userMapper.updateUserTotalPrice(payVo);

            if (updateUserTotalPrice > 0) {
                return userMapper.insertOrder(payVo);
            }else {
                return 0;
            }

        }else {
            return 0;
        }
    }

    public int getOrderTotalCount(String userId) {

        return userMapper.getOrderTotalCount(userId);
    }

    public List<PayVo> getOrderList(int startIndex, int pageSize, String userId) {

        List<PayVo> orderList = userMapper.getOrderList(startIndex, pageSize, userId);

        for (PayVo order : orderList) {
            PayVo orderVo = userMapper.getSelectVo(order.getProductSeq());
            order.setCategorySeq(orderVo.getCategorySeq());
            order.setProductPrice(orderVo.getProductPrice());
            order.setProductTitle(orderVo.getProductTitle());
            order.setCategoryName(userMapper.getCategoryName(order.getCategorySeq()));
        }

        return orderList;
    }

    public int cancleOrder(PayVo payVo) {
        PayVo resultVo = userMapper.selectOrderVo(payVo.getOrderSeq());
        PayVo productVo = userMapper.getSelectVo(resultVo.getProductSeq());
        int productPrice = productVo.getProductPrice();
        int orderCount = resultVo.getOrderCount();
        resultVo.setProductPrice(productPrice * orderCount);

        if(userMapper.cancleOrder(resultVo) > 0){
            if (userMapper.updateProductCount(resultVo) > 0){
                return userMapper.updateOrderStatus(payVo);
            } else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    public TermsVo getTerms(int termsSeq) {

        return userMapper.getTerms(termsSeq);
    }

    public MainVo getViewHistory(int productSeq) {
        MainVo mainVo = userMapper.getViewHistory(productSeq);
        mainVo.setCategoryName(userMapper.getCategoryName(mainVo.getCategorySeq()));
        return mainVo;
    }

    public LoginVo userEmailCheck(String userEmail) {

        LoginVo loginVo = userMapper.userEmailCheck(userEmail);

        if (loginVo == null) {
            return null;
        }else {
            // 랜덤한 비밀번호 생성
            String newPassword = generateRandomPassword();

            int sendEmailResult = sendEmail(loginVo, newPassword);

            if (sendEmailResult > 0) {
                int changePasswordResult = changePassword(loginVo.getUserId(), newPassword);
                if(changePasswordResult > 0) {
                    return loginVo;
                }else {
                    return null;
                }

            }else {
                return null;
            }
        }
    }

    // 랜덤한 비밀번호 생성 메서드
    private String generateRandomPassword() {
        // 비밀번호 생성 로직 구현
        // 예시: 8자리의 랜덤한 알파벳+숫자 조합 생성
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    // 비밀번호 변경 메서드
    private int changePassword(String userEmail, String newPassword) {

        LoginVo loginVo = new LoginVo();

        loginVo.setUserIdCheck(userEmail);

        loginVo.setUserPw(PasswordUtil.sha256(newPassword));

        return userMapper.userUpdate(loginVo);
    }

    // 이메일 전송 메서드
    private int sendEmail(LoginVo loginVo, String newPassword) {
        String host = "smtp.gmail.com"; // SMTP 서버 호스트 주소
        String port = "587"; // SMTP 서버 포트 번호
        String username = "qjatn4792@gmail.com"; // 이메일 계정 아이디
        String password = "yvpbynfareeafawe"; // 이메일 계정 비밀번호

        // 이메일 속성 설정
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // 세션 생성
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 이메일 메시지 생성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(loginVo.getUserEmail()));
            message.setSubject("Mall 사이트 패스워드 변경"); // 이메일 제목
            message.setText("Mall 사이트에 가입된 회원님의 계정은" + loginVo.getUserId() + "입니다.\n변경된 패스워드는 : " + newPassword + "입니다."); // 이메일 내용

            // 이메일 전송
            Transport.send(message);

            return 1; // 이메일 전송 성공
        } catch (MessagingException e) {
            e.printStackTrace();
            return 0; // 이메일 전송 실패
        }
    }
}
