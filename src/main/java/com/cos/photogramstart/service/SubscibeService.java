package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscibeService {
	
	private final SubscribeRepository subscribeRepository;
	@Transactional
	public void 구독하기(Long fromUserId , Long toUserId) {
		try {
	    subscribeRepository.mSubscibe(fromUserId, toUserId);
		}catch (Exception e) {
			throw new CustomApiException("이미 구독을 하셨습니다.");
		}
	}
	@Transactional
	public void 구독취소하기(Long fromUserId , Long toUserId) {
		subscribeRepository.mUnSubscibe(fromUserId, toUserId);
		
	}
}
