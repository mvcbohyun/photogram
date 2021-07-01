package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

	private final LikesRepository likesRepository;
	
	@Transactional
	public void 종아요(Long imageId, Long principalId) {
		likesRepository.mLikes(imageId, principalId);
		
	}
	@Transactional
	public void 종아요취소(Long imageId, Long principalId) {
		// TODO Auto-generated method stub
		likesRepository.mUnLikes(imageId, principalId);
	}
}
