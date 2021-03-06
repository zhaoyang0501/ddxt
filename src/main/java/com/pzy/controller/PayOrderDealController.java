package com.pzy.controller;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.pzy.entity.PayOrder;
import com.pzy.entity.User;
import com.pzy.service.OrderService;
import com.pzy.service.PayOrderService;
/***
 * @author panchaoyang
 *qq 263608237
 */
@Controller
@RequestMapping("/admin/orderdeal")
public class PayOrderDealController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private PayOrderService payOrderService;

	@RequestMapping("index")
	public String index(Model model) {
		return "admin/orderdeal/index";
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String user,
			String id,
			String state
			) throws ParseException {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<PayOrder> payOrders = payOrderService.findAll(pageNumber, pageSize, user,id,state);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", payOrders.getContent());
		map.put("iTotalRecords", payOrders.getTotalElements());
		map.put("iTotalDisplayRecords", payOrders.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping(value = "/send/{id}")
	@ResponseBody
	public Map<String, Object> send(@PathVariable Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PayOrder bean=payOrderService.find(id);
			bean.setState("已发货");
			payOrderService.save(bean);
			map.put("state", "success");
			map.put("msg", "发货成功");
		} catch (Exception e) {
			map.put("state", "success");
			map.put("msg", "发货失败");
		}
        return map;
	}
	
	@RequestMapping(value = "/pay/{id}")
	@ResponseBody
	public Map<String, Object> pay(@PathVariable Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PayOrder bean=payOrderService.find(id);
			User user=bean.getUser();
			bean.setState("已付款");
			Double m=Double.valueOf(bean.getOrder().getC9());
			bean.setPay(m*user.getP1()-user.getP2());
			payOrderService.save(bean);
			map.put("state", "success");
			map.put("msg", "付款成功");
		} catch (Exception e) {
			map.put("state", "success");
			map.put("msg", "付款成功");
		}
        return map;
	}
	
	
	@RequestMapping(value = "/payall")
	@ResponseBody
	public Map<String, Object> payall( String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(id)){
			map.put("state", "error");
			map.put("msg", "没有选择订单");
			return map;
		}
		String[] ids=id.split(",");
		List<PayOrder> payOrders=new ArrayList<PayOrder>();
		try {
			for(int i=0;i<ids.length;i++){
				PayOrder bean=payOrderService.find(Long.valueOf(ids[i]));
				if(bean!=null)
					payOrders.add(bean);
			}
			
			for(PayOrder bean:payOrders){
				if("商家未确认".equals(bean.getState())){
					map.put("state", "error");
					map.put("msg", bean.getOid()+"订单商家未确认不可付款");
					return map;
				}
			}
			
			for(PayOrder bean:payOrders){
				User user=bean.getUser();
				bean.setState("已付款");
				Double m=Double.valueOf(bean.getOrder().getC9());
				bean.setPay((m/100)*user.getP1()-user.getP2());
				payOrderService.save(bean);
			}
			map.put("state", "success");
			map.put("msg", "付款成功");
		} catch (Exception e) {
			map.put("state", "error");
			map.put("msg", "付款失败"+e.getMessage());
		}
        return map;
	}
	
	@RequestMapping(value = "/sendall")
	@ResponseBody
	public Map<String, Object> sendall( String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(id)){
			map.put("state", "error");
			map.put("msg", "没有选择订单");
			return map;
		}
		String[] ids=id.split(",");
		List<PayOrder> payOrders=new ArrayList<PayOrder>();
		try {
			for(int i=0;i<ids.length;i++){
				PayOrder bean=payOrderService.find(Long.valueOf(ids[i]));
				if(bean!=null)
					payOrders.add(bean);
			}
			
			for(PayOrder bean:payOrders){
				if("商家未确认".equals(bean.getState())){
					map.put("state", "error");
					map.put("msg", bean.getOid()+"订单商家未确认不可付款");
					return map;
				}
			}
			
			for(PayOrder bean:payOrders){
				bean.setState("发货成功");
				payOrderService.save(bean);
			}
			map.put("state", "success");
			map.put("msg", "发货成功");
		} catch (Exception e) {
			map.put("state", "error");
			map.put("msg", "发货成功"+e.getMessage());
		}
        return map;
	}
	
	@RequestMapping(value = "/deleteall")
	@ResponseBody
	public Map<String, Object> deleteall( String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(id)){
			map.put("state", "error");
			map.put("msg", "没有选择订单");
			return map;
		}
		String[] ids=id.split(",");
		List<PayOrder> payOrders=new ArrayList<PayOrder>();
		try {
			for(int i=0;i<ids.length;i++){
				PayOrder bean=payOrderService.find(Long.valueOf(ids[i]));
				if(bean!=null)
					payOrders.add(bean);
			}
			
			for(PayOrder bean:payOrders){
				if(!"商家未确认".equals(bean.getState())){
					map.put("state", "error");
					map.put("msg","只能删除未确认的订单");
					return map;
				}
			}
			
			for(PayOrder bean:payOrders){
				payOrderService.delete(bean.getId());
			}
			map.put("state", "success");
			map.put("msg", "删除成功");
		} catch (Exception e) {
			map.put("state", "error");
			map.put("msg", "删除成功"+e.getMessage());
		}
        return map;
	}
}
