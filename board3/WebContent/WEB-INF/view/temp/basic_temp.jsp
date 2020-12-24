<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<c:forEach items="${jsList}" var="item">
	<script defer src="/res/js/${item}.js"></script>
	<!-- defer는 맨 밑에 놔둔 효과, async는 화면을 안 느려지게 하는 효과 -->
</c:forEach>
<script defer src="/res/js/common.js"></script>
<link rel="stylesheet" href="/res/css/board.css">
<link rel="stylesheet" href="/res/css/common.css">
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
</head>
<body>
	<div id="container" class="themed">
		<header>
			<ul>
				<li>${loginUser.nm}님환영합니다.</li>
				<li><a href="/logout">Logout</a></li>
				<li><a href="/board/list?typ=1">게임</a></li>
				<li><a href="/board/list?typ=2">스포츠</a></li>
				<li><a href="/board/list?typ=3">연예/방송</a></li>
			</ul>
			<input class="check" type="checkbox" />
		</header>
		<section>
			<jsp:include page="${page}" />
		</section>
		<footer></footer>
	</div>
</body>
</html>