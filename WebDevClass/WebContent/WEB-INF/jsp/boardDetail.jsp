<%@ page import="com.koreait.board.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	BoardVO data = (BoardVO) request.getAttribute("data");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>디테일</title>
</head>
<body>
	<h1>디테일 페이지</h1>
	<div><%=data.getTitle()%></div>
	<div>작성일 : <%=data.getR_dt()%></div>
	<div><%=data.getCtnt()%></div>
</body>
</html>