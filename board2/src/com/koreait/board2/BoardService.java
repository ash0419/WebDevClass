package com.koreait.board2;

import java.util.List;

import com.koreait.board2.db.BoardDAO;
import com.koreait.board2.model.BoardVO;

public class BoardService { // logic 담당
	public static List<BoardVO> selBoardList(BoardVO param) {
		return BoardDAO.selBoardList(param);
	}

	public static int regmod(BoardVO param) {
		if (param.getI_board() > 0) { // 수정
			// TODO 수정처리
			return 0;
		} else {
			return BoardDAO.insBoard(param);
		}
	}
}
