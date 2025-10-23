package com.kd.basic.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartMapper {

	//장바구니테이블 추가하기 - 로그인한 사용자
	void cart_add(CartDTO dto);
	
	//장바구니 목록(장바구니, 상품 테이블 조인) - 로그인한 사용자
	List<Map<String, Object>> cart_list(String mbsp_id);
	
	//장바구니 총금액(로그인한 사용자)
	Integer getCartTotalPriceByUserId(String mbsp_id);
	
	//장바구니 비우기
	void cart_empty(String mbsp_id);
	
	// 선택 장바구니 삭제하기
	void cart_sel_delete(HashMap<String, Object> map);
	
	// 수량변경하기
	void cart_quantity_change(CartDTO dto);
	
}
