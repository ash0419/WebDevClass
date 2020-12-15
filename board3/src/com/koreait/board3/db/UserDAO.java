package com.koreait.board3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.model.UserModel;

public class UserDAO extends CommonDAO {
	public static UserModel selUser(UserModel p) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = " SELECT i_user, nm, user_pw, salt FROM t_user WHERE user_id = ? ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, p.getUser_id());
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
		}
		return null;
	}

	// 0: 에러발생, 1: 로그인 성공, 2: 아이디 없음, 3: 비밀번호 틀림
	public static int login(UserModel p) {
//		UserModel p2 = null;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		String sql = " SELECT i_user, nm, user_pw, salt FROM t_user WHERE user_id = ? ";

		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, p.getUser_id());
			rs = ps.executeQuery();

			if (rs.next()) {
//				p2 = new UserModel();
//				p2.setI_user(rs.getInt("i_user"));
//				p2.setNm(rs.getNString("nm"));
//				p2.setUser_pw(rs.getNString("user_pw"));
//				p2.setSalt(rs.getNString("salt"));

				String dbPw = rs.getString("user_pw");
				String dbSalt = rs.getString("salt");

				String encryptPw = SecurityUtils.getSecurePassword(p.getUser_pw(), dbSalt);

				if (encryptPw.equals(dbPw)) {
					return 1;
				} else {
					return 3;
				}

//				if (SecurityUtils.getSecurePassword(p.getUser_pw(), p2.getSalt()).equals(p2.getUser_pw())) {
//					return 1;
//				} else {
//					return 3;
//				}
//			} else {
//				return 2;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return 0;
	}
}