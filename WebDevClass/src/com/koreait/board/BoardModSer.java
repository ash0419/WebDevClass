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
 * Servlet implementation class BoardModSer
 */
@WebServlet("/BoardMod")
public class BoardModSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardDAO dao = BoardDAO.getInstance(); // static변수
		BoardVO data = dao.selBoard(Integer.parseInt(request.getParameter("i_board")));

		request.setAttribute("data", data);

		request.getRequestDispatcher("/WEB-INF/jsp/boardMod.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		Calendar time = Calendar.getInstance();

		int i_board = Integer.parseInt(request.getParameter("i_board"));
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		String r_dt = request.getParameter("r_dt");
		String m_dt = format.format(time.getTime());

		BoardDAO dao = BoardDAO.getInstance();
		dao.updBoard(new BoardVO(i_board, title, ctnt, r_dt, m_dt));
		
		response.sendRedirect("/BoardDetail?i_board=" +i_board);
	}

}
