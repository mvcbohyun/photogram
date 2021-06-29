package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;
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
	public List<SubscribeDto> 구독한리스트(Long principalId, Long pageUserId) {
		//쿼리 작성
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id , a.username ,ifnull(a.profileImageUrl,'') as profileImageUrl, ");
		sb.append("IF((SELECT 1  FROM Subscribe c WHERE c.fromuserid = ? AND c.touserid = a.id),1,0) as  subscribeState, ");
		sb.append("IF((?=a.id  ),1,0) as equalUserState ");
		sb.append("FROM user a ");
		sb.append("JOIN Subscribe b ");
		sb.append("ON a.id = b.touserid ");
		sb.append("WHERE b.fromuserid = ? ");
		//쿼리 완성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		//1.물음표 principalId
		//1.물음표 principalId
		//2.물음표 pageUserId
		//쿼리 실행(qlrm 라이브러리 필요 -- dto 에 매핑 하기 위해서 !)
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos= result.list(query, SubscribeDto.class);
		//qlrm -- 라이브러리 사용 
		return subscribeDtos;
	}
	
	public List<SubscribeDto> 구독자리스트(Long principalId, Long pageUserId) {
		//쿼리 작성
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id , a.username ,ifnull(a.profileImageUrl,'') as profileImageUrl, ");
		sb.append("IF((SELECT 1  FROM Subscribe c WHERE c.fromuserid = ? AND c.touserid = a.id),1,0) as  subscribeState, ");
		sb.append("IF((?=a.id  ),1,0) as equalUserState ");
		sb.append("FROM user a ");
		sb.append("JOIN Subscribe b ");
		sb.append("ON a.id = b.fromUserId ");
		sb.append("WHERE b.touserid = ? ");
		//쿼리 완성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		//1.물음표 principalId
		//2.물음표 principalId
		//3.물음표 pageUserId
		//쿼리 실행(qlrm 라이브러리 필요 -- dto 에 매핑 하기 위해서 !)
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos= result.list(query, SubscribeDto.class);
		//qlrm -- 라이브러리 사용 
		return subscribeDtos;
	}
}
