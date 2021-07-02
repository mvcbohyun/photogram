package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)//번호 증가 전략이 데이터 베이스를 따라간다.
	private Long id;
	
	private String caption;// 사진 내용 
	private String postImageUrl;// 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 -DB에 그 저장된 경로를 insert
	@JsonIgnoreProperties({"images"}) // user에 있는 images는 무시해 
	@JoinColumn(
			name="userid"
			)
	@ManyToOne
	private User user;
	
	
	
	//  이미지 좋아요
	@JsonIgnoreProperties({"image"}) // Likes에 있는 images는 무시해 
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;
	
	//  댓글
	
	private LocalDateTime createDate;
	
	@PrePersist// 디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate= LocalDateTime.now();
	}
	//오브젝트를 콘솔에 출력할 떄 문제가 될 수 있어서  User 부분을 삭제
	@Override
	public String toString() {
		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl + ", createDate="
				+ createDate + "]";
	}
	
	@Transient//디비에 컬럼이 만들어 지지 않는다 
	private boolean likeState;

	@Transient
	private int likeCount;

}
