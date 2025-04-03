package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.sevice.ArticleService;


import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController {

    private Scanner sc;
    private ArticleService articleService;


    public ArticleController() {
        this.sc = Container.sc;
        this.articleService = Container.articleService;
    }

    public void doWrite() {
        System.out.println("==글쓰기==");
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        int id = articleService.doWrite(title, body);

        System.out.println(id + "번 글이 생성되었습니다.");
    }

    public void showList() {
        System.out.println("==목록==");

        List<Article> articles = articleService.getArticles();


        if (articles.size() == 0) {
            System.out.println("게시글이 없습니다");
            return;
        }

        System.out.println("  번호  /   제목  ");
        for (Article article : articles) {
            System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
        }
    }

    public void doModify(String cmd) {

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력하세요");
            return;
        }

        Map<String, Object> articleMap = articleService.getArticleById(id);

        if (articleMap.isEmpty()) {
            System.out.println(id + "번 글은 없습니다.");
            return;
        }

        System.out.println("==수정==");
        System.out.print("새 제목 : ");
        String title = sc.nextLine().trim();
        System.out.print("새 내용 : ");
        String body = sc.nextLine().trim();


        articleService.doUpdate(id, title, body);

        System.out.println(id + "번 글이 수정되었습니다.");

    }


    public void showDetail(String cmd) {

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return ;
        }



        System.out.println("== 상세보기 ==");

        Map<String, Object> articleMap = articleService.getArticleById(id);


        if (articleMap.isEmpty()) {
            System.out.println(id + "번 글은 없습니다.");
            return;
        }

        Article article = new Article(articleMap);

        System.out.println("번호 : " + article.getId());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
    }

    public void doDelete(String cmd) {

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return;
        }

        Map<String, Object> articleMap = articleService.getArticleById(id);

        if (articleMap.isEmpty()) {
            System.out.println(id + "번 글은 없습니다.");
            return;
        }


        System.out.println("==삭제==");

        articleService.doDelete(id);

        System.out.println(id + "번 글이 삭제되었습니다.");

    }

}
