package com.koreait.board4;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.common.Utils;
import com.koreait.board4.db.SQLInterUpdate;
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
			request.setAttribute("msg", "아이디를 확인해 주세요.");
			login(request, response);
			return;
		}
		if (loginUser.getUser_pw().equals(SecurityUtils.getSecurePassword(login_pw, loginUser.getSalt()))) {
			System.out.println("로그인 성공");
			loginUser.setSalt(null);
			loginUser.setUser_pw(null);
			
			HttpSession session = request.getSession(); // session을 얻어옴
			session.setAttribute("loginUser", loginUser);
			
			response.sendRedirect("/board/list.korea");
		} else {
			request.setAttribute("msg", "비밀번호를 확인해 주세요.");
			login(request, response);
		}
	}

	public void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utils.forwardTemp("회원가입", "temp/basic_temp", "user/join", request, response);
	}

	public void joinProc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원가입 오류가 발생되면 다시 회원가입 페이지로
		// 회원가입 완료되면 로그인 화면으로 이동
		String join_id = request.getParameter("user_id");
		String join_pw = request.getParameter("user_pw");
		String join_nm = request.getParameter("nm");
		int join_gender = Utils.getIntParam(request, "gender");
		String join_ph = request.getParameter("ph");

		String salt = SecurityUtils.getSalt();
		String encryptPw = SecurityUtils.getSecurePassword(join_pw, salt);

		String sql = " INSERT INTO t_user (user_id, user_pw, salt, nm, gender, ph) VALUES (?, ?, ?, ?, ?, ?) ";

		int result = UserDAO.executeUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setNString(1, join_id);
				ps.setNString(2, encryptPw);
				ps.setNString(3, salt);
				ps.setNString(4, join_nm);
				ps.setInt(5, join_gender);
				ps.setNString(6, join_ph);
			}
		});
		if (result == 1) {
			login(request, response);
			return;
		}
		request.setAttribute("msg", "회원가입에 실패하였습니다.");
		join(request, response);
	}
}
