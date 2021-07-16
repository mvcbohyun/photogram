<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="main">
	<div class="containerfind">  
			<input class="findinput" type="text" id="usernamefind" name="usernamefind" placeholder="사용자 이름 검색" />
			<button class="findbutton" onclick="finduserload()">조회</button>
	</div>

		<div class="order" id ="userfind" >
		</div>

</main>
<script src="/js/finduser.js"></script>
</body>
</html>
