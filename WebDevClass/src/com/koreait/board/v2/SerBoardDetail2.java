package com.koreait.board.v2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SerBoardDetail
 */
@WebServlet("/v2/boardDetail")
public class SerBoardDetail2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int i_board = Utils2.getIntParam(request, "i_board");

		if (i_board == 0) {
			response.sendRedirect("/v2/boardList");
			return;
		}

		BoardDAO2 dao = BoardDAO2.getInstance();

		request.setAttribute("item", dao.selboard(i_board));

		Utils2.forward("boardDetail", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}