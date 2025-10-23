package com.kd.basic;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kd.basic.admin.category.AdCategoryService;
import com.kd.basic.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

	private final AdCategoryService adCategoryService;
	
	private final TestMapper testMapper;
	
	@ResponseBody
	@GetMapping("/getTime")
	public String getTime() {
		
		return testMapper.getTime();
	}
	
	// 기본페이지
	@GetMapping("/")
	public String home(Model model) {
		
		// 1차 카테고리작업.
		List<CategoryDTO> firstCategoryList = adCategoryService.getFirstCategoryList();
		model.addAttribute("firstCategoryList", firstCategoryList);
		
		return "index";
	}
	
	@GetMapping("/sub1")
	public String sub1() {
		
		return "sub1";
	}
	
	@GetMapping("/sub2")
	public String sub2() {
		
		return "sub2";
	}
}
