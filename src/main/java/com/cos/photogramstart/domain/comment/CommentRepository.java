package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	void deleteById(Long id);

// 네이티브 쿼리는 int만 리턴 받을수 있음
//	@Modifying
//	@Query(value = "INSERT INTO comment(content,imageId,userId,createDate) VALUES(:content,:imageId,:userId,now())",nativeQuery = true)
//	Comment mSave(String content,Long imageId,Long userId);
}
