package com.koreait.board4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.koreait.board4.db.DbUtils;
import com.koreait.board4.model.UserModel;

public class UserDAO extends CommonDAO {
	public static UserModel selUser(UserModel p) {
		String sql = " SELECT * FROM t_user WHERE ";
		if (p.getUser_id() != null) {
			sql += " user_id = ?";
		} else if (p.getI_user() > 0) {
			sql += " i_user = ?";
		}
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			if (p.getUser_id() != null) {
				ps.setString(1, p.getUser_id());
			} else if (p.getI_user() > 0) {
				ps.setInt(1, p.getI_user());
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				UserModel vo = new UserModel();
				vo.setI_user(rs.getInt("i_user"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setUser_pw(rs.getString("user_pw"));
				vo.setSalt(rs.getString("salt"));
				vo.setNm(rs.getString("nm"));
				vo.setGender(rs.getInt("gender"));
				vo.setPh(rs.getString("ph"));
				vo.setR_dt(rs.getString("r_dt"));
				vo.setProfile_img(rs.getString("profile_img"));
				return vo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return null;
	}
}
