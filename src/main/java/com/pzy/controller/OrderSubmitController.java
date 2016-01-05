package com.pzy.controller;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pzy.entity.PayOrder;
import com.pzy.entity.User;
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
	public String save(PayOrder payOrder,HttpSession httpSession,Model model) {
		User user=(User)httpSession.getAttribute("adminuser");
		payOrder.setState("已提交");
		payOrder.setUser(user);
		payOrder.setSubmitDate(new Date());
		payOrderService.save(payOrder);
		model.addAttribute("state", "success");
		model.addAttribute("tip", "提交成功");
		return "admin/ordersubmit/index";
	}
}
