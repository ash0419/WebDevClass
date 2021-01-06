package com.koreait.board4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.koreait.board4.db.DbUtils;
import com.koreait.board4.model.UserModel;

public class UserDAO {
	public static UserModel selUser(String user_id) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = " SELECT i_user, nm, user_pw, salt FROM t_user WHERE user_id = ? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, user_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				UserModel m = new UserModel();
				m.setI_user(rs.getInt("i_user"));
				m.setNm(rs.getString("nm"));
				m.setUser_pw(rs.getString("user_pw"));
				m.setSalt(rs.getString("salt"));
				return m;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return null;
	}
}
