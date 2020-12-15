package com.koreait.board3.user;

import javax.servlet.http.HttpServletRequest;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.model.UserModel;

public class UserService {
	public static int join(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String nm = request.getParameter("nm");
		int gender = Utils.getIntParam(request, "gender");
		String ph = request.getParameter("ph");

		String salt = SecurityUtils.getSalt();
		String encryptPw = SecurityUtils.getSecurePassword(user_pw, salt);

		System.out.println("encryptPw : " + encryptPw);

		UserModel p = new UserModel();
		p.setUser_id(user_id);
		p.setUser_pw(encryptPw);
		p.setSalt(salt);
		p.setNm(nm);
		p.setGender(gender);
		p.setPh(ph);

		return 0;
	}
}
