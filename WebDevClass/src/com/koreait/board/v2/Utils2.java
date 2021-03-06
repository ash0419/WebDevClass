package com.koreait.board.v2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils2 {
	public static void forward(String target, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsp = String.format("/WEB-INF/jsp2/%s.jsp", target);

		request.getRequestDispatcher(jsp).forward(request, response);
	}

	public static int getIntParam(HttpServletRequest request, String key) {
//		return Integer.parseInt(request.getParameter(key));
		String param = request.getParameter(key);
		return parseStrToInt(param);
	}

	public static int parseStrToInt(String val) {
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return 0;
		}
	}
}
