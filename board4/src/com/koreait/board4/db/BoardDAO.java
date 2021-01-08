package com.koreait.board4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board4.model.BoardPARAM;
import com.koreait.board4.model.BoardSEL;

public class BoardDAO extends CommonDAO {
	public static List<BoardSEL> selBoardList(BoardPARAM param) {
		List<BoardSEL> list = new ArrayList<BoardSEL>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = " SELECT A.i_board, A.seq, A.title, A.r_dt, A.hits, B.i_user, IFNULL(B.nm, '탈퇴회원') AS writer_nm, "
				+ "IFNULL(C.favorite_cnt, 0) AS favorite_cnt "
				+ "FROM t_board A LEFT JOIN t_user B ON A.i_user = B.i_user LEFT JOIN(SELECT i_board, COUNT(i_board) AS favorite_cnt "
				+ "FROM t_board_favorite GROUP BY i_board) C ON A.i_board = C.i_board WHERE typ = ? ORDER BY A.seq DESC; ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());

			rs = ps.executeQuery();

			while (rs.next()) {
				BoardSEL vo = new BoardSEL();
				vo.setI_board(rs.getInt("i_board"));
				vo.setSeq(rs.getInt("seq"));
				vo.setTitle(rs.getNString("title"));
				vo.setR_dt(rs.getString("r_dt"));
				vo.setHits(rs.getInt("hits"));
				vo.setI_user(rs.getInt("i_user"));
				vo.setWriter_nm(rs.getNString("writer_nm"));
				vo.setFavorite_cnts(rs.getInt("favorite_cnt"));
				
				list.add(vo);
			}
//			if(list.isEmpty()) {
//				list = null;
//			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return list;
	}
}
