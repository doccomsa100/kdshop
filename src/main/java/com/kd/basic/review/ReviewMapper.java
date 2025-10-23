package com.kd.basic.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.ReviewDTO;
import com.kd.basic.common.utils.SearchCriteria;

public interface ReviewMapper {

	// 상품후기목록
	List<ReviewDTO> rev_list(@Param("pro_num") Integer pro_num, @Param("cri") SearchCriteria cri);
	
	// 상품후기개수
	int getReviewCountByPro_num(Integer pro_num);
	
	// 상품후기 저장
	void review_save(ReviewDTO dto);
	
	// 상품후기 삭제
	void review_delete(Integer rev_code);
	
	ReviewDTO review_info(Integer rev_code);
	
	void review_modify(ReviewDTO dto);
}
