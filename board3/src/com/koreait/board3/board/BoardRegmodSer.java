package com.koreait.board3.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;

@WebServlet("/board/bRegmod")
public class BoardRegmodSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (SecurityUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}

		int typ = Utils.getIntParam(request, "typ");
		request.setAttribute("typ", typ);
		request.setAttribute("jsList", new String[] { "board" });
		Utils.forwardTemp("등록/수정", "temp/basic_temp", "board/bRegmod", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int result = BoardService.regMod(request);
		String typ = request.getParameter("typ");
		response.sendRedirect("list?typ=" + typ);
	}

}
