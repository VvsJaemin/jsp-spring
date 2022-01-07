package com.example.news.controller;

import com.example.news.dao.NewsDAO;
import com.example.news.domain.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsWebController {

    final NewsDAO dao;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public NewsWebController(NewsDAO dao) {
        this.dao = dao;
    }

    @Value("${news.imgdir")
    String fdir;


    @PostMapping("/add")
    public String addNews(@ModelAttribute News news, Model model, @RequestParam("file") MultipartFile file) {
        try {
            // 저장 파일 객체 생성
            File dest = new File(fdir + "/" + file.getOriginalFilename());

            // 파일 저장
            file.transferTo(dest);

            //news 객체에 파일 이름 저장
            news.setImg(dest.getName());
            dao.addNews(news);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("뉴스 추가 과정에서 문제 발생");
            model.addAttribute("error", "뉴스가 정상적으로 동작 안됨");
        }

        return "redirect:/news/list";
    }

    @GetMapping("/delete/{aid}")
    public String deleteNews(@PathVariable int aid, Model model) {
        try {
            dao.delNews(aid);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("뉴스 삭제 이상");
            model.addAttribute("error", "뉴스가 정상적으로 삭제 안됨");
        }
        return "redirect:/news/list";
    }

    @GetMapping("/list")
    public String listNews(Model model) {
        List<News> list;
        try {
            list = dao.getAll();
            model.addAttribute("newslist", list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("뉴스 목록 이상");
            model.addAttribute("error", "뉴스 목록이 정상 아님");
        }
        return "news/newsList";
    }

    @GetMapping("/{aid}")
    public String getNews(@PathVariable int aid, Model model) {
        try {
            News n = dao.getNews(aid);
            model.addAttribute("news", n);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("뉴스를 가져오는 과정 이상");
            model.addAttribute("error", "뉴스를 정상적으로 가져오지 못함");
        }
        return "news/newsView";
    }
}
