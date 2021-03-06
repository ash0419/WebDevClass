package com.koreait.board4;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.common.Utils;
import com.koreait.board4.db.SQLInterUpdate;
import com.koreait.board4.db.UserDAO;
import com.koreait.board4.model.UserModel;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class UserController {
	// 로그인 페이지 띄우기(GET 방식)
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utils.forwardTemp("로그인", "temp/basic_temp", "user/login", request, response);
	}

	// 로그인 처리(POST 방식)
	public void loginProc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 에러: 0, 이상없음: 1, 아이디 없음: 2, 비밀번호 틀림: 3,
		String login_id = request.getParameter("user_id");
		String login_pw = request.getParameter("user_pw");

		UserModel param = new UserModel();
		param.setUser_id(login_id);
		// 이상없음: 1 아이디 없음 :2, 비밀번호 틀림 : 3
		UserModel loginUser = UserDAO.selUser(param);

		if (loginUser == null) {
			request.setAttribute("msg", "아이디를 확인해 주세요.");
			login(request, response);
			return;
		}
		if (loginUser.getUser_pw().equals(SecurityUtils.getSecurePassword(login_pw, loginUser.getSalt()))) {
			System.out.println("로그인 성공");
			loginUser.setSalt(null);
			loginUser.setUser_pw(null);
			loginUser.setR_dt(null);
			loginUser.setPh(null);
			loginUser.setProfile_img(null);
			loginUser.setUser_id(null);

			HttpSession session = request.getSession(); // session을 얻어옴
			session.setAttribute("loginUser", loginUser);

			response.sendRedirect("/board/list.korea?typ=1");
		} else {
			request.setAttribute("msg", "비밀번호를 확인해 주세요.");
			login(request, response);
		}
	}

	public void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utils.forwardTemp("회원가입", "temp/basic_temp", "user/join", request, response);
	}

	public void joinProc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원가입 오류가 발생되면 다시 회원가입 페이지로
		// 회원가입 완료되면 로그인 화면으로 이동
		String join_id = request.getParameter("user_id");
		String join_pw = request.getParameter("user_pw");
		String join_nm = request.getParameter("nm");
		int join_gender = Utils.getIntParam(request, "gender");
		String join_ph = request.getParameter("ph");

		String salt = SecurityUtils.getSalt();
		String encryptPw = SecurityUtils.getSecurePassword(join_pw, salt);

		String sql = " INSERT INTO t_user (user_id, user_pw, salt, nm, gender, ph) VALUES (?, ?, ?, ?, ?, ?) ";

		int result = UserDAO.executeUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setNString(1, join_id);
				ps.setNString(2, encryptPw);
				ps.setNString(3, salt);
				ps.setNString(4, join_nm);
				ps.setInt(5, join_gender);
				ps.setNString(6, join_ph);
			}
		});
		if (result == 1) {
			login(request, response);
			return;
		}
		request.setAttribute("msg", "회원가입에 실패하였습니다.");
		join(request, response);
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		hs.invalidate(); // 세션에 있는 정보를 다 날려줌
		response.sendRedirect("/user/login.korea");
	}

	public void profile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserModel param = new UserModel();
		param.setI_user(SecurityUtils.getLoingUserPk(request));
		request.setAttribute("data", UserDAO.selUser(param));
		request.setAttribute("jsList", new String[] { "axios.min", "user" });
		Utils.forwardTemp("프로필", "temp/basic_temp", "user/profile", request, response);
	}

	// 이미지 업로드 proc
	public void profileUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int i_user = SecurityUtils.getLoingUserPk(request);

		String savePath = request.getServletContext().getRealPath("/res/img/" + i_user);

//		File imgFile = new File(savePath + "/파일명.jpg");
//		if(imgFile.exists()) {
//			imgFile.delete();
//		}
		File folder = new File(savePath);
		if (folder.exists()) {
			// 기존 이미지가 있었다면 삭제처리
			File[] folder_list = folder.listFiles();
			for (File file : folder_list) {
				if (file.isFile()) {
					file.delete();
				}
			}
			folder.delete();
		}
		folder.mkdirs(); // s를 붙여주면 없는 상위 폴더까지 다 만들어준다. 될수있으면 쓰는 걸 권장.

		int sizeLimit = 104_857_600; // 100MB 제한

		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
				new DefaultFileRenamePolicy());

		Enumeration files = multi.getFileNames();
		if (files.hasMoreElements()) {
			String eleName = (String) files.nextElement();

			System.out.println("eleName : " + eleName);

			String fileNm = multi.getFilesystemName(eleName);
			System.out.println("fileNm2 : " + fileNm);

			String fileType = multi.getContentType(eleName);
			System.out.println("fileType : " + fileType);

			String sql = " UPDATE t_user SET profile_img = ? WHERE i_user = ? ";

			UserDAO.executeUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setString(1, fileNm);
					ps.setInt(2, i_user);
				}
			});
		}
		response.sendRedirect("/user/profile.korea");
	}

	public void delProfileImg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int i_user = SecurityUtils.getLoingUserPk(request);
		String savePath = request.getServletContext().getRealPath("/res/img/" + i_user);

		File folder = new File(savePath);
		if (folder.exists()) {
			// 기존 이미지가 있었다면 삭제처리
			File[] folder_list = folder.listFiles();
			for (File file : folder_list) {
				if (file.isFile()) {
					file.delete();
				}
			}
			folder.delete();
		}
		String sql = " UPDATE t_user SET profile_img = null WHERE i_user = ? ";

		UserDAO.executeUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_user);
			}
		});
		String result = "{\"result\": 1}";
		response.setContentType("application/json");
		response.getWriter().print(result);
	}

	public void changePw(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("jsList", new String[] { "user" });
		Utils.forwardTemp("비밀번호 변경", "temp/basic_temp", "user/changePw", request, response);
	}

	public void changePwProc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int i_user = SecurityUtils.getLoingUserPk(request);
		String before_pw = request.getParameter("current_pw");
		String after_pw = request.getParameter("user_pw");

		UserModel param = new UserModel();
		param.setI_user(i_user);
		UserModel loginUser = UserDAO.selUser(param);

		String before_salt = loginUser.getSalt();
		String after_salt = SecurityUtils.getSalt();
		
		if (SecurityUtils.getSecurePassword(before_pw, before_salt).equals(loginUser.getUser_pw())) {
			String sql = " UPDATE t_user SET user_pw = ?, salt = ? WHERE i_user = ? ";

			UserDAO.executeUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setNString(1, SecurityUtils.getSecurePassword(after_pw, after_salt));
					ps.setNString(2, after_salt);
					ps.setInt(3, i_user);
				}
			});
			logout(request, response);
		} else {
			request.setAttribute("msg", "기존 비밀번호가 틀렸습니다.");
			changePw(request, response);
		}
	}
}
