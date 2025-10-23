package com.kd.basic.admin.review;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kd.basic.common.dto.ReviewDTO;
import com.kd.basic.common.dto.ReviewReplyDTO;
import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminReviewService {

	private final AdminReviewMapper adminReviewMapper;
	
	public List<ReviewDTO> review_list(SearchCriteria cri, String rev_rate, String rev_content) {
		return adminReviewMapper.review_list(cri, rev_rate, rev_content);
	}
	
	public int review_count(SearchCriteria cri, String rev_rate, String rev_content) {
		return adminReviewMapper.review_count(cri, rev_rate, rev_content);
	}
	
	public Map<String, Object> review_info(Integer rev_code) {
		return adminReviewMapper.review_info(rev_code);
	}
	
	public void reply_insert(ReviewReplyDTO dto) {
		adminReviewMapper.reply_insert(dto);
	}
	
	public void reply_modify(ReviewReplyDTO dto) {
		adminReviewMapper.reply_modify(dto);
	}
	
	public void reply_delete(Integer reply_id) {
		adminReviewMapper.reply_delete(reply_id);
	}
}
