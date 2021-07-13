package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDtails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //readOnly = true-- 영속성 컨텍스트에서 변경 감지를 해서 , 더티체킹 ,flush(반영) X 
public class ImageService {

	private final ImageRepository imageRepository;
	
	@Value("${file.path}")// yml 에 있는거 가져 오기 
	private String uploadFolder;
	
	public  Page<Image> 이미지스토리(Long principalId, Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId,pageable);
		
		
		//2번으로 로그인
		//images에 좋아요 상태 담기
		
		images.forEach((image)->{
			
			image.setLikeCount(image.getLikes().size());
			image.getLikes().forEach((like)->{
				if(like.getUser().getId()==principalId) {//해당 이미지에 좋아요 한사람인지 아닌지
					image.setLikeState(true);
				}
			});
		});
		
		
		return images;
	}
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto ,PrincipalDtails principalDtails) {
	
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_"+imageUploadDto.getFile().getOriginalFilename();//1.jpg
		System.out.println("이미지 파일 이름 :"+imageFileName);
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		
		//통신,I/O -> 예외가 발생 할 수 있다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//image테이블 저장
		Image image =imageUploadDto.toEntity(imageFileName,principalDtails.getUser());
		Image imageEntity = imageRepository.save(image);
		System.out.println(imageEntity);
	}

	public List<Image> 인기사진() {
		
		return imageRepository.mPopular();
	}

	public List<Image> 최신사진() {
		// TODO Auto-generated method stub
		return imageRepository.mFind();
	}
	
}
