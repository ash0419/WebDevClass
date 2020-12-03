package com.koreait.board.v2;

import java.util.ArrayList;
import java.util.List;

public class BoardDAO2 {
	private List<BoardDTO2> list;

	private static BoardDAO2 dao;

	// 싱글톤 : 외부에서 객체화를 못하게 해야한다. -> 생성자 앞에 private를 붙여준다.
	public static BoardDAO2 getInstance() {
		if (dao == null) {
			dao = new BoardDAO2();
		}
		return dao;
	}

	private BoardDAO2() {
		list = new ArrayList<BoardDTO2>();
		BoardDTO2 dto = new BoardDTO2();
		dto.setI_board(1);
		dto.setTitle("타이틀");
		dto.setCtnt("내용");
		dto.setR_dt("11-25");

		list.add(dto);
	}

	public void insBoard(BoardDTO2 vo) {
		vo.setI_board(list.size() + 1);
		list.add(vo);
	}

	public List<BoardDTO2> selBoardList() {
		return list;
	}

	public BoardDTO2 selboard(int i_board) {
		for (BoardDTO2 dto : list) {
			if (dto.getI_board() == i_board) {
				return dto;
			}
		}
		return null;
	}

	public void delBoard(int i_board) {
		for (BoardDTO2 dto : list) {
			if (dto.getI_board() == i_board) {
				list.remove(dto);
				return;
			}
		}
	}

	public void modBoard(BoardDTO2 param) {
		for (BoardDTO2 dto : list) {
			if (dto.getI_board() == param.getI_board()) {
				dto.setTitle(param.getTitle());
				dto.setCtnt(param.getCtnt());
				return;
			}
		}
	}
}
