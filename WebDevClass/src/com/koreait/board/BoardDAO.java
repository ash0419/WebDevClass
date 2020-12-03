package com.koreait.board;

import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	private List<BoardVO> list;

	private static BoardDAO dao;

	// 싱글톤 : 외부에서 객체화를 못하게 해야한다. -> 생성자 앞에 private를 붙여준다.
	public static BoardDAO getInstance() {
		if (dao == null) {
			dao = new BoardDAO();
		}
		return dao;
	}

	private BoardDAO() {
		list = new ArrayList();
		list.add(new BoardVO(1, "안녕하세요", "내용입니다. 안녕하세요.", "12-12", "12-12"));
		list.add(new BoardVO(2, "Hello", "Hi, Nice to meet you", "10-20", "10-20"));
		list.add(new BoardVO(3, "반갑습니다.", "반가워요. 반갑습니다.", "11-20", "11-20"));
	}

	public List<BoardVO> selBoardList() {
		return list;
	}

	public BoardVO selBoard(int i_board) {
		for (BoardVO vo : list) {
			if (vo.getI_board() == i_board) {
				return vo;
			}
		}
		return null;
	}

	public void insBoard(BoardVO vo) {
		list.add(vo);
	}

	public void delBoard(int i_board) {
		// 객체 주소값으로 삭제
		for (BoardVO vo : list) {
			if (vo.getI_board() == i_board) {
				list.remove(vo);
				return;
			}
		}
		/*
		 * //인덱스로 삭제 for (int i=0; i< list.size(); i++) { BoardVO vo = list.get(i);
		 * list.remove(i); return }
		 */
	}

	public void updBoard(BoardVO vo) {
		delBoard(vo.getI_board());
		insBoard(vo);
	}
}