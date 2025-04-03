package org.example.dao;

import org.example.util.DBUtil;
import org.example.util.SecSql;

import org.example.dto.Member;
import java.sql.Connection;
import java.util.Map;

public class MemberDao {


    public boolean isLoginIdDup(Connection conn, String loginId) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE loginId = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }
    public int doLogin(Connection conn, String loginId, String loginPw) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) FROM `member`");
        sql.append("WHERE loginId = ?,", loginId);
        sql.append("OR loginPw = ?", loginPw);
        sql.append("LIMIT 3");

        return DBUtil.selectRowIntValue(conn, sql);
    }

    public int doJoin(Connection conn, String loginId, String loginPw, String name) {

        SecSql sql = new SecSql();

        sql.append("INSERT INTO `member`");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("loginId = ?,", loginId);
        sql.append("loginPw = ?,", loginPw);
        sql.append("`name` = ?;", name);

        return DBUtil.insert(conn, sql);
    }

    public org.example.dto.Member getMemberByLoginId(Connection conn, String loginId) {

        SecSql sql = new SecSql();

        sql.append("SELECT * FROM `member` WHERE loginId = ?;", loginId);

        Map<String, Object> memberMap = DBUtil.selectRow(conn, sql);

        if (memberMap.isEmpty()) {
            return null;
        }
        return new Member(memberMap);
    }
}