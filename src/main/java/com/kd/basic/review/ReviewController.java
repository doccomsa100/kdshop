package com.kd.basic.review;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.dto.ReviewDTO;
import com.kd.basic.common.utils.Criteria;
import com.kd.basic.common.utils.SearchCriteria;
import com.kd.basic.product.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review/*")
@RestController  // 타임리프 페이지 사용안함. 상품상세페이지에 일부를 사용.
public class ReviewController {

	private final ReviewService reviewService;
	private final ProductService productService;
	
	// /review/rev_list/1/1
	@GetMapping("/rev_list/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> rev_list(@PathVariable("pro_num") Integer pro_num, @PathVariable("page") int page) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
	    entity = new ResponseEntity<Map<String,Object>>(reviewService.getReviewList(pro_num, page), HttpStatus.OK);
		
		return entity;
	}
	
	// 상품후기 저장
	@PostMapping(value = "/review_save", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_save(@RequestBody ReviewDTO dto, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
		reviewService.review_save(dto); // 상품후기저장, 상품후기 카운드 변경
		
		int review_count = productService.getProductReviewCountByPro_num(dto.getPro_num());
		
		entity = new ResponseEntity<String>( String.valueOf(review_count), HttpStatus.OK);
		
		return entity;
	}
	
	@DeleteMapping("/review_delete/{rev_code}")
	public ResponseEntity<String> review_delete(@PathVariable("rev_code") Integer rev_code) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		reviewService.review_delete(rev_code);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		
		return entity;
		
	}
	
	// 수정목적으로 사용할 상품후기정보를 JSON 포맷으로 클라이언트에게 보낸다.
	@GetMapping(value = "/review_info/{rev_code}")
	public ResponseEntity<ReviewDTO> review_info(@PathVariable("rev_code") Integer rev_code) throws Exception {
		
		log.info("후기코드: " + rev_code);
		
		ResponseEntity<ReviewDTO> entity = null;
		
		entity = new ResponseEntity<ReviewDTO>(reviewService.review_info(rev_code), HttpStatus.OK);
		
		return entity;
	}
	
	// 상품후기 수정하기
	@PutMapping(value = "/review_modify", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_modify(@RequestBody ReviewDTO dto) throws Exception {
		ResponseEntity<String> entity = null;
		
		reviewService.review_modify(dto);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
}
