package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;

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
	
	@Column(length = 20, unique = true)
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
	
	private LocalDateTime createDate;
	
	@PrePersist// 디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate= LocalDateTime.now();
	}
}
