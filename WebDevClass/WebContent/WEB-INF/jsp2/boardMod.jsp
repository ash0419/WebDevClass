<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
</head>
<body>
	<center>
		<h1>글수정</h1>
		<hr>
		<form action="/v2/boardMod?i_board=${item.i_board}" method="post">
			<div>
				번호 :
				<input type="text" name="i_board" value="${item.i_board }" readonly>
			</div>
			<div>
				작성일 :
				<input type="text" name="r_dt" value="${item.r_dt }" readonly>
			</div>
			<div>
				제목 :
				<input type="text" name="title" value="${item.title }">
			</div>
			<div>
				내용 :
				<textarea name="ctnt">${item.ctnt}</textarea>
			</div>
			<div>
				<input type="submit" value="글수정">
				<input type="reset" value="다시 쓰기">
			</div>
	</center>
</body>
</html>