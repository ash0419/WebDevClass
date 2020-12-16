package com.koreait.board3.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.Utils;

@WebServlet("/board/list")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (Utils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
//		String[] jsList = {"board"};
		request.setAttribute("jsList", new String[] { "board" });
		Utils.forwardTemp("List", "temp/basic_temp", "board/bList", request, response);
	}
}
