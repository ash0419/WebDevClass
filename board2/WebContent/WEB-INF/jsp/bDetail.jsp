<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
	<a href="/bList?typ=${item.typ}">돌아가기</a>
	<form action="/bDetail?typ=${item.typ}&i_board=${item.i_board}"
		method="post">
		<input type="submit" value="글 삭제"/ >
	</form>
</div>
<div>
	<div>번호 : ${item.i_board}</div>
	<div>제목 : ${item.title}</div>
	<div>내용 : ${item.ctnt}</div>
	<div>작성일 : ${item.r_dt}</div>
</div>