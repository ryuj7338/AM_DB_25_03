package org.example;

import java.sql.Connection;

public class ArticleService {

    private ArticleDao articleDao;

    public ArticleService() {
        this.articleDao = new ArticleDao();
    }

    public int doWrite(Connection conn, String title, String body) {
        return articleDao.doWrite(conn, title, body);
    }
    public int doList(Connection conn, String title) {

        return articleDao.doList();
    }
}
