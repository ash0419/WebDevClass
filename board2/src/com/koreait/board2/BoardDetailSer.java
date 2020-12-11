package com.koreait.board2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board2.common.Utils;
import com.koreait.board2.model.BoardCmtVO;
import com.koreait.board2.model.BoardVO;

@WebServlet("/bDetail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int typ = Utils.getIntParam(request, "typ");
		int i_board = Utils.getIntParam(request, "i_board");
		int err = Utils.getIntParam(request, "err");
		if (err > 0) {
			switch (err) {
			case 1:
				request.setAttribute("msg", "댓글 쓰기 실패");
				break;
			}
		}
		BoardVO vo = new BoardVO();
		vo.setTyp(typ);
		vo.setI_board(i_board);

		BoardCmtVO cmtVo = new BoardCmtVO();
		cmtVo.setTyp(typ);
		cmtVo.setI_board(i_board);

		request.setAttribute("item", BoardService.detail(vo, request));
		request.setAttribute("cmtList", BoardService.selBoardCmtList(cmtVo));
		Utils.forward("리스트 세부내용", "bDetail", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int typ = Utils.getIntParam(request, "typ");
		int i_board = Utils.getIntParam(request, "i_board");

		BoardVO vo = new BoardVO();
		vo.setTyp(typ);
		vo.setI_board(i_board);

		int result = BoardService.delBoard(vo);
		if (result == 0) {
			request.setAttribute("msg", "삭제할 수 없습니다.");
			doGet(request, response);
			return;
		}

		response.sendRedirect("/bList?typ=" + typ);
	}
}
