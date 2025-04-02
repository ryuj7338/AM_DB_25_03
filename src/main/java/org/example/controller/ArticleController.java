package org.example.controller;

import org.example.ArticleService;

import java.sql.Connection;
import java.util.Scanner;

public class ArticleController {

    private Connection conn;
    private Scanner sc;

    private ArticleService articleService;

    public ArticleController(Scanner sc, Connection conn) {
        this.sc = sc;
        this.conn = conn;
        this.articleService = new ArticleService();
    }

    public void doWrite() {
        System.out.println("==글쓰기==");
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        int id = articleService.doWrite(conn, title, body);

        System.out.println(id + "번 글이 생성됨");
    }

    public void doList() {
        
    }

    public void doModify() {
    }

    public void doDetail() {
    }

    public void doDelete() {
    }
}
