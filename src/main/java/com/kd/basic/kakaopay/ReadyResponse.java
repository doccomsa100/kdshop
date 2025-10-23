package com.kd.basic.kakaopay;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 1-2)결제준비 요청을 했을 경우, 카카오페이 서버로부터 받는 파라미터를 필드로하는 클래스

@Getter
@Setter
@ToString
public class ReadyResponse {

	private String tid; // 결제 고유 번호, 20자
	private String next_redirect_pc_url; // 카카오톡으로 결제 요청 메시지(TMS)를 보내기 위한 사용자 정보 입력 화면 Redirect URL
	private Date created_at; // 결제 준비 요청 시간
}
