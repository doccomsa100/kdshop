package com.kd.basic.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.OrderDTO;

public interface OrderMapper {

	// 주문테이블 insert.   mapper xml파일의 useGeneratedKeys="true" keyProperty="ord_code" OrderDTO클래스의 ord_code필드에 주문번호가 저장됨.
	void order_insert(OrderDTO dto);
	
	// 주문상세테이블 insert : 장바구니테이블의 데이타를 이용
	void order_detail_insert(@Param("ord_code") Integer ord_code, @Param("mbsp_id") String mbsp_id);
	
	// 현재 주문내역.  테이블 조인으로 사용하는 메서드는 Map으로 리턴타입으로 사용한다.
	List<Map<String, Object>> getOrderInfoByOrd_code(Integer ord_code);
	
	// 주문번호 검색
	OrderDTO findOrderById(@Param("ord_code") int ord_code);
	
}
