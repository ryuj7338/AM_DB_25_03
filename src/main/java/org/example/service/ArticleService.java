package org.example.service;

import org.example.container.Container;
import org.example.dao.ArticleDao;
import org.example.dto.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {

    private ArticleDao articleDao;



    public ArticleService() {

        this.articleDao = Container.articleDao;
    }

    public int doWrite(int memberId, String title, String body) {
        return articleDao.doWrite(memberId,title, body);

    }

    public List<Article> getArticles() {

        return articleDao.getArticles();
    }

    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void doUpdate(int id, String title, String body) {
        articleDao.doUpdate(id, title, body);
    }

    public void doDelete(int id) {
        articleDao.doDelete(id);
    }
    public List<Article> getForPrintArticles(int page, int itemsInPage, String searchKeyword) {
        int limitFrom = (page - 1) * itemsInPage;
        int limitTake = itemsInPage;

        Map<String, Object> args = new HashMap<>();
        args.put("limitFrom", limitFrom);
        args.put("limitTake", limitTake);
        args.put("searchKeyword", searchKeyword);

        return articleDao.getForPrintArticles(args);

    }
}