package com.kd.basic.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kd.basic.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdCategoryService {

	private final AdCategoryMapper adCategoryMapper;
	
	// 1차 카테고리
	public List<CategoryDTO> getFirstCategoryList() {
		return adCategoryMapper.getFirstCategoryList();
	}
	
	// 2차 카테고리
	public List<CategoryDTO> getSecondCategoryList(Integer firstCategoryCode) {
		return adCategoryMapper.getSecondCategoryList(firstCategoryCode);
	}
	
	@Transactional
	public void categorySort(List<Integer> orderArr) {
		for(int i=0; i<orderArr.size(); i++) {
			Integer cate_code = orderArr.get(i);
			Integer order = (i + 1);
			adCategoryMapper.categorySort(cate_code, order);
		}
	}
	
	public void insertFirstCategory(String cate_name) {
		adCategoryMapper.insertFirstCategory(cate_name);
	}
	
	public void editFirstCategory(CategoryDTO dto) {
		adCategoryMapper.editFirstCategory(dto);
	}
	
	public void addSecondCategory(CategoryDTO dto) {
		adCategoryMapper.addSecondCategory(dto);
	}
	
	public void editSecondCategory(CategoryDTO dto) {
		adCategoryMapper.editSecondCategory(dto);
	}
	
	public void deleteCategory(Integer cate_code) {
		adCategoryMapper.deleteCategory(cate_code);
	}
}
