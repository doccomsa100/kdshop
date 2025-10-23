package com.kd.basic.common.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewReplyDTO {

	private Integer reply_id;
	private Integer rev_code;
	private String manager_id;
	private String reply_text;
	private LocalDateTime reply_date;
}
