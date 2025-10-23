package com.kd.basic.admin.statistics;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StatisticsService {

	private final StatisticsMapper statisticsMapper;
	
	public List<Map<String, Object>> getDailyStatistics(String date) {
		return statisticsMapper.getDailyStatistics(date);
	}
	
	public List<Map<String, Object>> getHourlyStatistics(String start_date, String end_date) {
		return statisticsMapper.getHourlyStatistics(start_date, end_date);
	}
	
	public List<Map<String, Object>> getWeeklyStatistics(String start_date, String end_date) {
		return statisticsMapper.getWeeklyStatistics(start_date, end_date);
	}
	
	public List<Map<String, Object>> getMonthlyStatistics(String year) {
		return statisticsMapper.getMonthlyStatistics(year);
	}
	
	public int getMonthlyCount() {
		return statisticsMapper.getMonthlyCount();
	}
	
	public int getMonthlySalesTotal() {
		return statisticsMapper.getMonthlySalesTotal();
	}
	
	public int getTotalProductCount() {
		return statisticsMapper.getTotalProductCount();
	}
	
	public int getSoldOutProductCount() {
		return statisticsMapper.getSoldOutProductCount();
	}
	
	public List<Map<String, Object>> getMonthlyOrderCategoryStats() {
		return  statisticsMapper.getMonthlyOrderCategoryStats();
	}
	
	public List<Map<String, Object>> getThisWeekOrderAmount() {
		return statisticsMapper.getThisWeekOrderAmount();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
