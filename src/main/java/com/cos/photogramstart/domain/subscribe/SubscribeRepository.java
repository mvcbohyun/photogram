package com.cos.photogramstart.domain.subscribe;

import java.util.concurrent.Flow.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

	@Modifying// insert , delete ,.update 를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요 !
	@Query(value = "INSERT INTO subscribe(fromUserId , toUserId,createDate) VALUES (:fromUserId,:toUserId,now())" ,nativeQuery = true)
	void mSubscibe(Long fromUserId, Long toUserId);// 1(변경된 행의 개수가  리턴됨) , -1 
	
	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId =:fromUserId AND toUserId = :toUserId" ,nativeQuery = true)
	void mUnSubscibe(Long fromUserId, Long toUserId);// 1 , -1
}
