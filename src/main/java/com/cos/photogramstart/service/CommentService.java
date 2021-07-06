package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public Comment 댓글쓰기(String content, Long imageId, Long userId) {
		//tip (객체를 만들때 아이디 값만 담아서 insert 할 수 있다 . -- 이렇게 안하면 다 찾아서 진행해야 해서 복잡스)
		// 대신 return 시에 완벽한 객체를 받고 싶으면 이렇게 하면 안됨!
		//image 객체와 user 객체는 id 값만 가지고 있는 빈 객체를 리턴받음.
		Image image = new Image();
		image.setId(imageId);
		User userEntity = userRepository.findById(userId);
		
		if(userEntity==null) {
			throw new CustomApiException("찾을수 없는 유저입니다.");
		}
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(userEntity);
		return commentRepository.save(comment);
	}
	
	@Transactional
	public void 댓글삭제(Long id) {
		try {
			commentRepository.deleteById(id);	
		} catch (Exception e) {
			throw new CustomApiException(e.getMessage());
		}
		
	}
}
