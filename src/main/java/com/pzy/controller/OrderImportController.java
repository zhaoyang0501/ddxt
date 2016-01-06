package com.pzy.controller;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pzy.entity.Order;
import com.pzy.service.OrderService;
import com.pzy.service.PayOrderService;
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
	private PayOrderService payOrderService;
	@RequestMapping(value="index" , method = RequestMethod.GET) 
	public String index(Model model) throws IOException {
		return "admin/orderimport/index";
	}
	
	@RequestMapping(value="index" , method = RequestMethod.POST) 
    public String upload(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request, ModelMap model) throws IOException {  
        System.out.println("开始");  
        String filetype=StringUtils.getFilenameExtension( file.getOriginalFilename());
        String fileName = new Date().getTime()+"."+filetype;
        if(!"XLS".equals(fileName)&&!"xls".equals(filetype)){
        	 model.addAttribute("msg","文件格式不受支持");
        }
        else{
        	InputStream is=file.getInputStream();
        	List<Order> orders = ExcelUtil.readXls(is);
        	is.close();
     		for(Order order:orders){
     			order.setImportDate(new Date());
     		}
     		 orderService.save(orders);
     		 payOrderService.updateAll();
     		 model.addAttribute("msg","成功导入"+orders.size()+"条数据");
        }
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
