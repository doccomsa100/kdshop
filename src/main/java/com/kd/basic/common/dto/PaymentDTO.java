package com.kd.basic.common.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PaymentDTO {

	private Integer payment_id;
	private Integer ord_code;
	private String mbsp_id;
	private String payment_method;
	private int  payment_price;
	private String payment_status;
	private Date payment_date;
}
