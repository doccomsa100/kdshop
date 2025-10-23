package com.kd.basic.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.utils.SearchCriteria;

public interface AdminOrderMapper {

	// 값의 타입을 Object으로 사용한 이유? 컬럼의 데이타타입이 문자형, 숫자형, 날짜형에 해당하는 다양한 데이타타입이므로
	// 전체주문목록  SearchCriteria 클래스 필드 :int page=1, int perPageNum=10, String searchType=null,String keyword=null
	List<Map<String, Object>> order_list(
			@Param("cri") SearchCriteria cri, 
			@Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date,
			@Param("payment_method") String payment_method,
			@Param("ord_status") String ord_status
			);
	
	int getTotalcount(@Param("cri") SearchCriteria cri, 
			@Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date,
			@Param("payment_method") String payment_method,
			@Param("ord_status") String ord_status);
	
	void order_status_change(@Param("ord_code") Integer ord_code, 
			@Param("ord_status") String ord_status);
	
	
	// 주문상세내역
	List<Map<String, Object>> orderdetail_info(Integer ord_code);
	
	// 주문내역
	OrderDTO order_info(Integer ord_code);
	
	// 주문내역 개별상품삭제
	void order_product_del(@Param("ord_code") Integer ord_code, @Param("pro_num") Integer pro_num);
	
	// 주문금액 - 개별상품삭제금액
	void order_price_change(@Param("ord_code") Integer ord_code, @Param("unitprice") int unitprice);
	
	// 관리자 주문메세지 수정
	void admin_ord_message(@Param("ord_code") Integer ord_code, @Param("ord_message") String ord_message);
	
	// 배송지 정보수정
	void order_info_edit(OrderDTO dto);
	
	
	
	
}
