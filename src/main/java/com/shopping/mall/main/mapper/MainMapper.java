package com.shopping.mall.main.mapper;

import com.shopping.mall.main.vo.MainVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MainMapper {

    List<MainVo> getMainList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int getTotalCount();

    MainVo getMainDetail(int mainSeq);

    int updateViews(int mainSeq);

    List<MainVo> getMainPreview(int mainSeq);
}
