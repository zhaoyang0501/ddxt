package com.pzy.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.entity.Order;
import com.pzy.entity.PayOrder;
import com.pzy.service.ClubService;
import com.pzy.service.OrderService;
import com.pzy.service.PayOrderService;
/***
 * @author panchaoyang
 *qq 263608237
 */
@Controller
@RequestMapping("/admin/ordersubmit")
public class OrderSubmitController {
	@Autowired
	private PayOrderService payOrderService;
	@RequestMapping("index")
	public String index(Model model) throws IOException {
		return "admin/ordersubmit/index";
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> save(PayOrder payOrder) {
		payOrder.setState("已提交");
		payOrderService.save(payOrder);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		map.put("msg", "保存成功");
		return map;
	}
}
