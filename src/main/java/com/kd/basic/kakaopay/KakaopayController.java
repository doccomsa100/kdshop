package com.kd.basic.kakaopay;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.order.OrderService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/kakao/*")
@Controller
public class KakaopayController {

	private final KakaopayService kakaopayService;
	private final OrderService orderService;
	
	private OrderDTO dto;
	
	// 주문정보저장(카카오페이 결제).  ajax 요청
	@PostMapping("/kakaoPay") // 1차결제 준비요청작업 -> 2차결제 승인요청작업
	public ResponseEntity<ReadyResponse> kakaoPay (OrderDTO dto, String item_name, int quantity, HttpSession session ) {
		
		// 1차결제준비요청 작업
			
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
		this.dto = dto;
		
		
//		log.info("주문정보: " + dto);
//		log.info("상품이름: " + item_name);
//		log.info("수량: " + quantity);
		
		
		
		ResponseEntity<ReadyResponse> entity = null;
		
		// 가맹점주문번호
		String partner_order_id = "KDMall-" + mbsp_id + "-" + new Date().toString();
		
		ReadyResponse readyResponse = kakaopayService.ready(partner_order_id, mbsp_id, item_name, quantity, dto.getOrd_price(), 0);
		
		log.info("결제준비요청: " + readyResponse);
		
		entity = new ResponseEntity<ReadyResponse>(readyResponse, HttpStatus.OK);
			
		return entity;
	}
	
	// 카카오페이 API에 아래 매핑주소정보를 제공.
	// QR코드를 찍고 난 후 아래주소가 요청.
	// 결제준비요청 성공및 결제승인 매핑주소.  http://localhost:8888/kakao/approval?pg_token?sdfsdfsfsfwsdfsfs
	@GetMapping("/approval")
	public String approval(String pg_token, RedirectAttributes rttr) {
		
		
		log.info("pg_토큰: " + pg_token);
		
		boolean isPaymentApprove = kakaopayService.approve(pg_token); // 결제승인요청
		
		
		String url = "";
		if(isPaymentApprove == true) {
			
			// db에 저장작업.
			orderService.order_process(dto, "KakaoPay", null, null);
			
			rttr.addAttribute("ord_code", dto.getOrd_code());
			rttr.addAttribute("ord_mail", dto.getOrd_mail());
			
			// /order/order_result?ord_code=10&ord_mail=newcomsa@nate.com
			
			url = "/order/order_result";
			
			
			
		}else {
			url = "/order/order_falil_result";
		}
		
		
		return "redirect:" + url;
	}
	
	
	// 결제취소 매핑주소
	@GetMapping("/cancel")
	public String cancel() {
		
		return "order/order_cancel";
	}
		
	// 결제실패 매핑주소
	@GetMapping("/fail")
	public String fail() {
		
		return "order/order_fail";
	}
}
