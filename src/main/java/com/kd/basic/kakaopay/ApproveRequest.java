package com.kd.basic.kakaopay;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// 2-1)결제승인 요청에 필요한 파라미터를 필드로하는 클래스.

@Getter
@AllArgsConstructor // 모든 필드를 대상으로 생성자메서드 생성
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApproveRequest {

	private String cid; // 가맹점 코드, 10자
	private String tid; // 결제 고유번호, 결제 준비 API 응답에 포함
	private String partner_order_id; // 가맹점 주문번호, 결제 준비 API 요청과 일치해야 함
	private String partner_user_id; // 가맹점 회원 id, 결제 준비 API 요청과 일치해야 함
	private String pg_token; // 결제승인 요청을 인증하는 토큰.  사용자 결제 수단 선택 완료 시, approval_url로 redirection 해줄 때 pg_token을 query string으로 전달
	
}
