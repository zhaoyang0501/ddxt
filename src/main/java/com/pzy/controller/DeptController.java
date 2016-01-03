package com.pzy.controller;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.entity.Dept;
import com.pzy.service.ClubService;
import com.pzy.service.DeptService;
/***
 * @author panchaoyang
 *qq:263608237
 */
@Controller
@RequestMapping("/admin/dept")
public class DeptController {
	@Autowired
	private DeptService deptService;
	@Autowired
	private ClubService clubService;
	@RequestMapping("index")
	public String index() {
		return "admin/dept/index";
	}
	@RequestMapping("create")
	public String create(Model mode) {
		mode.addAttribute("clubs", clubService.findAll());
		return "admin/dept/create";
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, String deptname
			) throws ParseException {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<Dept> depts = deptService.findAll(pageNumber, pageSize, deptname);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", depts.getContent());
		map.put("iTotalRecords", depts.getTotalElements());
		map.put("iTotalDisplayRecords", depts.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Dept dept,Model model) {
		dept.setCreateDate(new Date());
		deptService.save(dept);
		model.addAttribute("tip", "登记成功");
		return "admin/dept/create";
	}
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(Dept dept) {
		deptService.save(dept);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		map.put("msg", "保存成功");
		return map;
	}
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			deptService.delete(id);
			map.put("state", "success");
			map.put("msg", "删除成功");
		} catch (Exception e) {
			map.put("state", "error");
			map.put("msg", "删除失败，外键约束");
		}
        return map;
	}

	@RequestMapping(value = "/get/{id}")
	@ResponseBody
	public Map<String, Object> get(@PathVariable Long id ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("object", deptService.find(id));
		map.put("state", "success");
		map.put("msg", "成功");
		return map;
	}
}
