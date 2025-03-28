package org.example;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("==프로그램 시작==");
        Scanner sc = new Scanner(System.in);


        int lastArticleId = 0;
        List<Article> articles = new ArrayList<Article>();

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();


            if (cmd.equals("exit")) {
                break;
            }
            if (cmd.equals("article write")) {
                int id = lastArticleId + 1;


                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String content = sc.nextLine().trim();


                System.out.println(id + "번 글이 작성되었습니다.");
                lastArticleId++;

                /////////////////////////////////////////////////////////////

                Connection conn = null;

                Statement stmt = null;


                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_DB_25_03?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공!");


                    if (cmd.equals("article write")) {

                        String sql = "INSERT INTO article" +
                                " SET regDate = NOW()," +
                                "updateDate = NOW()," +
                                "title = '" + title + "'," +
                                "content = '" + content + "';";


                        System.out.println(sql);

                        stmt = conn.createStatement();

                        int result = stmt.executeUpdate(sql);

                        System.out.println("result: " + result);

                        if (result > 0) {
                            System.out.println("입력 성공");
                        } else {
                            System.out.println("입력 실패");
                        }

                    }


                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            } else if (cmd.equals("article list")) {


                Connection conn = null;

                Statement stmt = null;

                ResultSet rs = null;

                List<Article> articleList = new ArrayList<Article>();

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_DB_25_03?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");



                    String sql = "select * from article" + " order by id desc";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);


                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String regDate = rs.getString("regDate");
                        String updateDate = rs.getString("updateDate");
                        String title = rs.getString("title");
                        String content = rs.getString("content");

                        Article article = new Article(id, regDate, updateDate, title, content);

                        articleList.add(article);

                    }
                    System.out.println("   번호   /   제목   ");

                    for (int i = 0; i < articleList.size(); i++) {
                        System.out.printf("   %d   /   %s    \n", articleList.get(i).getId(), articleList.get(i).getTitle());
                    }

                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                }
            }else if (cmd.startsWith("article modify")) {

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Connection conn = null;

                Statement stmt;

                ResultSet rs = null;

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_DB_25_03?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");

                    System.out.print("새 제목: ");
                    String title = sc.nextLine().trim();
                    System.out.print("새 내용: ");
                    String content = sc.nextLine().trim();



                    String sql = "UPDATE article" +
                            " SET updateDate = NOW()," +
                            "title = '" + title + "'," +
                            "content = '" + content + "'" +
                            " WHERE id = " + id + ";";

                    System.out.println(sql);


                    conn = DriverManager.getConnection(url, "root", "");
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);





                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
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
    }
}




class Article {
    private int id;
    private String regDate;
    private String updateDate;
    private String title;
    private String content;

    public Article(int id, String regDate, String updateDate, String title, String content) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.content = content;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}