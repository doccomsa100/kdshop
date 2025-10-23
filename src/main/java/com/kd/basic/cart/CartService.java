package com.kd.basic.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {

	private final CartMapper cartMapper;
	
	public void cart_add(CartDTO dto) {
		cartMapper.cart_add(dto);
	}
	
	public List<Map<String, Object>> cart_list(String mbsp_id) {
		return cartMapper.cart_list(mbsp_id);
	}
	
	public Integer getCartTotalPriceByUserId(String mbsp_id) {
		return cartMapper.getCartTotalPriceByUserId(mbsp_id);
	}
	
	public void cart_empty(String mbsp_id) {
		cartMapper.cart_empty(mbsp_id);
	}
	
	public void cart_sel_delete(int[] pro_num, String mbsp_id) {	
		// 서로 다른타입의 정보를 map으로 구성하는 이유는 mybatis에서 사용하기위함이다.		
		HashMap<String, Object> map = new HashMap<>();
		map.put("pro_num_arr", pro_num);
		map.put("mbsp_id", mbsp_id);
		
		cartMapper.cart_sel_delete(map);
	}
	
	public void cart_quantity_change(CartDTO dto) {
		cartMapper.cart_quantity_change(dto);
	}
}
