package com.kd.basic.admin.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kd.basic.admin.delivery.AdminDeliveryMapper;
import com.kd.basic.admin.payment.AdminPaymentMapper;
import com.kd.basic.common.dto.DeliveryDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminOrderService {

	private final AdminOrderMapper adminOrderMapper;
	private final AdminPaymentMapper adminPaymentMapper;
	private final AdminDeliveryMapper adminDeliveryMapper;
	
	public List<Map<String, Object>> order_list(
			SearchCriteria cri, String period, String start_date, String end_date, String payment_method, String ord_status
			) {
		return adminOrderMapper.order_list(cri, period, start_date, end_date, payment_method, ord_status);
	}
	
	public int getTotalcount(SearchCriteria cri, String period, String start_date, String end_date, String payment_method, String ord_status) {
		return adminOrderMapper.getTotalcount(cri, period, start_date, end_date, payment_method, ord_status);
	}
	
	@Transactional
	public void order_status_change(Integer ord_code, String ord_status) {
		// 주문테이블의 주문상태가 입금미납에서 입금완료 변경작업
		adminOrderMapper.order_status_change(ord_code, ord_status);
		
		if(ord_status.equals("입금완료")) {
			// 결제테이블의 결제상태도 입금완료 변경작업
			adminPaymentMapper.payment_status_change(ord_code, ord_status);
		}
	}
	
	public List<Map<String, Object>> orderdetail_info(Integer ord_code) {
		return adminOrderMapper.orderdetail_info(ord_code);
	}
	
	public OrderDTO order_info(Integer ord_code) {
		return adminOrderMapper.order_info(ord_code);
	}
	
	// 주문내역 개별상품삭제
	@Transactional
	public void order_product_del(Integer ord_code, Integer pro_num, int unitprice) {
		
		// 개별상품삭제
		adminOrderMapper.order_product_del(ord_code, pro_num);
		// 주문테이블의 전체금액 - 개별상품금액 빼기(삭제상품금액)
		adminOrderMapper.order_price_change(ord_code, unitprice);
		// 결제테이블의 전제금액 - 개별상품금액 빼기(삭제상품금액)
		adminPaymentMapper.payment_price_change(ord_code, unitprice);
		
	}
	
	public void admin_ord_message(Integer ord_code, String ord_message) {
		adminOrderMapper.admin_ord_message(ord_code, ord_message);
	}
	
	@Transactional
	public void order_info_edit(OrderDTO dto) {
		
		// 주문테이블 배송지정보 수정
		adminOrderMapper.order_info_edit(dto);
		// 배송테이블 배송지정보 수정.
		
		DeliveryDTO deliveryDTO = new DeliveryDTO();
		deliveryDTO.setOrd_code(dto.getOrd_code());
		deliveryDTO.setShipping_addr(dto.getOrd_addr_basic());
		deliveryDTO.setShipping_deaddr(dto.getOrd_addr_detail());
		deliveryDTO.setShipping_zipcode(dto.getOrd_addr_zipcode());
		
		adminDeliveryMapper.delivery_info_edit(deliveryDTO);
	}
	
	
	
	
	
	
	
	
}
