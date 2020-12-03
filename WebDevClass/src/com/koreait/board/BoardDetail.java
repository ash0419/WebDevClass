package com.koreait.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BoardDetail")
public class BoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strI_board = request.getParameter("i_board");
		System.out.println("i_board : " + strI_board);
		// DB통신
		BoardDAO dao = BoardDAO.getInstance(); // static변수
		BoardVO data = dao.selBoard(Integer.parseInt(strI_board));
		
		// 값 전달 servlet -> jsp
		request.setAttribute("data", data); // 사이트에 한번 쓰고 버리는 데이터는 request에 담는다. 
		// application은 모든 값을 전달할때, session은 로그인 정보로 주로 사용
		// pagecontext > request > session > application 순서로 확인
		String jsp = "/WEB-INF/jsp/boardDetail2.jsp";
		// 대부분 jsp파일에 접근할 때 사용
		request.getRequestDispatcher(jsp).forward(request, response); // jsp 파일을 쓸 때 사용하는 객체

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)r
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}