package com.kd.basic.kakaopay;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


// 1-1)결제준비 요청에 필요한 파라미터를 필드로 구성하고 있는 클래스

@Getter
@AllArgsConstructor // 모든필드를 매개변수로 하여, 생성자메서드 생성
@Builder  // 디자인 패턴중 빌더패턴을 적용
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReadyRequest {

	private String cid;  // 가맹점 코드, 10자
	private String partner_order_id;  // 가맹점 주문번호, 최대 100자
	private String partner_user_id; // 가맹점 회원 id, 최대 100자
	private String item_name; // 상품명, 최대 100자
	private Integer quantity; // 상품 수량
	private Integer total_amount; // 상품 총액
	private Integer tax_free_amount; // 상품 비과세 금액
	private String approval_url;  // 결제 성공 시 redirect url, 최대 255자
	private String cancel_url;  // 결제 취소 시 redirect url, 최대 255자
	private String fail_url; // 결제 실패 시 redirect url, 최대 255자
	
	public ReadyRequest(String cid, String partner_order_id) {
		this.cid = cid;
		this.partner_order_id = partner_order_id;
	}
}
