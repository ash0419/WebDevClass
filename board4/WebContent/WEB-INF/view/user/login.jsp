<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
</head>
<body>
	<div id="centerContainer">
		<div>
			<form action="/user/loginProc.korea" method="post">
				<div>
					<input type="text" name="user_id" value="mic" placeholder="id">
				</div>
				<div>
					<input type="password" name="user_pw" value="1212" placeholder="password">
				</div>
				<div>
					<input type="submit" value="LOGIN">
				</div>
			</form>
			<div style="color: red;">${msg}</div>
			<a href="/join">join</a>
		</div>
	</div>
</body>
</html>