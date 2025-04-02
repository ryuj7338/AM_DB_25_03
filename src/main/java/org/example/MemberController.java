package org.example;

import org.example.sevice.MemberService;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {
    private Connection conn;
    private Scanner sc;

    private MemberService memberService;

    public MemberController(Scanner sc, Connection conn) {
        this.sc = sc;
        this.conn = conn;
        this.memberService = new MemberService();
    }

    public void doJoin() {
        String loginId = null;
        String loginPw = null;
        String loginConfirm = null;
        String name = null;
        System.out.println("== 회원가입 ==");

        while (true) {
            System.out.print("loginId : ");
            loginId = sc.nextLine().trim();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디를 다시 입력하세요");
                continue;
            }


            boolean isLoginIdDup = memberService.isLoginIdDup(conn, loginId);

            System.out.println(isLoginIdDup);

            if (isLoginIdDup) {
                System.out.println("이미 사용중인 아이디입니다.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("loginPw : ");
            loginPw = sc.nextLine();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                System.out.println("비밀번호를 다시 입력하세요");
                continue;
            }

            boolean loginCheckPW = true;

            while (true) {
                System.out.println("loginPwCheck : ");
                loginConfirm = sc.nextLine().trim();

                if (loginPw.equals(loginConfirm) == false) {
                    System.out.println("비밀번호가 일치하지 않습니다.");
                    loginCheckPW = false;
                }
                break;
            }
            if (loginCheckPW) {
                break;
            }
        }
        while (true) {
            System.out.print("name : ");
            name = sc.nextLine();

            if (name.length() == 0 || name.contains(" ")) {
                System.out.println("다시 입력하세요");
                continue;
            }
            break;
        }

        int id = memberService.doJoin(conn, loginId, loginPw, name);

        System.out.println(id + "번 회원이 가입되었습니다.");
    }
}
