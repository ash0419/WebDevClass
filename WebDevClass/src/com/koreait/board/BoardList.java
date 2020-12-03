package com.koreait.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BoardList") // 파일을 열고 싶을 때 쓰는 주소, annotation
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) // get 방식일 때 실행
			throws ServletException, IOException {

		BoardDAO dao = BoardDAO.getInstance();
		List<BoardVO> dao_list = dao.selBoardList();
		request.setAttribute("data", dao_list);

//		response.setContentType("text/html");
//		response.setCharacterEncoding("UTF-8");
//		
//		PrintWriter out = response.getWriter();
//		
//		out.append("<html>")
//		.append("<body>")
//		.append("<h1>Hello World!!!</h1>")
//		.append("<div><a href=\"http://www.naver.com\">네이버 가기</a></div>")
//		.append("</body>")
//		.append("</html>");
		String jsp = "/WEB-INF/jsp/boardList2.jsp";
		// 대부분 jsp파일에 접근할 때 사용
		RequestDispatcher rd = request.getRequestDispatcher(jsp); // jsp 파일을 쓸 때 사용하는 객체
		rd.forward(request, response);

	}
//		request.getRequestDispatcher(jsp).forward(request, response);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) // post 방식일 때 실행
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}