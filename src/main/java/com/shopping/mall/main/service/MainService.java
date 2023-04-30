package com.shopping.mall.main.service;

import com.shopping.mall.main.mapper.MainMapper;
import com.shopping.mall.main.vo.MainVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service("mainService")
public class MainService {

    @Autowired
    MainMapper mainMapper;

    public List<MainVo> getMainList(int startIndex, int pageSize) {

        return mainMapper.getMainList(startIndex, pageSize);
    }

    public List<MainVo> getCategoryList() {

        return mainMapper.getCategoryList();
    }

    public int getTotalCount() {

        return mainMapper.getTotalCount();
    }

    public MainVo getMainDetail(int productSeq) {

        mainMapper.updateViews(productSeq);

        return mainMapper.getMainDetail(productSeq);
    }

    public List<MainVo> getMainPreview(int productSeq) {

        return mainMapper.getMainPreview(productSeq);
    }
}
