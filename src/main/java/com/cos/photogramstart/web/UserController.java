package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDtails;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable Long pageUserId
						  ,Model model
						  , @AuthenticationPrincipal PrincipalDtails principalDtails) {
		UserProfileDto dto = userService.회원프로필(pageUserId,principalDtails.getUser().getId());
		model.addAttribute("dto",dto);
		return "user/profile";
	}
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable Long id, @AuthenticationPrincipal PrincipalDtails principalDtails) {
		// 이걸로 찾으면 세션을 쉽게 찾을수 있음
		//System.out.println("세션정보 : "+ principalDtails.getUser());
		
		
		//이렇게도 찾을수 있음 사용하지 말것 !
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		//PrincipalDtails mPrincipalDtails  = (PrincipalDtails)auth.getPrincipal();
		//System.out.println("직접 찾은 세션정보 : "+mPrincipalDtails.getUser());
		return "user/update";
	}
}

