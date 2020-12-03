package com.koreait.board;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardReg
 */
@WebServlet("/BoardReg")
public class BoardReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardDAO dao = BoardDAO.getInstance();
		int newI_board = dao.selBoardList().size() + 1;
		request.setAttribute("idx", newI_board);
		String jsp = "/WEB-INF/jsp/boardReg.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		Calendar time = Calendar.getInstance();
		String today = format.format(time.getTime());
		request.setCharacterEncoding("UTF-8");

		int i_board = Integer.parseInt(request.getParameter("i_board"));
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		System.out.println("i_board : " + i_board);
		System.out.println("title : " + title);
		System.out.println("ctnt : " + ctnt);

		BoardDAO dao = BoardDAO.getInstance();
		dao.insBoard(new BoardVO(i_board, title, ctnt, today, today));

		response.sendRedirect("/BoardList");
		// sendRedirect는 값 전달이 불가능하다. 단순한 주소이동.
	}

}
