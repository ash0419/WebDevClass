<%@page import="com.koreait.board.BoardVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<BoardVO> data = (List<BoardVO>) request.getAttribute("data");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
</head>
<body>
	<h1>보드 리스트</h1>
	<table>
		<tr>
			<th>No</th>
			<th>제목</th>
		</tr>
		<tr>
			<td>3</td>
			<td>
				<a href="/BoardDetail?i_board=3">반갑습니다.</a>
			</td>
		</tr>
		<tr>
			<td>2</td>
			<td>
				<a href="/BoardDetail?i_board=2">Hello</a>
			</td>
		</tr>
		<tr>
			<td>1</td>
			<td>
				<a href="/BoardDetail?i_board=1">안녕하세요</a>
			</td>
		</tr>
		<%
			for (BoardVO vo : data) {
		%>
		<tr>
			<td><%=vo.getI_board()%></td>
			<td>
				<a href="/BoardDetail?i_board=<%=vo.getI_board()%>"><%=vo.getTitle()%></a>
			</td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>