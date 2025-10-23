package com.kd.basic.admin.order;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.admin.payment.AdminPaymentService;
import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.dto.PaymentDTO;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.common.utils.PageMaker;
import com.kd.basic.common.utils.SearchCriteria;
import com.kd.basic.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/order/*")
@Controller
public class AdminOrderController {

	private final AdminOrderService adminOrderService;
	private final AdminPaymentService adminPaymentService;
	
	private final MemberService memberService;
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	// 1  2  3   4   5 페이지번호를 클릭시 요청주소
	// 전체주문목록  SearchCriteria 클래스 필드 :int page=1, int perPageNum=10, String searchType=null,String keyword=null  
	@GetMapping("/order_list")  // SearchCriteria cri = new SearchCriteria();
	public void order_list(@ModelAttribute("cri") SearchCriteria cri, 
			@ModelAttribute("period") String period, @ModelAttribute("start_date") String start_date, @ModelAttribute("end_date") String end_date,
			@ModelAttribute("payment_method") String payment_method,
			@ModelAttribute("ord_status") String ord_status,
			Model model) throws Exception {
			
		cri.setPerPageNum(2);
		List<Map<String, Object>> order_list = adminOrderService.order_list(cri, period, start_date, end_date, payment_method, ord_status);
		
		model.addAttribute("order_list", order_list);
		
		int totalcount = adminOrderService.getTotalcount(cri, period, start_date, end_date, payment_method, ord_status);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(totalcount);
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	// 주문상태 변경
	@PostMapping("/order_status_change")
	public ResponseEntity<String> order_status_change(Integer ord_code, String ord_status) throws Exception {
		ResponseEntity<String> entity = null;
		
		adminOrderService.order_status_change(ord_code, ord_status);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 주문정보 상세보기
	@GetMapping("/orderdetail_info")
	public void orderdetail_info(Integer ord_code, Model model) throws Exception {
		
		// 1)주문상세내역
		List<Map<String, Object>> orderdetail_info = adminOrderService.orderdetail_info(ord_code);
		model.addAttribute("order_product_info", orderdetail_info);
		
		// 2)주문결제내역
		PaymentDTO payment_info = adminPaymentService.payment_info(ord_code);
		model.addAttribute("payment_info", payment_info);
		
		
		// 4)배송지정보
		OrderDTO order_info = adminOrderService.order_info(ord_code);
		model.addAttribute("order_info", order_info);
		
		// 3)주문자정보(회원ID를 사용)
		String mbsp_id = order_info.getMbsp_id();
		MemberDTO memberDTO = memberService.login(mbsp_id);
		model.addAttribute("member_info", memberDTO);
	
		
	}
	
	// 주문상품이미지 보여주기
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	
	// 주문내역 개별상품삭제
	@PostMapping("/order_product_del")
	public ResponseEntity<String> order_product_del(Integer ord_code, Integer pro_num, int unitprice) throws Exception {
		ResponseEntity<String> entity = null;
		
		//개별상품삭제작업
		adminOrderService.order_product_del(ord_code, pro_num, unitprice);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 관리자가 주문메세지 저장(수정)
	@PostMapping("/admin_ord_message")
	public ResponseEntity<String> admin_ord_message(Integer ord_code, String ord_message) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		// 주문메세지 수정.
		adminOrderService.admin_ord_message(ord_code, ord_message);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 배송지정보 수정.
	@PostMapping("/order_info_edit")
	public ResponseEntity<String> order_info_edit(OrderDTO dto) throws Exception {
		
		log.info("배송지정보: "  + dto);
		
		ResponseEntity<String> entity = null;
		
		//배송지정보 수정작업.
		adminOrderService.order_info_edit(dto);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
	
	
}
