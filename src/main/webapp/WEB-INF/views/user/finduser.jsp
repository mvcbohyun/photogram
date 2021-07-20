<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="main">
	<div class="containerfind">  
			<input class="findinput" type="text" id="usernamefind" name="usernamefind" placeholder="사용자 이름 검색" />
			<button class="findbutton" onclick="finduserload()">조회</button>
			<div class="radiogroup">
			 <label><input type="radio"id="order"name="order" value="최신" checked="checked">최신순</label>
      		 <label><input type="radio"id="order" name="order" value="팔로우"> 팔로우순</label>
      		
			</div>
	</div>

		<div class="order" id ="userfind" >
		</div>

</main>
<script src="/js/finduser.js"></script>
</body>
</html>
