package com.koreait.board2.model;

public class CommentVO {
	private int i_board;
	private String id;
	private String ctnt;
	private String r_dt;
	private int typ;

	public int getI_board() {
		return i_board;
	}

	public void setI_board(int i_board) {
		this.i_board = i_board;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCtnt() {
		return ctnt;
	}

	public void setCtnt(String ctnt) {
		this.ctnt = ctnt;
	}

	public String getR_dt() {
		return r_dt;
	}

	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}

	public int getTyp() {
		return typ;
	}

	public void setTyp(int typ) {
		this.typ = typ;
	}

}
