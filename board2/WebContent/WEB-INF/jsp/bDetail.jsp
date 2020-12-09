<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<a href="/bList?typ=${item.typ}">돌아가기</a>
	<form action="/bDetail" method="post" onsubmit="isDel(event)">
		<input type="hidden" name="typ" value="${item.typ}">
		<input type="hidden" name="i_board" value="${item.i_board}">
		<input type="submit" value="글 삭제" / onclick="return isDel();">
	</form>
	<a href="/bRegmod?typ=${item.typ}&i_board=${item.i_board}"><button>수정</button></a>
</div>
<div>
	<div>번호 : ${item.i_board}</div>
	<div>제목 : ${item.title}</div>
	<div>내용 : ${item.ctnt}</div>
	<div>조회수 : ${item.hits}</div>
	<div>작성일 : ${item.r_dt}</div>
</div>
<script>
	function isDel(e) {
		var result = confirm('삭제 하시겠습니까?');
		if (!result) {
			e.preventDefault();
		}
	}
	<c:if test="${msg != null}">
		alert('${msg}');
	</c:if>
</script>