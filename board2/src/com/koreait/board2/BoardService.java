package com.koreait.board2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.koreait.board2.common.SQLInterUpdate;
import com.koreait.board2.db.BoardDAO;
import com.koreait.board2.model.BoardCmtVO;
import com.koreait.board2.model.BoardVO;
import com.mysql.cj.xdevapi.PreparableStatement;

public class BoardService { // logic 담당
	public static int selPageCnt(BoardVO param) {
		return BoardDAO.selPageCnt(param);
	}

	public static List<BoardVO> selBoardList(BoardVO param) {
		return BoardDAO.selBoardList(param);
	}

	public static List<BoardVO> selBoardList(BoardVO param, int page) {
		int s_idx = (page - 1) * param.getRowCntPerPage();
		param.setS_idx(s_idx);
		return BoardDAO.selBoardList(param);
	}

	public static int regmod(BoardVO param) {
		if (param.getI_board() > 0) { // 수정
			String sql = " UPDATE t_board_? SET title=?, ctnt=? WHERE i_board=? ";
			return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, param.getTyp());
					ps.setNString(2, param.getTitle());
					ps.setNString(3, param.getCtnt());
					ps.setInt(4, param.getI_board());
				}
			});
		}
		return BoardDAO.insBoard(param);
	}

	public static BoardVO detail(BoardVO param, HttpServletRequest request) {
		String ip = request.getRemoteAddr();

		String key = String.format("b_%d_%d", param.getTyp(), param.getI_board());
		ServletContext application = request.getServletContext(); // 누구나 들어 왔을 때 서버마다 하나만 만들어지는 객체, session이나 context는
																	// 개인용 객체
		String savedIp = (String) application.getAttribute(key);
		if (!ip.equals(savedIp)) {
			application.setAttribute(key, ip);

			String sql = "UPDATE t_board_? SET hits = hits +1 WHERE i_board=? ";
			BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, param.getTyp());
					ps.setInt(2, param.getI_board());
				}
			});
		}

		Enumeration<String> strArr = application.getAttributeNames();
		while (strArr.hasMoreElements()) {
			String str = strArr.nextElement();

			if (str.startsWith("b_")) {
				System.out.println("key : " + str);
				System.out.println("value : " + application.getAttribute(str));
			}
		}
		return selBoard(param);
	}

	public static BoardVO selBoard(BoardVO param) {
		return BoardDAO.selBoard(param);
	}

	public static int delBoard(BoardVO param) {

		String sql = " DELETE FROM t_board_? WHERE i_board=? ";
		return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() { // 인터페이스를 객체화 한 것이 아니라 이름이 없는 클래스를 객체화 한 것 (익명 클래스)
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getTyp());
				ps.setInt(2, param.getI_board());
			}
		});
	}

	public static int RegCmt(BoardCmtVO param) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = " INSERT INTO t_board_cmt_? (i_board, ctnt) VALUES (? , ?) ";

		return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getTyp());
				ps.setInt(2, param.getI_board());
				ps.setNString(3, param.getCtnt());
			}
		});
	}
	public static List<BoardCmtVO> selBoardCmtList(BoardCmtVO param) {
		return BoardDAO.selBoardCmtList(param);
	}
}