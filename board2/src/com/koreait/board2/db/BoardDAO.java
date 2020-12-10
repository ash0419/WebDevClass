package com.koreait.board2.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board2.model.BoardVO;

public class BoardDAO {
	public static int selPageCnt(final BoardVO param) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT CEIL(COUNT(i_board) /?) FROM t_board_?;";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getRowCntPerPage());
			ps.setInt(2, param.getTyp());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return 0;
	}

	public static List<BoardVO> selBoardList(final BoardVO param) {
		BoardVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<BoardVO> list = new ArrayList<BoardVO>();

		String sql = " SELECT i_board, title, r_dt, hits FROM t_board_? ORDER BY i_board DESC LIMIT ?, ? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			ps.setInt(2, param.getS_idx());
			ps.setInt(3, param.getRowCntPerPage());
			rs = ps.executeQuery();

			while (rs.next()) {
				vo = new BoardVO();
				vo.setI_board(rs.getInt("i_board"));
				vo.setTitle(rs.getNString("title"));
//				vo.setCtnt(rs.getNString("ctnt")); //트래픽을 많이 잡아먹기 때문에 필요가 없으면 안 넣는다.
				vo.setR_dt(rs.getString("r_dt"));
				vo.setHits(rs.getInt("hits"));

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

	public static BoardVO selBoard(final BoardVO param) {
		BoardVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = " SELECT i_board, title, ctnt, r_dt, hits FROM t_board_? WHERE i_board = ? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			ps.setInt(2, param.getI_board());

			rs = ps.executeQuery();

			if (rs.next()) {
				String title = rs.getNString("title");
				String ctnt = rs.getNString("ctnt");
				String r_dt = rs.getString("r_dt");
				int hits = rs.getInt("hits");

				vo = new BoardVO();
				vo.setI_board(param.getI_board());
				vo.setTitle(title);
				vo.setCtnt(ctnt);
				vo.setR_dt(r_dt);
				vo.setTyp(param.getTyp());
				vo.setHits(hits);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return vo;
	}

	public static int insBoard(BoardVO vo) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = " INSERT INTO t_board_? (title, ctnt) values ( ? , ? ) ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, vo.getTyp());
			ps.setNString(2, vo.getTitle());
			ps.setNString(3, vo.getCtnt());

			result = ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int i_board = rs.getInt(1);
				vo.setI_board(i_board);
			}

			return result;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return result;
	}

	public static int delBoard(BoardVO vo) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = " DELETE FROM t_board_? WHERE i_board=? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, vo.getTyp());
			ps.setInt(2, vo.getI_board());

			result = ps.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
		return result;
	}

	public static int modBoard(BoardVO vo) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = " UPDATE t_board_? SET title=?, ctnt=? WHERE i_board=? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, vo.getTyp());
			ps.setNString(2, vo.getTitle());
			ps.setNString(3, vo.getCtnt());
			ps.setInt(4, vo.getI_board());

			result = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
		return result;
	}

	public static void addHits(BoardVO param) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE t_board_? SET hits = hits +1 WHERE i_board=? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			ps.setInt(2, param.getI_board());

			ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
	}
}
