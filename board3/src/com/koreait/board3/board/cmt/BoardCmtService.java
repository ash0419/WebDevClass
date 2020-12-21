package com.koreait.board3.board.cmt;

import javax.servlet.http.HttpServletRequest;

import com.koreait.board3.common.Utils;
import com.koreait.board3.db.BoardCmtDAO;
import com.koreait.board3.model.BoardPARAM;

public class BoardCmtService {
	public static void selBoarCmtdList(HttpServletRequest request) {
		int i_board = Utils.getIntParam(request, "i_board");
		BoardPARAM p = new BoardPARAM();
		p.setI_board(i_board);

		request.setAttribute("list", BoardCmtDAO.selCmtList(p));
	}
}
