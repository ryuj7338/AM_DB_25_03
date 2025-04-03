package org.example.sevice;

import org.example.dao.MemberDao;
import org.example.dto.Member;

import java.sql.Connection;

public class MemberService {

    private MemberDao memberDao;

    public MemberService() {
        this.memberDao = new MemberDao();
    }
    public int doLogin( String loginId, String loginPw) {
        return this.memberDao.doLogin(loginId, loginPw);
    }

    public boolean isLoginIdDup(String loginId) {
        return memberDao.isLoginIdDup(loginId);
    }
    public int doJoin( String loginId, String loginPw, String name) {
        return memberDao.doJoin(loginId,loginPw,name);
    }

    public Member getMemberByLoginId(String loginId) {
        return memberDao.getMemberByLoginId(loginId);
    }
}
