package com.kd.basic.admin.statistics;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/admin/statist/*")
@RequiredArgsConstructor
@Controller
public class StatisticsController {

	private final StatisticsService statisticsService;
	
	// 주문통계
	@GetMapping("/static_sale_all")
	public void static_sale_all() throws Exception {
				
	}
	
	// 일자별
	@GetMapping("/daily")
	public ResponseEntity<List<Map<String, Object>>> getDailyStatistics(String date) throws Exception {
		
		/*
		ResponseEntity<List<Map<String, Object>>> entity = null;
		entity = new ResponseEntity<List<Map<String,Object>>>(statisticsService.getDailyStatistics(date), HttpStatus.OK);
		return entity;
		*/
		log.info("날짜:" + date);
//		int year = Integer.parseInt(date.substring(0, 4));
//		int month = Integer.parseInt(date.substring(4));
				
		return ResponseEntity.ok(statisticsService.getDailyStatistics(date));
	}
	
	// 시간별
	@GetMapping("/hourly")
	public ResponseEntity<List<Map<String, Object>>> getHourlyStatistics(String start_date, String end_date) throws Exception {
		
		log.info("날짜:" + start_date + "~" + end_date);
		
		return ResponseEntity.ok(statisticsService.getHourlyStatistics(start_date, end_date));
	}
	
	// 요일별
	@GetMapping("/weekly")
	public ResponseEntity<List<Map<String, Object>>> getWeeklyStatistics(String start_date, String end_date) throws Exception {
		
		return ResponseEntity.ok(statisticsService.getWeeklyStatistics(start_date, end_date));
	}
	
	
	// 월별
	@GetMapping("/monthly")
	public ResponseEntity<List<Map<String, Object>>> getMonthlyStatistics(String year) throws Exception {
		
		return ResponseEntity.ok(statisticsService.getMonthlyStatistics(year));
	}
	
	// 이번달 거래현황중 거래건수, 이번달 총매출금액, 이번달 카테고리 통계, 이번주 거래금액 통계.
	@GetMapping("/dashboard")
	public void dashboard(Model model) throws Exception {
		
		model.addAttribute("monthlyCount", statisticsService.getMonthlyCount());
		model.addAttribute("monthlySalesTotal", statisticsService.getMonthlySalesTotal());
		model.addAttribute("totalProductCount", statisticsService.getTotalProductCount());
		model.addAttribute("soldOutProductCount", statisticsService.getSoldOutProductCount());
				
		
		List<Map<String, Object>> monthlyOrderCategoryStats = statisticsService.getMonthlyOrderCategoryStats();

	    List<String> labels1 = monthlyOrderCategoryStats.stream()
	            .map((Map<String, Object> stat) -> (String) stat.get("primary_category_name"))
	            .collect(Collectors.toList());

	    List<Integer> data1 = monthlyOrderCategoryStats.stream()
	            .map((Map<String, Object> stat) -> {
	                Object value = stat.get("total_orders");
	                if (value instanceof Number) {
	                    return ((Number) value).intValue();
	                } else {
	                    return 0;
	                }
	            })
	            .collect(Collectors.toList());

	    model.addAttribute("labels1", labels1);
	    model.addAttribute("data1", data1);
	    
	    /*             */
	    
	    List<Map<String, Object>> thisWeekOrderAmount = statisticsService.getThisWeekOrderAmount();

	    List<String> labels2 = thisWeekOrderAmount.stream()
	            .map((Map<String, Object> stat) -> String.valueOf(stat.get("order_date")))
	            .collect(Collectors.toList());

	    List<Integer> data2 = thisWeekOrderAmount.stream()
	            .map((Map<String, Object> stat) -> {
	                Object value = stat.get("daily_total_price");
	                if (value instanceof Number) {
	                    return ((Number) value).intValue();
	                } else {
	                    return 0;
	                }
	            })
	            .collect(Collectors.toList());

	    model.addAttribute("labels2", labels2);
	    model.addAttribute("data2", data2);
		

	}
	
	
	
	
}
