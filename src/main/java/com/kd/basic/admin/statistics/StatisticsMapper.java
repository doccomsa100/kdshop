package com.kd.basic.admin.statistics;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StatisticsMapper {

	List<Map<String, Object>> getDailyStatistics(String date); // 2025-09
	
	List<Map<String, Object>> getHourlyStatistics(@Param("start_date") String start_date, @Param("end_date") String end_date);
	
	List<Map<String, Object>> getWeeklyStatistics(@Param("start_date") String start_date, @Param("end_date") String end_date);
	
	List<Map<String, Object>> getMonthlyStatistics(@Param("year") String year);
	
	// 이번달 거래건수
	int getMonthlyCount();
	
	// 이번달 총매출금액
	int getMonthlySalesTotal();
	
	// 총 상품수
	int getTotalProductCount();
	
	// 품절 상품수
	int getSoldOutProductCount();
	
	// 이번달 카테고리 통계
	List<Map<String, Object>> getMonthlyOrderCategoryStats();
	
	// 이번주 거래금액 통계
	List<Map<String, Object>> getThisWeekOrderAmount();
}
