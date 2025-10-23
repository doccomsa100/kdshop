package com.kd.basic.admin.review;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.ReviewDTO;
import com.kd.basic.common.dto.ReviewReplyDTO;
import com.kd.basic.common.utils.SearchCriteria;

public interface AdminReviewMapper {

	List<ReviewDTO> review_list(@Param("cri") SearchCriteria cri, @Param("rev_rate") String rev_rate, @Param("rev_content") String rev_content);
	
	int review_count(@Param("cri") SearchCriteria cri, @Param("rev_rate") String rev_rate, @Param("rev_content") String rev_content);
	
	Map<String, Object>  review_info(Integer rev_code);
	
	void reply_insert(ReviewReplyDTO dto);
	
	void reply_modify(ReviewReplyDTO dto);
	
	void reply_delete(Integer reply_id);
}
