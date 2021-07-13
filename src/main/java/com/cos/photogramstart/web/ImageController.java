package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDtails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;
	@GetMapping({"/","/image/story"})
	public String story() {
		return "image/story";
	}
	
	

	@GetMapping("/image/popular")
	public String popular(Model model ) {
		
		//api는 데이터를 리턴하는 서버 !
		List<Image> images = imageService.인기사진();
		
		model.addAttribute("images",images);
		
		return "image/popular";
	}
	
	@GetMapping("/image/find")
	public String find(Model model ) {
		
		//api는 데이터를 리턴하는 서버 !
		List<Image> images = imageService.최신사진();
		
		model.addAttribute("images",images);
		
		return "image/find";
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDtails principalDtails) {
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다", null);
		}
		
		// service 호출 
		imageService.사진업로드(imageUploadDto, principalDtails);
		return "redirect:/user/"+principalDtails.getUser().getId();
	}
}
