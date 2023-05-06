package com.shopping.mall.user.service;

import com.shopping.mall.login.vo.LoginVo;
import com.shopping.mall.main.mapper.MainMapper;
import com.shopping.mall.user.mapper.UserMapper;
import com.shopping.mall.user.vo.PayVo;
import com.shopping.mall.user.vo.TermsVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

            return userMapper.insertOrder(payVo);
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
}
