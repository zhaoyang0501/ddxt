package com.pzy.controller;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.entity.Order;
import com.pzy.service.ClubService;
import com.pzy.service.OrderService;
import com.pzy.util.ExcelUtil;
/***
 * @author panchaoyang
 *qq 263608237
 */
@Controller
@RequestMapping("/admin/orderimport")
public class OrderImportController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ClubService clubService;
	@RequestMapping("index")
	public String index(Model model) throws IOException {
		List<Order> orders = ExcelUtil.readXls("d:\\1.xls");
			for(Order order:orders){
				order.setImportDate(new Date());
		}
		orderService.save(orders);
		
		return "admin/orderimport/index";
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Order order) {
		orderService.save(order);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		map.put("msg", "保存成功");
		return map;
	}
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(Order order) {
		orderService.save(order);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		map.put("msg", "保存成功");
		return map;
	}
}
