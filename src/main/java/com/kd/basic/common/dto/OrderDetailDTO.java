package com.kd.basic.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class OrderDetailDTO {

	private Integer ord_code;
	private Integer pro_num;
	private int dt_amount;
	private int dt_price;
}
