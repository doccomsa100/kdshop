package com.kd.basic.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kd.basic.cart.CartMapper;
import com.kd.basic.common.dto.DeliveryDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.dto.PaymentDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderMapper orderMapper;
	private final PaymentMapper paymentMapper;
	private final DeliveryMapper deliveryMapper;
	private final CartMapper cartMapper;
	
	// 주문, 주문상세, 결제, 배송, 장바구니 테이블
	@Transactional  // DB의 트랜잭션기능을 스프링에서 제어하도록 만든 어노테이션
	public void order_process(OrderDTO dto, String p_method, String account_transfer, String sender) {
		log.info("주문번호: " + dto.getOrd_code()); // null 출력
		
		// 1)주문.  dto객체의 주소
		orderMapper.order_insert(dto); // 이 메서드가 실행되면, ord_code 필드에 주문번호 저장된다.
		
		log.info("주문번호: " + dto.getOrd_code()); // 일련번호 출력
		
				
		// 결제종류
		String p_method_info = "";
		String payment_status = "";
		if(!p_method.equals("KakaoPay")) { // 무통장입금
			p_method_info = p_method + "/" + account_transfer + "/" + sender;
			payment_status = "입금미납";
			
		}else {  // KakaoPay
			p_method_info = p_method;
			payment_status = "입금완료";
		}
		
		
		// 2)주문상세 : 장바구니테이블의 데이타를 참고하여, 구성.
		dto.setOrd_status(payment_status);
		orderMapper.order_detail_insert(dto.getOrd_code(), dto.getMbsp_id());
		
		// 3)결제.
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setOrd_code(dto.getOrd_code()); // 주문번호
		paymentDTO.setMbsp_id(dto.getMbsp_id());
		paymentDTO.setPayment_method(p_method_info);  // 무통장/국민은행/홍길동
		paymentDTO.setPayment_price(dto.getOrd_price());
		
		paymentDTO.setPayment_status(payment_status);
		
		paymentMapper.payment_insert(paymentDTO);
		
		// 4)배송
		DeliveryDTO deliveryDTO = new DeliveryDTO();
		deliveryDTO.setOrd_code(dto.getOrd_code()); // 주문번호
		deliveryDTO.setShipping_zipcode(dto.getOrd_addr_zipcode());
		deliveryDTO.setShipping_addr(dto.getOrd_addr_basic());
		deliveryDTO.setShipping_deaddr(dto.getOrd_addr_detail());
		
		deliveryMapper.delivery_insert(deliveryDTO);
		
		
		
		// 5)장바구니
		cartMapper.cart_empty(dto.getMbsp_id());
	}
	
	// 현재 주문내역
	public List<Map<String, Object>> getOrderInfoByOrd_code(Integer ord_code) {
		return orderMapper.getOrderInfoByOrd_code(ord_code);
	}
}
