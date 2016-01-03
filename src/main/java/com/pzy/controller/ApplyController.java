package com.pzy.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.entity.Apply;
import com.pzy.service.ApplyService;
import com.pzy.service.ClubService;
/***
 * @author panchaoyang
 *qq:263608237
 */
@Controller
@RequestMapping("/admin/apply")
public class ApplyController {
	@Autowired
	private ApplyService applyService;
	@Autowired
	private ClubService clubService;
	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	            ServletRequestDataBinder binder) throws Exception {   
	      binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	} 
	@RequestMapping("index")
	public String index() {
		return "admin/apply/index";
	}
	@RequestMapping("create")
	public String create(Model mode) {
		mode.addAttribute("clubs", clubService.findAll());
		return "admin/apply/create";
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, String applyname
			) throws ParseException {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<Apply> applys = applyService.findAll(pageNumber, pageSize, applyname);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", applys.getContent());
		map.put("iTotalRecords", applys.getTotalElements());
		map.put("iTotalDisplayRecords", applys.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Apply apply,Model model) {
		apply.setCreateDate(new Date());
		apply.setState("未审批");
		applyService.save(apply);
		model.addAttribute("tip", "登记成功");
		return "admin/apply/create";
	}
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(Apply apply) {
		applyService.save(apply);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		map.put("msg", "保存成功");
		return map;
	}
	/***
	 * 审批通过
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable Long id) {
		Apply apply=applyService.find(id);
		apply.setState("审批通过");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			applyService.save(apply);
			map.put("state", "success");
			map.put("msg", "审批成功");
		} catch (Exception e) {
			map.put("state", "error");
			map.put("msg", "审核失败，外键约束");
		}
        return map;
	}

	@RequestMapping(value = "/get/{id}")
	@ResponseBody
	public Map<String, Object> get(@PathVariable Long id ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("object", applyService.find(id));
		map.put("state", "success");
		map.put("msg", "成功");
		return map;
	}
}
