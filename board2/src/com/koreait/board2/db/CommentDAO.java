package com.koreait.board2.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board2.model.CommentVO;

public class CommentDAO {
	public static List<CommentVO> selBoardList(final CommentVO param) {
		CommentVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CommentVO> list = new ArrayList<CommentVO>();

		String sql = " SELECT i_board, title, ctnt, r_dt FROM t_board_? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());

			rs = ps.executeQuery();

			while (rs.next()) {
				vo = new CommentVO();
				vo.setI_board(rs.getInt("i_board"));
				vo.setId(rs.getNString("id"));
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
}
