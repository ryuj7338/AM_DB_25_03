package org.example;

import org.example.controller.ArticleController;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public void run() {
        System.out.println("==프로그램 시작==");
        Scanner sc = new Scanner(System.in);

//        Controller controller = null;

        while (true) {
            System.out.print("명령어 > ");
            String cmd = sc.nextLine().trim();

            Connection conn = null;

            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            String url = "jdbc:mariadb://127.0.0.1:3306/AM_DB_25_03?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

            try {
                conn = DriverManager.getConnection(url, "root", "");

                int actionResult = action(conn, sc, cmd);

                if (actionResult == -1) {
                    System.out.println("==프로그램 종료==");
                    sc.close();
                    break;
                }

            } catch (SQLException e) {
                System.out.println("에러 1 : " + e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int action(Connection conn, Scanner sc, String cmd) {

        if (cmd.equals("exit")) {
            return -1;
        }

        ArticleController articleController = new ArticleController(sc, conn);
        MemberController memberController =  new MemberController(sc,conn);

        if (cmd.equals("member join")) {
            memberController.doJoin();


        } else if (cmd.equals("article write")) {
            articleController.doWrite();

        } else if (cmd.equals("article list")) {
            articleController.doList();
            System.out.println("==목록==");

            List<Article> articles = new ArrayList<>();



            List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

            for (Map<String, Object> articleMap : articleListMap) {
                articles.add(new Article(articleMap));
            }

            if (articles.size() == 0) {
                System.out.println("게시글이 없습니다");
                return 0;
            }

            System.out.println("  번호  /   제목  ");
            for (Article article : articles) {
                System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
            }
        } else if (cmd.startsWith("article modify")) {
            articleController.doModify();

            int id = 0;

            List<Article> articles = new ArrayList<>();

            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("번호는 정수로 입력해");
                return 0;
            }

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM article");
            sql.append("WHERE id = ?;", id);

            Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

            if (articleMap.isEmpty()) {
                System.out.println(id + "번 글은 없습니다.");
                return 0;
            }

            System.out.println("==수정==");
            System.out.print("새 제목 : ");
            String title = sc.nextLine().trim();
            System.out.print("새 내용 : ");
            String body = sc.nextLine().trim();

            sql = new SecSql();

            PreparedStatement pstmt = null;
            sql.append("UPDATE article");
            sql.append("SET updateDate = NOW(),");
            if (title.length() > 0) {
                sql.append("title = ?,", title);
            }
            if (body.length() > 0) {
                sql.append("`body` = ?", body);
            }
            sql.append("where id = ?;", id);


            DBUtil.update(conn, sql);

            System.out.println(id + "번 글이 수정되었습니다.");
        } else if (cmd.startsWith("article detail")) {
            articleController.doDetail();
            int id = 0;

            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("번호는 정수로 입력해");
                return 0;
            }

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM article");
            sql.append("WHERE id = ?;", id);

            Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

            if (articleMap.isEmpty()) {
                System.out.println(id + "번 글은 없습니다.");
                return 0;
            }

            Article article = new Article(articleMap);

            System.out.println("번호 : " + article.getId());
            System.out.println("제목 : " + article.getTitle());
            System.out.println("제목 : " + article.getTitle());
            System.out.println("내용 : " + article.getBody());


        } else if (cmd.startsWith("article delete")) {
            articleController.doDelete();
            int id = 0;


            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("번호는 정수로 입력해");
                return 0;
            }

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM article");
            sql.append("WHERE id = ?;", id);

            Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

            if (articleMap.isEmpty()) {
                System.out.println(id + "번 글은 없습니다.");
                return 0;
            }


            System.out.println("==삭제==");
            sql = new SecSql();
            sql.append("DELETE FROM article");
            sql.append("WHERE id = ?;", id);

            DBUtil.delete(conn, sql);

            System.out.println(id + "번 글이 삭제되었습니다.");

        }
        return 0;
    }
}