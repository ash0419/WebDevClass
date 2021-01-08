package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.db.CommonDAO;

public class Controller {
	private static UserController uCont = new UserController();
	private static BoardController bCont = new BoardController();

	public static void goToErr(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsp = "/WEB-INF/view/err.jsp";
		request.getRequestDispatcher(jsp).forward(request, response); // dispatcher로 하면 주소값이 바뀌지 않는다.
	}

	// 보통 nav가 아니라 router로 많이 씀
	public static void nav(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String[] urlArr = request.getRequestURI().split("/");

		// 메뉴 리스트 가져오기
		ServletContext application = request.getServletContext();
		if (application.getAttribute("menus") == null) {
			application.setAttribute("menus", CommonDAO.selManageBoardList());
		}

		switch (urlArr[1]) {
		case "user":
			switch (urlArr[2]) {
			case "login.korea":
				uCont.login(request, response);
				return;
			case "loginProc.korea":
				uCont.loginProc(request, response);
				return;
			case "join.korea":
				uCont.join(request, response);
				return;
			case "joinProc.korea":
				uCont.joinProc(request, response);
				return;
			case "logout.korea":
				uCont.logout(request, response);
				return;
			}
			break;
		case "board":
			switch (urlArr[2]) {
			case "list.korea":
				bCont.list(request, response);
				return;
			}
			break;
		}
		if (SecurityUtils.getLoingUserPk(request) > 0) { // 로그인이 되어 있는 상태
			switch (urlArr[1]) {
			case "board":
				switch (urlArr[2]) {
				case "reg.korea":
					bCont.reg(request, response);
					return;
				case "regProc.korea":
					bCont.regProc(request, response);
					return;
				case "modProc.korea":
					bCont.modProc(request, response);
					return;
				}
			}
		}
		goToErr(request, response);
	}
}
