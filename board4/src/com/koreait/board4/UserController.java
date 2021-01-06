package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.common.Utils;
import com.koreait.board4.db.UserDAO;
import com.koreait.board4.model.UserModel;

public class UserController {
	// 로그인 페이지 띄우기(GET 방식)
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utils.forwardTemp("로그인", "temp/basic_temp", "user/login", request, response);
	}

	// 로그인 처리(POST 방식)
	public void loginProc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 에러: 0, 이상없음: 1, 아이디 없음: 2, 비밀번호 틀림: 3,
		String login_id = request.getParameter("user_id");
		String login_pw = request.getParameter("user_pw");
		UserModel loginUser = UserDAO.selUser(login_id);

		if (loginUser == null) {
			System.out.println("아이디 없음");
			login(request, response);
		} else {
			if (loginUser.getUser_pw().equals(SecurityUtils.getSecurePassword(login_pw, loginUser.getSalt()))) {
				System.out.println("로그인 성공");
				response.sendRedirect("/board/list.korea");
			} else {
				System.out.println("비밀번호 틀림");
				login(request, response);
			}
		}
	}
}
