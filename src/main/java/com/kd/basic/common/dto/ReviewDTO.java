package com.kd.basic.common.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


// 상품후기 ( 상품후기답변 + 상품 )
@Getter
@Setter
@ToString
public class ReviewDTO {

	//상품후기
	private Integer rev_code;
	private String mbsp_id;
	private Integer pro_num;
	private String rev_content;
	private int rev_rate;
	
	// 이유? 날짜를 JSON으로 변환시 ISO에서 국제표준으로  T 가 들어있는 날짜형식으로 기본 변환한다.
	// 그래서, 원하는 날짜형식으로 변환할려면, 아래와 같은 작업을 해야 한다.
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime rev_date;
	
	//상품
	private ProductDTO product;
	
	// 상품후기답변
	private List<ReviewReplyDTO> replies;
	
	
	
}
