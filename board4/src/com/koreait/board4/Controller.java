package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller {
	private static UserController uCont = new UserController();

	public static void goToErr(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsp = "/WEB-INF/view/err.jsp";
		request.getRequestDispatcher(jsp).forward(request, response); // dispatcher로 하면 주소값이 바뀌지 않는다.
	}

	public static void nav(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] urlArr = request.getRequestURI().split("/");
		System.out.println(request.getRequestURI());
		switch (urlArr[1]) {
		case "user":
			switch (urlArr[2]) {
			case "login.korea":
				uCont.login(request, response);
				return;
			case "loginProc.korea":
				uCont.loginProc(request, response);
				return;
			}
			break;
		case "board":

			break;

		}
		goToErr(request, response);
	}
}
