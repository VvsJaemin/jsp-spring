package com.example.news.controller;

import com.example.news.dao.NewsDAO;
import com.example.news.domain.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsApiController {

    final NewsDAO dao;

    @Autowired
    public NewsApiController(NewsDAO dao) {
        this.dao = dao;
    }

    @PostMapping
    public String addNews(@RequestBody News news) throws Exception {
        dao.addNews(news);
        return "뉴스 등록";
    }

    @DeleteMapping("{aid}")
    public String delNews(@PathVariable("aid") int aid) throws SQLException {
        dao.delNews(aid);
        return "뉴스 삭제";
    }

    @GetMapping
    public List<News> getNewsList() throws Exception {
        List<News> newsList = null;
        newsList = dao.getAll();

        return newsList;
    }

    @GetMapping("{aid}")
    public News getNews(@PathVariable("aid") int aid) throws SQLException {
       News news = dao.getNews(aid);

        return news;
    }
}
