package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ImageRepository  extends JpaRepository<Image, Integer> {

	@Query(value = "SELECT * FROM image WHERE userid IN(SELECT touserid  FROM subscribe WHERE fromuserid =:principalId) ORDER BY ID desc", nativeQuery =  true)
	Page<Image> mStory(Long principalId, Pageable pageable);
}

