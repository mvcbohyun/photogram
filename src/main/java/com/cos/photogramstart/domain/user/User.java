package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA -- java persistence api(자바로 데이터를 영구적으로 저장할 수 있는 api를 제공)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)//번호 증가 전략이 데이터 베이스를 따라간다.
	private Long id;
	
	@Column(length = 100, unique = true)// OAuth2 로그인을 위해 컬럼 늘리기
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String website;//웹사이트
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;

	private String profileImageUrl; // 사진
	private String role; // 권한
	
	//나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마
	//user를 select 할때 해달 user id 로 등록된 image들을 다 가져와!
	//LAZY = User를 select 할떄 해당 User id로 등록된 image들을 가져오지말고 내가 부를때 가져와 getimages() 호출될때 !
	//EAGER = User를 SELECT 할떄 해당 user id 등록된 image득ㄹ을 전부 join 해서 가져와 
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"})
	private List<Image> images;// 양방향 매핑!
	
	
	private LocalDateTime createDate;

	
	@PrePersist// 디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate= LocalDateTime.now();
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role + ", createDate=" + createDate + "]";
	}
}
