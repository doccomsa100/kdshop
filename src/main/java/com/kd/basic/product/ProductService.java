package com.kd.basic.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kd.basic.common.dto.ProductDTO;
import com.kd.basic.common.utils.Criteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductMapper productMapper;
	
	public List<ProductDTO> getProductListBysecondCategory(Criteria cri, Integer cate_code) {
		return productMapper.getProductListBysecondCategory(cri, cate_code);
	}
	
	public int getProductListCountBysecondCategory(Integer cate_code) {
		return productMapper.getProductListCountBysecondCategory(cate_code);
	}
	
	public ProductDTO pro_detail(Integer pro_num) {
		return productMapper.pro_detail(pro_num);
	}
	
	public int getProductReviewCountByPro_num(Integer pro_num) {
		return productMapper.getProductReviewCountByPro_num(pro_num);
	}
}
