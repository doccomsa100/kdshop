package com.kd.basic.admin.payment;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.PaymentDTO;

public interface AdminPaymentMapper {

	// 결제상태 변경
	void payment_status_change(@Param("ord_code") Integer ord_code, @Param("payment_status") String payment_status);
	
	// 결제내역
	PaymentDTO payment_info(Integer ord_code);
	
	// 결제금액 차감(상품삭제금액)
	void payment_price_change(@Param("ord_code") Integer ord_code, @Param("unitprice") int unitprice);
}
