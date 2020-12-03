package com.koreait.board2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board2.common.Utils;

@WebServlet("/bList")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int typ = Utils.getIntParam(request, "typ", 1); // 문제가 발생하면 0값이 넘어 옴
		System.out.println("typ : " + typ);
		
		request.setAttribute("title", "리스트");
		request.setAttribute("page", "/WEB-INF/jsp/bList");
		Utils.forward(request, response);
	}
}
