<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div>
		<a href="/bRegmod?typ=${typ}"><button>글쓰기</button></a>
	</div>
	<table>
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성일</td>
		</tr>
		<c:forEach items="${list}" var="item">
			<tr class="pointer" onclick="clkItem(${typ}, ${item.i_board})">
				<td>${item.i_board}</td>
				<td>${item.title}</td>
				<td>${item.r_dt}</td>
			</tr>
		</c:forEach>
	</table>
</div>

<script> //자바스크립트는 서버가 아니라 브라우저, 개인 컴퓨터에서 돌아간다. 그래서 문장에 값을 넣어주지 않고 그대로 보내줘야함.
	function clkItem(typ, i_board) {
		// console.log('typ : ' + typ + ', i_board : ' + i_board);
		//var url = '/bDtail?typ=' +typ +'&i_board=' +i_board;
		var url = `/bDetail?typ=\${typ}&i_board=\${i_board}`;
		console.log('url : ' +url);
		location.href = url; // 주소값 이동
	}
</script>