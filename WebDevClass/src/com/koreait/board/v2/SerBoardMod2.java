package com.koreait.board.v2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/v2/boardMod")
public class SerBoardMod2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int i_board = Utils2.getIntParam(request, "i_board");

		BoardDAO2 dao = BoardDAO2.getInstance();
		request.setAttribute("item", dao.selboard(i_board));

		Utils2.forward("boardMod", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int i_board = Integer.parseInt(request.getParameter("i_board"));
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		String r_dt = request.getParameter("r_dt");
		BoardDTO2 dto = new BoardDTO2();
		BoardDAO2 dao = BoardDAO2.getInstance();

		dto.setI_board(i_board);
		dto.setTitle(title);
		dto.setCtnt(ctnt);
		dto.setR_dt(r_dt);
		dao.modBoard(dto);

		response.sendRedirect("/v2/boardList");
	}
}