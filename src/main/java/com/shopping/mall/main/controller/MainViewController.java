package com.shopping.mall.main.controller;

import com.shopping.mall.main.service.MainService;
import com.shopping.mall.main.vo.MainVo;
import com.shopping.mall.util.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@AllArgsConstructor
@Controller
public class MainViewController {

    @Autowired
    MainService mainService;

    @RequestMapping("/")
    public String main(@RequestParam(defaultValue = "1") int pageNum, Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        model.addAttribute("loginVo", session.getAttribute("loginVo"));

        int pageSize = 12; // 한 페이지에 보여줄 데이터 개수
        int totalItemCount = mainService.getTotalCount(); // 전체 데이터 개수
        int totalPages = (int) Math.ceil((double) totalItemCount / pageSize); // 전체 페이지 개수
        int startIndex = (pageNum - 1) * pageSize;

        List<MainVo> mainList = mainService.getMainList(startIndex, pageSize); // pageNum 에 해당하는 페이지 데이터 가져오기

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setTotalPages(totalPages);

        model.addAttribute("mainList", mainList);
        model.addAttribute("pageInfo", pageInfo);

        return "main/main.html";
    }

    @RequestMapping("/mainDetail")
    public String mainDetail(@RequestParam("productSeq") int productSeq, Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        model.addAttribute("loginVo", session.getAttribute("loginVo"));

        // productSeq 값을 이용해 데이터 조회 및 처리
        MainVo mainVo = mainService.getMainDetail(productSeq);
        List<MainVo> previewList = mainService.getMainPreview(productSeq);
        List<MainVo> categoryList = mainService.getCategoryList(); // category 데이터 가져오기

        model.addAttribute("mainVo", mainVo);
        model.addAttribute("previewList", previewList);
        model.addAttribute("categoryList", categoryList);
        return "main/main-detail.html";
    }

    @GetMapping(value = "/search")
    public String search(@RequestParam("query") String searchWord, Model model) {

        // 검색어(searchWord)를 이용한 검색 로직 수행
        List<MainVo> searchResult = mainService.search(searchWord);
        model.addAttribute("mainList", searchResult);
        model.addAttribute("message",searchWord + "에 대한 검색 결과가 없습니다.");

        return "main/searchResult.html";
    }
}
