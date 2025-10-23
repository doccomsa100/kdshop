package com.kd.basic.product;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.admin.category.AdCategoryService;
import com.kd.basic.common.dto.ProductDTO;
import com.kd.basic.common.utils.Criteria;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.common.utils.PageMaker;
import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/product/*")
@Controller
public class ProductController {

	private final ProductService productService;
	
	private final AdCategoryService adCategoryService;
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	@Value("${com.kd.upload.ckeditor.path}")
	private String uploadCKPath;
	
	// 2차카테고리 코드를 이용하여, 상품목록을 DB에서 가져와 타임리프 페이지에 출력하는 작업
	// 페이지번호 클리깃 아래주소가 요청된다.
	@GetMapping("/pro_list") // 검색기능 사용안함.
	public void pro_list(SearchCriteria cri, @ModelAttribute("cate_name") String cate_name, @ModelAttribute("cate_code") Integer cate_code, Model model) throws Exception {
		
//		log.info("2차카테고리코드: " + cate_code);
//		log.info("2차카테고리이름: " + cate_name);
		
		// 1차카테고리 목록
		model.addAttribute("firstCategoryList", adCategoryService.getFirstCategoryList());
		
		// 페이지에 보여줄 상품개수
		cri.setPerPageNum(6);
		
		// 2차카테고리 상품목록
		List<ProductDTO> produtctList = productService.getProductListBysecondCategory(cri, cate_code);
		model.addAttribute("produtctList", produtctList);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		// 페이징.
		pageMaker.setTotalCount(productService.getProductListCountBysecondCategory(cate_code));
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	
	// 상품상세
	@GetMapping("/pro_detail")
	public void pro_detail(Integer pro_num, @ModelAttribute("cate_name") String cate_name, Model model) throws Exception {
//		log.info("상품코드 " + pro_num);
//		log.info("카테고리: " + cate_name);
		
		model.addAttribute("productDTO", productService.pro_detail(pro_num));
		
	}
}
