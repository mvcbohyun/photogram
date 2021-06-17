// (1) 회원정보 수정
function update(userId,event) {
	event.preventDefault();// 폼테그 액션을 막음 
	let data =$("#profileUpdate").serialize();
	console.log(userId);
	
	$.ajax({
		type: "put",
		url:`/api/user/${userId}`,
		data: data,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json"
	}).done(res=>{// HttpStatus 상태코드가 200번대
		console.log("성공",res);
		location.href=`/user/${userId}`
	}).fail(error=>{// HttpStatus 상태코드가 200번대가 아닐때 
		console.log("실패",error.responseJSON.data);
		if(error.data ==null){
		alert(error.responseJSON.message);
		}else{
		alert(JSON.stringify(error.responseJSON.data));
		}
		});
}