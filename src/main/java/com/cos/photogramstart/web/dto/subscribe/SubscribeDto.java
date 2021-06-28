package com.cos.photogramstart.web.dto.subscribe;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {

	private BigInteger id;
	private String username;
	private String profileImageUrl;
	private Integer subscribeState; // 구독했니 안했니 확인
	private Integer equalUserState;// 로그인 한 사람이 동일인 인지 아니인지 

}
