package com.pzy.controller;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pzy.entity.Order;
import com.pzy.entity.PayOrder;
import com.pzy.entity.User;
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
	@Autowired
	private OrderService orderService;
	@RequestMapping("index")
	public String index(Model model) throws IOException {
		return "admin/ordersubmit/index";
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(PayOrder payOrder,HttpSession httpSession,Model model) {
		model.addAttribute("state", "success");
		model.addAttribute("tip", "提交成功");
		
		Order order=orderService.find(payOrder.getOid());
		if(order==null){
			payOrder.setState("商家未确认");
			model.addAttribute("state", "success");
			model.addAttribute("tip", "订单提交成功，等待工作人员录入");
		}else{
			payOrder.setOrder(order);
			payOrder.setState("已确认订单");
		}
		List<PayOrder> oldpayOrders=payOrderService.findByOid(payOrder.getOid());
		if(oldpayOrders.size()!=0){
			model.addAttribute("state", "success");
			model.addAttribute("tip", "订单已经提交，请勿重复提交");
			return "admin/ordersubmit/index"; 
		}
		User user=(User)httpSession.getAttribute("adminuser");
		
		payOrder.setUser(user);
		payOrder.setSubmitDate(new Date());
		payOrderService.save(payOrder);
		
		return "admin/ordersubmit/index";
	}
}
