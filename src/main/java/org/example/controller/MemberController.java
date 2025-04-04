package org.example.controller;

import org.example.container.Container;
import org.example.dto.Member;
import org.example.service.MemberService;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {
    private Connection conn;
    private Scanner sc;

    private MemberService memberService;

    public MemberController() {
        this.memberService = Container.memberService;
        sc = Container.sc;

    }

    public void login() {
        String loginId = null;
        String loginPw = null;

        System.out.println("== 로그인 ==");

        while (true) {
            System.out.print("아이디 : ");
            loginId = sc.nextLine();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디를 다시 입력하세요");
                continue;
            }

            boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

            if (isLoginIdDup == false) {
                System.out.println(loginId + "(은)는 없는 아이디입니다.");
                continue;
            }
            break;
        }

        Member member = memberService.getMemberByLoginId(loginId);

        int tryMaxCount = 3;
        int tryCount = 0;

        while (true) {
            if (tryCount >= tryMaxCount) {
                System.out.println("비밀번호가 틀렸습니다. 다시 입력하세요");
                break;
            }
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                System.out.println("비밀번호를 다시 입력하세요");
                continue;
            }
            if (member.getLoginPw().equals(loginPw) == false) {
                tryCount++;
                System.out.println("비밀번호가 일치하지 않습니다.");
                continue;
            }

            Container.session.login(member);

            System.out.println(member.getName() + "님 환영합니다.");
            break;
        }
    }

    public void showProfile() {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        } else {
            System.out.println(Container.session.loginedMember);
        }

    }

    public void logout() {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        }
        System.out.println("== 로그아웃 ==");
        Container.session.logout();
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


            boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

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

        int id = memberService.doJoin(loginId, loginPw, name);

        System.out.println(id + "번 회원이 가입되었습니다.");
    }


}
