<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<a href="list?typ=${data.typ}">돌아가기</a>

	<c:if test="${data.i_user == loginUser.i_user}">
		<button onclick="clkDel(${data.i_board}, ${data.typ});">삭제</button>
		<a href="regmod?typ=${data.typ}&i_board=${data.i_board}">
			<button>수정</button>
		</a>
	</c:if>
	<div>
		<div>번호 : ${data.seq}</div>
		<div>조회수 : ${data.hits}</div>
		<div>이름 : ${data.nm}</div>
		<div>제목 : ${data.title}</div>
		<div>작성일 : ${data.r_dt}</div>
		<div>${data.ctnt}</div>
	</div>
	<div style="margin-top: 20px;">
		<div>
			<form action="cmt/reg" method="post">
				<input type="hidden" name="i_board" value="${data.i_board}">
				댓글:
				<input type="text" name="cmt_ctnt">
				<input type="submit" value="댓글쓰기">
			</form>
		</div>
		<div>
			<table>
				<tr>
					<th>댓글</th>
				</tr>
				<c:forEach items="${cmtList}" var="item">
					<tr>
						<td>${item.ctnt}</td>
					</tr>
				</c:forEach>
			</table>

		</div>
	</div>
</div>
<script>
	function isDel(e) {
		var result = confirm('삭제 하시겠습니까?');
		if(!result) {
			e.preventDefault();
		}
	}
	<c:if test="${msg != null}">
		alert('${msg}');
	</c:if>
</script>