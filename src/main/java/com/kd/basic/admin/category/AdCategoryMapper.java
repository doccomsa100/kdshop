package com.kd.basic.admin.category;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.CategoryDTO;

public interface AdCategoryMapper {

	// 1차카테고리 목록
	List<CategoryDTO> getFirstCategoryList();

	// 1차카테고리를 참조하는 2차카테고리 목록
	List<CategoryDTO> getSecondCategoryList(Integer firstCategoryCode);
	
	// 1차카테고리 정렬
	void categorySort(@Param("cate_code") Integer cate_code, @Param("order") Integer order );
	
	// 1차카테고리 등록
	void insertFirstCategory(String cate_name);
	
	// 1차카테고리 수정
	void editFirstCategory(CategoryDTO dto);
	
	// 2차카테고리 등록
	void addSecondCategory(CategoryDTO dto);
	
	// 2차카테고리 수정
	void editSecondCategory(CategoryDTO dto);
	
	// 1차, 2차카테고리 삭제
	void deleteCategory(Integer cate_code);
	
}
