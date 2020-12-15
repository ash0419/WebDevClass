package com.koreait.board3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtils {
	public static Connection getCon() throws ClassNotFoundException, SQLException {
		final String URL = "jdbc:mysql://localhost:3306/board_1";
		final String USER = "root";
		final String PW = "koreait2020";

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(URL, USER, PW);

		System.out.println("DB 연결 성공");
		return con;
	}

	// Connection : DB연걸 역활, PreparedStatement : 쿼리문 실행 담당, ResultSet : select문 결과가
	// 들어가있음
	public static void close(Connection con, PreparedStatement ps) {

		try {
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(con, ps);
	}
}