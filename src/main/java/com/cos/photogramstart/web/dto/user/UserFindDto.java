package com.cos.photogramstart.web.dto.user;

import java.math.BigInteger;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFindDto {
	
	private BigInteger id;
	private String username;
	private String profileImageUrl; // 사진
	private String bio;
	private Integer gudok;
}
