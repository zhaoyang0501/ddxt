package com.pzy.controller;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.entity.PayOrder;
import com.pzy.entity.User;
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
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, 
			String begin,
			String end,
			String state,
			HttpSession httpSession
			) throws Exception {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		User user=(User)httpSession.getAttribute("adminuser");
		Page<PayOrder> orders = payOrderService.findAllByUser(pageNumber, pageSize, user.getId(),begin,end,state);
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
	
	@RequestMapping(value = "/confirm")
	@ResponseBody
	public Map<String, Object> confirm(Long  id) {
		Map<String, Object> map = new HashMap<String, Object>();
		PayOrder bean=payOrderService.find(id);
		if(!"已付款".equals(bean.getState())){
			map.put("state", "error");
			map.put("msg", "只能确认已付款的订单");
			return map;
		}else{
			bean.setState("已确认");
			payOrderService.save(bean);
		}
		map.put("state", "success");
		map.put("msg", "保存成功");
		return map;
	}
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PayOrder bean=payOrderService.find(id);
			if(bean==null){
				map.put("state", "error");
				map.put("msg", "订单不存在或者已经被删除");
				return map;
			}
			if(!"商家未确认".equals(bean.getState())){
				map.put("state", "error");
				map.put("msg", "只能删除商家未确认的订单！");
				return map;
			}
			payOrderService.delete(id);
			map.put("state", "success");
			map.put("msg", "删除成功");
		} catch (Exception e) {
			map.put("state", "error");
			map.put("msg", "删除失败，外键约束");
		}
        return map;
	}
}
