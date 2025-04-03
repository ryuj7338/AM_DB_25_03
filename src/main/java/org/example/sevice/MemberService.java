package org.example.sevice;

import org.example.dao.MemberDao;
import org.example.dto.Member;

import java.sql.Connection;

public class MemberService {

    private MemberDao memberDao;

    public MemberService() {
        this.memberDao = new MemberDao();
    }
    public int doLogin(Connection conn, String loginId, String loginPw) {
        return this.memberDao.doLogin(conn, loginId, loginPw);
    }

    public boolean isLoginIdDup(Connection conn, String loginId) {
        return memberDao.isLoginIdDup(conn,loginId);
    }
    public int doJoin(Connection conn, String loginId, String loginPw, String name) {
        return memberDao.doJoin(conn,loginId,loginPw,name);
    }

    public Member getMemberByLoginId(Connection conn, String loginId) {
        return memberDao.getMemberByLoginId(conn, loginId);
    }
}
