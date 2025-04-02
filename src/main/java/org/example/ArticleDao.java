package org.example;

import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;

public class ArticleDao {

    public int doWrite(Connection conn, String title, String body) {

        SecSql sql = new SecSql();

        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body` = ?;", body);

        return DBUtil.insert(conn, sql);
    }
    public int doList() {
        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM article");
        sql.append("ORDER BY id DESC");

        return DBUtil.insert()
    }
}
