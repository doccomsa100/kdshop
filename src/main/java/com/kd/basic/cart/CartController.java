package com.kd.basic.cart;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.utils.FileUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/cart/*")
@Slf4j
@Controller
public class CartController {

	private final CartService cartService;
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	// 장바구니 추가 : 사용자의 상품이 존재안하면 추가, 존재하면 수량변경
	// ajax호출
	@PostMapping("/cart_add")
	public ResponseEntity<String> cart_add(CartDTO dto, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
		cartService.cart_add(dto);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 장바구니 목록  login_auth
	@GetMapping("/cart_list")
	public void cart_list(HttpSession session, Model model) throws Exception {
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		
		List<Map<String, Object>> cart_list = cartService.cart_list(mbsp_id);
		
		model.addAttribute("cart_list", cart_list);
		
		// 장바구니가 비워있을 때 아래 코드가 실행되면 null이어서 int로 결과를 받을수 가 없다.
		// 리턴타입을 int에서 Integer로 변환
		model.addAttribute("cart_total_price", cartService.getCartTotalPriceByUserId(mbsp_id));
	}
	
	// 상품이미지 출력
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	
	// 장바구니 비우기
	@GetMapping("/cart_empty")
	public String cart_empty(HttpSession session) throws Exception {
		
		// 장바구니 비우기
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		cartService.cart_empty(mbsp_id);
		
		return "redirect:/cart/cart_list";
	}
	
	// 선택삭제
	@PostMapping("/cart_sel_delete")
	public String cart_sel_delete(int[] check, HttpSession session) throws Exception {
		
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		// 선택삭제작업  체크된 체크박스의 상품코드만 전송되어온 1, 2
//		log.info("상품코드: " + Arrays.toString(check));
		cartService.cart_sel_delete(check, mbsp_id);
		
		return "redirect:/cart/cart_list";
	}
	
	// 수량변경.
	@GetMapping("/cart_quantity_change")
	public String cart_quantity_change(CartDTO dto,  HttpSession session) throws Exception {
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		
		dto.setMbsp_id(mbsp_id);
		
		log.info("장바구니수량변경: " + dto);
		
		cartService.cart_quantity_change(dto);
		
		return "redirect:/cart/cart_list";
	}
	
}
