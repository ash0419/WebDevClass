package com.koreait.board.v2;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/v2/boardList")
public class SerBoardList2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardDAO2 dao = BoardDAO2.getInstance();
		List<BoardDTO2> list = dao.selBoardList();

		request.setAttribute("list", list);

		Utils2.forward("boardList", request, response);
	}
}
