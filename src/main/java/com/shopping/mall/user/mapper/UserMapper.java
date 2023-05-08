package com.shopping.mall.user.mapper;

import com.shopping.mall.login.vo.LoginVo;
import com.shopping.mall.main.vo.MainVo;
import com.shopping.mall.user.vo.PayVo;
import com.shopping.mall.user.vo.TermsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    int userIdCheck(LoginVo loginVo);

    int userRegister(LoginVo loginVo);

    int userUpdate(LoginVo loginVo);

    int updateUserImg(LoginVo loginVo);

    int insertOrder(PayVo payVo);

    int updateUserTotalPrice(PayVo payVo);

    List<PayVo> getOrderList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("userId") String userId);

    PayVo getSelectVo(int productSeq);

    int getOrderTotalCount(String userId);

    String getCategoryName(int categorySeq);


    PayVo selectOrderVo(int orderSeq);

    int cancleOrder(PayVo payVo);

    int updateProductCount(PayVo payVo);

    int updateOrderStatus(PayVo payVo);


    TermsVo getTerms(int termsSeq);

    MainVo getViewHistory(int productSeq);
}
