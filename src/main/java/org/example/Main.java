package org.example;


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

                Article article = new Article(id, title, content);
                articles.add(article);
                lastArticleId++;
            }
            else if (cmd.equals("article list")) {

                if (articles.size() == 0) {
                    System.out.println(" 목록 없음 ");
                    continue;
                }
                System.out.println("   번호   /   제목   ");
                for (Article article : articles) {
                    System.out.printf("   %d   /   %s   \n", article.getId(), article.getTitle());
                }

            }
        }
    }
}
class Article {
    private int id;
    private String title;
    private String content;
    public Article(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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