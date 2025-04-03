package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.service.ArticleService;

import java.util.List;
import java.util.Scanner;

public class ArticleController {

    private Scanner sc;
    private ArticleService articleService;
    private List<Article> articles;

    public ArticleController() {
        this.sc = Container.sc;
        this.articleService = Container.articleService;
    }

    public void doWrite() {

        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        }

        System.out.println("==글쓰기==");
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        int memberId = Container.session.loginedMemberId;

        int id = articleService.doWrite(memberId, title, body);

        System.out.println(id + "번 글이 생성되었습니다.");
    }

    public void showList(String cmd) {
        System.out.println("==목록==");

//        List<Article> forPrintArticles = articleService.getArticles();

        String[] comBits = cmd.split(" ");

        int page = 1;
        String searchKeyword = "";

        if (comBits.length >= 3) {
            page = Integer.parseInt(comBits[2]);
        }
        if (comBits.length >= 4) {
            searchKeyword = comBits[3];
        }

        int itemsInPage = 10;

        List<Article> articles = articleService.getForPrintArticles(page, itemsInPage, searchKeyword);


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

        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        }

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력하세요");
            return;
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.println(id + "번 글은 없습니다.");
            return;
        }
        if (article.getMemberId() != Container.session.loginedMemberId) {
            System.out.println("권한이 없습니다.");
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
            return;
        }

        System.out.println("== 상세보기 ==");

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.println(id + "번 글은 없습니다.");
            return;
        }


        System.out.println("번호 : " + article.getId());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
    }

    public void doDelete(String cmd) {

        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        }

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력하세요");
            return;
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.println(id + "번 글은 없습니다.");
            return;
        }
        if (article.getMemberId() != Container.session.loginedMemberId) {
            System.out.println("권한이 없습니다.");
            return;
        }


        System.out.println("==삭제==");

        articleService.doDelete(id);

        System.out.println(id + "번 글이 삭제되었습니다.");

    }

}
