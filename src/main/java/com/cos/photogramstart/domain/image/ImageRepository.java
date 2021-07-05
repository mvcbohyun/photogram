package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ImageRepository  extends JpaRepository<Image, Integer> {

	@Query(value = "SELECT * FROM image WHERE userid IN(SELECT touserid  FROM subscribe WHERE fromuserid =:principalId) ORDER BY ID desc", nativeQuery =  true)
	Page<Image> mStory(Long principalId, Pageable pageable);
	
	@Query(value="SELECT  a1.id , a1.caption , a1.createdate , a1.postImageUrl , a1.userid FROM (SELECT a.id , a.caption , a.createdate , a.postImageUrl , a.userid   ,COUNT(a.id) likecount   FROM image a JOIN likes b ON a.id = b.imageid GROUP BY a.id )a1 ORDER BY a1.likecount desc ", nativeQuery = true)
	List<Image> mPopular();
}

