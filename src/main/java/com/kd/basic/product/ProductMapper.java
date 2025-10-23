package com.kd.basic.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.ProductDTO;
import com.kd.basic.common.utils.Criteria;

public interface ProductMapper {

	// 2차카테고리에 해당하는 상품리스트
	// limit 0, 10
	List<ProductDTO> getProductListBysecondCategory(@Param("cri") Criteria cri, @Param("cate_code") Integer cate_code);
	
	int getProductListCountBysecondCategory(Integer cate_code);
	
	ProductDTO pro_detail(Integer pro_num);
	
	void review_count_update(Integer pro_num);
	
	// 상품후기 댓글개수
	int getProductReviewCountByPro_num(Integer pro_num);
}
