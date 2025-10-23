package com.kd.basic.order;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kd.basic.cart.CartDTO;
import com.kd.basic.cart.CartService;
import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.mail.EmailDTO;
import com.kd.basic.mail.EmailService;
import com.kd.basic.member.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/order/*")
@Slf4j
@Controller
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	private final MemberService memberService;
	private final EmailService emailService;
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	//장바구니, 바로구매 요청받았는 지 구분하기위하여, type값이 cart or buy
	@GetMapping("/order_info")
	public void order_info(HttpSession session, CartDTO dto, String type, Model model) throws Exception {
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
		// 바로구매. type이 buy 이면 장바구니테이블에 저장.
		if(type.equals("buy")) {
			cartService.cart_add(dto);
		}
		
		
		// 주문상품정보
		List<Map<String, Object>> orderDetails = cartService.cart_list(mbsp_id);
		model.addAttribute("orderDetails", orderDetails);
		
		// 주문상품이름, 수량 : 카카오페이 결제를 위한 정보. 
		String item_name = "";
		if(orderDetails.size() == 1) {
			item_name = (String) orderDetails.get(0).get("pro_name");
		}else {
			item_name = (String) orderDetails.get(0).get("pro_name") + " 외" +  (orderDetails.size() - 1) + "건";
		}
		
		model.addAttribute("item_name", item_name);
		model.addAttribute("quantity", orderDetails.size());
		
		
		// 주문총금액
		model.addAttribute("order_total_price", cartService.getCartTotalPriceByUserId(mbsp_id));
		
		// 주문자정보
		MemberDTO memberDTO = memberService.modify(mbsp_id);
		model.addAttribute("memberDTO", memberDTO);
		

		
	}
	
	// 상품이미지 출력
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	
	// 주문정보저장(무통장입금 결제). <form>태그 주소요청
	@PostMapping("/order_save")
	public String order_save(OrderDTO dto, String p_method, String account_transfer, String sender, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
		log.info("주문정보: " + dto);
		
		orderService.order_process(dto, p_method, account_transfer, sender);
		
		rttr.addAttribute("ord_code", dto.getOrd_code());
		rttr.addAttribute("ord_mail", dto.getOrd_mail());
		
		
		
		
		
		// /order/order_result?ord_code=10&ord_mail=newcomsa@nate.com
		return "redirect:/order/order_result";
	}
	
	// 주문처리결과 : 주문테이블, 주문상세테이블의 내용을 출력.
	int order_total_price = 0;  // 주문금액.
	@GetMapping("/order_result")
	public void order_result(Integer ord_code, String ord_mail, Model model) throws Exception {
		
		 List<Map<String, Object>> order_info = orderService.getOrderInfoByOrd_code(ord_code);
		 model.addAttribute("order_info", order_info);
		 
		 
		 order_info.forEach(o_info -> {
			 order_total_price += (int) o_info.get("dt_amount") * (int) o_info.get("dt_price");
		 });
		 
		 // 주문내역을 내용으로 메일보내기.
		 EmailDTO dto = new EmailDTO("KDMall", "KDMall", ord_mail, "주문내역", "주문내역");
		 emailService.sendMail("mail/orderConfirmation", dto, order_info, order_total_price);
		 
		 model.addAttribute("order_total_price", order_total_price);
		 
	}
	
}
