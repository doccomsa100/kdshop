package com.kd.basic.kakaopay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;


// application.properties 파일의 정보들은 Controller, Service 클래스에서 특별한 설정없이 참조가능하다.

@Slf4j
@Service
public class KakaopayService {
	
	@Value("${readUrl}")
	private String readUrl;
	
	@Value("${approveUrl}")
	private String approveUrl;
	
	@Value("${secretKey}")
	private String secretKey;
	
	@Value("${cid}")
	private String cid;
	
	@Value("${approval}")
	private String approval;
	
	@Value("${cancel}")
	private String cancel;
	
	@Value("${fail}")
	private String fail;
	

	// 1차 결제준비요청, 2차 결제승인요청에서 사용하기위한 전역변수 선언
	String tid; // 1차 결제준비요청에서 보내온 값을 2차 결제승인요청에서 사용해야 하므로.
	String partner_order_id;
	String partner_user_id;
	
	// 1차 결제준비요청작업(ready) - 요청: ReadyRequest 클래스 응답: ReadyResponse 클래스 
	public ReadyResponse ready(String partner_order_id,String partner_user_id,String item_name,
								Integer quantity,Integer total_amount,Integer tax_free_amount) {
		
		// 1)Header
		HttpHeaders headers = getHttpHeaders();
		// 2)Body. ReadyRequest 클래스가 빌더패턴이 적용되어 아래 구문을 사용할수가 있다.
		ReadyRequest readyRequest = ReadyRequest.builder()
				.cid(cid)
				
				// 컨트롤러에서 전달 받는작업
				.partner_order_id(partner_order_id)  // 주문번호
				.partner_user_id(partner_user_id)    // 아이디
				.item_name(item_name)                // 상품이름
				.quantity(quantity)                  // 수량
				.total_amount(total_amount)          // 전체금액
				.tax_free_amount(tax_free_amount)    // 부가세 0
				
				 
				.approval_url(approval)  // 결제성공 주소. 결제준비요청이 완료(성공)되면, 카카오페이 API서버에서 호출되는 용도로 사용
				.cancel_url(cancel)    // 결제취소 주소
				.fail_url(fail)      // 결제실패 주소
				.build();
		
		// 3)데이터 준비
		// 1)Header + 2)Body 를 HttpEntity클래스 객체로 생성
		HttpEntity<ReadyRequest> entityMap = new HttpEntity<ReadyRequest>(readyRequest, headers);
		
		// 결제준비요청
		ResponseEntity<ReadyResponse> response = new RestTemplate().postForEntity(readUrl, entityMap, ReadyResponse.class);
		
		// 응답데이터. tid, next_redirect_app_url, created_at
		ReadyResponse readyResponse = response.getBody();
		
		
		// 2차 결제승인요청에서 사용하기위한 준비.
		tid = readyResponse.getTid();
		this.partner_order_id = partner_order_id;
		this.partner_user_id = partner_user_id;
		
		return readyResponse;
	}
	
		
	// 2차 결제승인요청작업(approve)
	public boolean approve(String pg_token) {
		
		// 1)Request Header
		HttpHeaders headers = getHttpHeaders();
		// 2)Body 요청: ApproveRequest.java   응답: ApproveResponse.java
		ApproveRequest approveRequest = ApproveRequest.builder()
				
							.cid(cid)
							.tid(tid)	  // 1차 결제준비요청에 의하여 받는 파라미터 값.
							.partner_order_id(partner_order_id) // 1차 결제준비요청에서 보낸 동일한 값을 다시 보내야 한다. 
							.partner_user_id(partner_user_id)  // 1차 결제준비요청에서 보낸 동일한 값을 다시 보내야 한다. 
							.pg_token(pg_token)
				
							.build();
		
		// 2차승인요청에 보낼 정보. header, body를 하나의 단위로 조합.
		HttpEntity<ApproveRequest> entityMap = new HttpEntity<>(approveRequest, headers);
		
		// 결제승인요청작업. 응답데이타는 response
		ResponseEntity<ApproveResponse> response = new RestTemplate().postForEntity(approveUrl, entityMap, ApproveResponse.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			return true;
		}else {
			return false;
		}

	}
	
	
		
	/*
	 Authorization: SECRET_KEY ${SECRET_KEY}
	 Content-Type: application/json
	 */
	
	public HttpHeaders getHttpHeaders() {
		 HttpHeaders headers = new HttpHeaders();
		 headers.set("Authorization", "SECRET_KEY " + secretKey);
		 headers.set("Content-Type", "application/json;charset=utf-8");
		 
		 return headers;
	}
	
}
