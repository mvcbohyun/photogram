/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */


// (0) 현재 로그인한 아이디
let principalId = $("#principalId").val();


finduserload() ;
function finduserload() {
	
	let usernamefind = $("#usernamefind").val();
	
	let order = $("input[name=order]:checked").val();
	


 
$.ajax({
	url:`/api/user/search?page=0`,
	dataType : 'json', 
	data : { searchuser :usernamefind ,
			 order: order }
}).done(res=>{
	console.log(res);
	$(".finduser").remove();
	res.data.forEach((users)=>{
		
		let storyItem = getUsers(users);
		console.log(storyItem)
		$("#userfind").append(storyItem);
	})
}).fail(error=>{
	console.log("오류",error);
})
}

// (1) 스토리 로드하기
let page=0;


function finduser() {
	
	let usernamefind = $("#usernamefind").val();
	let order = $("input[name=order]:checked").val();
	
	
console.log(" finduser page :" +page)

 
$.ajax({
	url:`/api/user/search?page=${page}`,
	dataType : 'json', 
	data : { searchuser :usernamefind ,
			 order: order }
}).done(res=>{
	console.log(res);
	res.data.forEach((users)=>{
		let storyItem = getUsers(users);
		console.log(storyItem)
		$("#userfind").append(storyItem);
	})
}).fail(error=>{
	console.log("오류",error);
})
}


function getUsers(users) {
 let item =`

	  <div class="finduser" >
		<div class="subscribe__item" id="subscribeModalItem-${users.id}">
			<div class="subscribe__img">
				<a href="/user/${users.id}">
					<img class = "findimage"src="/upload/${users.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />
				</a>
			</div>
			<div class="subscribe__text" >
				<h2>${users.username}</h2>
			</div>
			<div class="subscribe__btn">
				<button>`;
				if(users.gudok){
					item+=`<button class="cta blue" onclick="toggleSubscribe(${users.id},this)">구독취소</button>`
				}else{
					item+=`<button class="cta" onclick="toggleSubscribe(${users.id},this)">구독하기</button>`
				}
				
					
				item+=`</button>
			</div>
			<div>
				${users.bio}
			<div>
	</div>
	
	</div>
		
			
					
					
					
			
		`
			return item;
			
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	//console.log("윈도우 scrollTop",$(window).scrollTop());
	//console.log("문서 height",$(document).height());
	//console.log("윈도우 height",$(window).height());
	
	let checkNum = $(window).scrollTop() -($(document).height() -$(window).height())
	
	if(checkNum<1&& checkNum>-1){
		console.log("page :" +page)
		page= page+10;
		finduser();
	}
});


// (3) 좋아요, 안좋아요
function toggleSubscribe(toUserid,obj) {
	if ($(obj).text() === "구독취소") {
	
		$.ajax({
			type:"delete",
			url:"/api/subscribe/"+toUserid,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error =>{
			console.log("구독취소실패",error)
		});
		
	} else {
	
		$.ajax({
			type:"post",
			url:"/api/subscribe/"+toUserid,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error =>{
			console.log("구독하기실패",error)
		});
	}
}








