package com.koreait.board4.model;

// Entity, Domain, DTO, VO, Model
public class BoardSEL extends BoardModel {
	private String writer_nm;
	private int favorite_cnts;
	private int is_favorite;

	public int getIs_favorite() {
		return is_favorite;
	}

	public void setIs_favorite(int is_favorite) {
		this.is_favorite = is_favorite;
	}

	public String getWriter_nm() {
		return writer_nm;
	}

	public void setWriter_nm(String writer_nm) {
		this.writer_nm = writer_nm;
	}

	public int getFavorite_cnts() {
		return favorite_cnts;
	}

	public void setFavorite_cnts(int favorite_cnts) {
		this.favorite_cnts = favorite_cnts;
	}
}