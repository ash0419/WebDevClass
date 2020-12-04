package com.koreait.board2.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board2.model.BoardVO;

public class BoardDAO {
	public static List<BoardVO> selBoardList(final BoardVO param) {
		BoardVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<BoardVO> list = new ArrayList<BoardVO>();

		String sql = " SELECT i_board, title, ctnt, r_dt FROM t_board_? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());

			rs = ps.executeQuery();

			while (rs.next()) {
				vo = new BoardVO();
				vo.setI_board(rs.getInt("i_board"));
				vo.setTitle(rs.getNString("title"));
				vo.setCtnt(rs.getNString("ctnt"));
				vo.setR_dt(rs.getString("r_dt"));

				list.add(vo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return list;
	}

	public static BoardVO selBoard(int i_board, int typ) {
		BoardVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = " SELECT * FROM t_board_? WHERE i_board = ? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, typ);
			ps.setInt(2, i_board);

			rs = ps.executeQuery();

			if (rs.next()) {
				String title = rs.getNString("title");
				String ctnt = rs.getNString("ctnt");
				String r_dt = rs.getNString("r_dt");

				vo = new BoardVO();
				vo.setI_board(i_board);
				vo.setTitle(title);
				vo.setCtnt(ctnt);
				vo.setR_dt(r_dt);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
		return vo;
	}

	public static int insBoard(BoardVO vo) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = " INSERT INTO t_board_? (title, ctnt) values ( ? , ? ) ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, vo.getTyp());
			ps.setNString(2, vo.getTitle());
			ps.setNString(3, vo.getCtnt());

			result = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
		return result;
	}
}
