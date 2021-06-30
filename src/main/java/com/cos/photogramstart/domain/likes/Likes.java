package com.cos.photogramstart.domain.likes;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
					name="subscibe_uk",
					columnNames= {"imageId","userId"}
				)// 두개를 유니크 값으로 잡음
		}
)
public class Likes {//N
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)//번호 증가 전략이 데이터 베이스를 따라간다.
	private Long id;
	
	@JoinColumn(name = "imageId")
	@ManyToOne
	private Image image;//1
	
	// 오류가 터지고 나서 잡자 !
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;//1
	
	private LocalDateTime createDate;
	
	@PrePersist// 디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate= LocalDateTime.now();
	}

}
