package com.koreait.board3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board3.model.BoardCmtModel;
import com.koreait.board3.model.BoardCmtSEL;

public class BoardCmtDAO extends CommonDAO {
	public static List<BoardCmtSEL> selCmtList(BoardCmtModel p) {
		List<BoardCmtSEL> list = new ArrayList<BoardCmtSEL>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = " SELECT A.i_cmt, A.i_board, A.i_user, A.ctnt, A.r_dt B.nm FROM t_board_cmt A INNER JOIN t_user B ON A.i_user = B.i_user "
				+ "WHERE A.i_board = ? ORDER BY A.i_cmt DESC ";
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, p.getI_board());
			rs = ps.executeQuery();

			while (rs.next()) {
				BoardCmtSEL vo = new BoardCmtSEL();
				list.add(vo);

				vo.setI_board(rs.getInt("i_board"));
				vo.setI_cmt(rs.getInt("i_cmt"));
				vo.setI_user(rs.getInt("i_user"));
				vo.setCtnt(rs.getNString("ctnt"));
				vo.setUser_nm(rs.getNString("nm"));
				vo.setR_dt(rs.getString("r_dt"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return list;
	}
}
