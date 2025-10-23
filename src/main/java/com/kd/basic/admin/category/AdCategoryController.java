package com.kd.basic.admin.category;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kd.basic.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/admin/category/*")
@RequiredArgsConstructor
@Controller
public class AdCategoryController {

	private final AdCategoryService adCategoryService;
	
	@GetMapping("/categorymenu")
	public void categorymenu(Model model) throws Exception {
		List<CategoryDTO> firstCategoryList = adCategoryService.getFirstCategoryList();
		model.addAttribute("firstCategoryList", firstCategoryList);
	}
	
	// 1차 카테고리목록
	@GetMapping("/firstCategoryList") // 경로형태주소
	public ResponseEntity<List<CategoryDTO>> firstCategoryList() throws Exception {
		
		ResponseEntity<List<CategoryDTO>> entity = null;
		
		entity = new ResponseEntity<List<CategoryDTO>>(adCategoryService.getFirstCategoryList(), HttpStatus.OK);
		
		return entity;
		
	}
	
	
	// 2차 카테고리목록
	@GetMapping("/secondCategoryList/{firstCategoryCode}") // 경로형태주소
	public ResponseEntity<List<CategoryDTO>> 
			secondCategoryList(@PathVariable("firstCategoryCode") Integer firstCategoryCode) throws Exception {
		
		ResponseEntity<List<CategoryDTO>> entity = null;
		
		List<CategoryDTO> subCategoryList = adCategoryService.getSecondCategoryList(firstCategoryCode);
		
		entity = new ResponseEntity<List<CategoryDTO>>(subCategoryList, HttpStatus.OK);
		
		return entity;
		
	}
	
	// 1차, 2차 카테고리 정렬
	@PostMapping("/categorySort")
	public ResponseEntity<String> categorySort(@RequestParam("orderArr") List<Integer> orderArr) throws Exception {
		ResponseEntity<String> entity = null;
		
		log.info("1차카테고리 정렬: " + orderArr.toString());
		
		adCategoryService.categorySort(orderArr);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 1차 카테고리등록
	@PostMapping("/addFirstCategory")
	public ResponseEntity<String> addFirstCategory(String cate_name) throws Exception {
		ResponseEntity<String> entity = null;
		adCategoryService.insertFirstCategory(cate_name);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 1차 카테고리수정
	@PostMapping("/editFirstCategory")
	public ResponseEntity<String> editFirstCategory(CategoryDTO dto) throws Exception {
		ResponseEntity<String> entity = null;
		
		adCategoryService.editFirstCategory(dto);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 2차 카테고리 등록
	@PostMapping("/addSecondCategory")
	public ResponseEntity<String> addSecondCategory(CategoryDTO dto) throws Exception {
		ResponseEntity<String> entity = null;
		
		adCategoryService.addSecondCategory(dto);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 2차 카테고리 수정
	@PostMapping("/editSecondCategory")
	public ResponseEntity<String> editSecondCategory(CategoryDTO dto) throws Exception {
		ResponseEntity<String> entity = null;
		
		adCategoryService.editSecondCategory(dto);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 1차, 2차카테고리 삭제.
	@PostMapping("/deleteCategory")
	public ResponseEntity<String> deleteCategory(Integer cate_code) throws Exception {
		ResponseEntity<String> entity = null;
		
		adCategoryService.deleteCategory(cate_code);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
