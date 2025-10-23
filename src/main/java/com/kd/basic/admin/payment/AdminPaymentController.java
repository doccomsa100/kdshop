package com.kd.basic.admin.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/admin/payment/*")
@Controller
public class AdminPaymentController {

	private final AdminPaymentService adminPaymentService;
}
