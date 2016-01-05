package com.pzy.controller;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.entity.Order;
import com.pzy.entity.PayOrder;
import com.pzy.service.ClubService;
import com.pzy.service.OrderService;
import com.pzy.service.PayOrderService;
import com.pzy.util.StringUtil;
/***
 * @author panchaoyang
 *qq 263608237
 */
@Controller
@RequestMapping("/admin/orderstate")
public class OrderStateController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private PayOrderService payOrderService;
	@RequestMapping("index")
	public String index(Model model) {
		return "admin/orderstate/index";
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, String ordername
			) throws ParseException {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<PayOrder> orders = payOrderService.findAll(pageNumber, pageSize, ordername,"","");
		for(PayOrder payOrder:orders.getContent()){
			if(payOrder.getOrder()!=null&&payOrder.getOrder().getC4()!=null){
				payOrder.getOrder().setC4(StringUtil.getEncodeStr(payOrder.getOrder().getC4()));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", orders.getContent());
		map.put("iTotalRecords", orders.getTotalElements());
		map.put("iTotalDisplayRecords", orders.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
}