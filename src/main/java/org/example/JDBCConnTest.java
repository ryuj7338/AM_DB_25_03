package org.example;

import java.sql.*;
import java.util.Scanner;

public class JDBCConnTest {
    public static void main(String[] args) {
        insertForStatement();
    }

    public static void insertForStatement() {


        Connection conn = null;

        Statement stmt = null;

        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://127.0.0.1:3306/AM_DB_25_03?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "");
            System.out.println("연결 성공!");

            while (true){
                Scanner sc = new Scanner(System.in);
                System.out.print("명령어) ");
                String cmd = sc.nextLine();

                if (cmd.equals("article write")) {

                    String sql = "INSERT INTO article" +
                            " SET regDate = NOW()," +
                            "updateDate = NOW()," +
                            "title = CONCAT('제목',SUBSTRING(RAND() * 1000 FROM 1 FOR 2))," +
                            "content = CONCAT('내용',SUBSTRING(RAND() * 1000 FROM 1 FOR 2))";


                    System.out.println(sql);

                    stmt = conn.createStatement();

                    int result = stmt.executeUpdate(sql);

                    System.out.println("result: " + result);

                    if (result > 0) {
                        System.out.println("입력 성공");
                    } else {
                        System.out.println("입력 실패");
                    }

                } else if (cmd.equals("article list")) {
                    String sql = "select * from article";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        String title2 = rs.getString("title");
                        String content2 = rs.getString("content");
                        System.out.println(title2 + "  " + content2 + " ");
                    }
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
    }
}