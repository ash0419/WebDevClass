package com.koreait.board4;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.common.Utils;
import com.koreait.board4.db.BoardDAO;
import com.koreait.board4.db.SQLInterUpdate;
import com.koreait.board4.model.BoardPARAM;
import com.koreait.board4.model.BoardSEL;

public class BoardController {
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int typ = Utils.getIntParam(request, "typ", 1);

		BoardPARAM p = new BoardPARAM();
		p.setTyp(typ);

		request.setAttribute("list", BoardDAO.selBoardList(p));
		request.setAttribute("jsList", new String[] { "board" });

		Utils.forwardTemp("리스트", "temp/basic_temp", "board/bList", request, response);
	}

	public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i_board = Utils.getIntParam(request, "i_board");

		BoardPARAM p = new BoardPARAM();
		p.setI_board(i_board);
		p.setI_user(SecurityUtils.getLoingUserPk(request));

		// ip주소로 구분하여 application 객체 이용해서 새로고침을 해도 조회수가 안 올라가도록 설정
		// 1. 아이피 주소값 얻어야 함
		// 2. application에서 "특정키값 만들어야 함" 즉, 어떤 글인지 구분이 가능해야 한다.
		// 3. application에서 특정키값으로 값이 있는지 체크
		// 3-1 : 없으면 그 특정키값으로 나의 ip 주소를 set한다. (조회수 올림)
		// 3-2 : 있으면 applicaiotion에 저장된 ip주소가 나랑 같은지 확인
		// 3-2-1 : 같으면 무시
		// 3-2-2 : 다르면 조회수 올림 > 그 특정키값으로 나의 ip 주소를 set한다.

		// application에 데이터가 쌓이기 때문에 메모리상으로 손해가 많다.
		final String key = "b_" + i_board;
		String myIpAddr = request.getRemoteAddr(); // ip 주소값 얻어오는 명령어
		ServletContext application = request.getServletContext(); // application 객체를 가져오는 명령어

		String savedIpAddr = (String) application.getAttribute(key);

		if (savedIpAddr == null || !savedIpAddr.contentEquals(myIpAddr)) {
			application.setAttribute(key, myIpAddr);

			String sql = " UPDATE t_board SET hits = hits + 1 WHERE i_board = ? ";
			BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, i_board);
				}
			});
		}

		BoardSEL data = BoardDAO.selBoard(p);
		request.setAttribute("data", data);
		request.setAttribute("jsList", new String[] { "board", "axios.min" });
		Utils.forwardTemp(data.getTitle(), "temp/basic_temp", "board/bDetail", request, response);
	}

	public void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utils.forwardTemp("글등록", "temp/basic_temp", "board/bRegmod", request, response);
	}

	public void regProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int typ = Utils.getIntParam(request, "typ");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		int writerI_user = SecurityUtils.getLoingUserPk(request);

		String sql = " INSERT INTO t_board (typ, seq, title, ctnt, i_user) SELECT typ, IFNULL(MAX(seq), 0) +1, ?, ?, ? FROM t_board WHERE typ = ? ";
		int result = BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setNString(1, title);
				ps.setNString(2, ctnt);
				ps.setInt(3, writerI_user);
				ps.setInt(4, typ);
			}
		});
		
		request.getRequestDispatcher("/board/list.korea?typ=" + typ).forward(request, response); // 원래 쓰던 데이터가 그대로 남아서 가지고 온다.

		// response.sendRedirect("/board/list?typ=" + typ); 새로운 request가 만들어진다.
	}

	public void mod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utils.forwardTemp("글수정", "temp/basic_temp", "board/bRegmod", request, response);
	}

	public void modProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void ajaxFavorite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int state = Utils.getIntParam(request, "state");
		int i_board = Utils.getIntParam(request, "i_board");

		// INSERT문
		String sql = " INSERT INTO t_board_favorite (i_board, i_user) VALUES (?, ?) ";

		if (state == 0) {
			// DELETE문
			sql = " DELETE FROM t_board_favorite WHERE i_board = ? AND i_user = ? ";
		}
		
		int result = BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_board);
				ps.setInt(2, SecurityUtils.getLoingUserPk(request));
			}
		});
		
		response.setContentType("application/json");
		response.getWriter().print(String.format("{\"result\":%d}", result));
		// {"result":1} {"result":0}
	}
}
