package com.pzy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pzy.entity.User;
import com.pzy.service.UserService;
/***
 * 后台首页，处理后台登录验证权限等操作
 * @author qq:263608237
 *
 */
@Controller
@RequestMapping("/admin")
public class IndexController {
	
	@Autowired
	private UserService userService;
	@RequestMapping("index")
	public String index() {
		return "admin/login";
	}
	@RequestMapping("login")
	public String login() {
		return "admin/login";
	}
	@RequestMapping("loginout")
	public String loginout(HttpSession httpSession) {
		httpSession.setAttribute("adminuser", null);
		return "admin/login";
	}
	@RequestMapping("gologin")
	public String gologin(HttpSession httpSession,String userName,String password,Model model) {
		
		User user=userService.login(userName, password);
    	if("admin".equals(userName)&&"123456".equals(password)){
    		User admin=new User();  
    		admin.setUsername("admin");
    		admin.setPassword("123456");
    		admin.setName("超级管理员");
    		httpSession.setAttribute("adminuser", admin);
    		return "admin/index";
    	}
		
    	else if(user!=null){
    		httpSession.setAttribute("adminuser", user);
    		return "admin/index";
    	}
    	else{
    		httpSession.setAttribute("adminuser", null);
    		model.addAttribute("tip","登陆失败 不存在此用户名或密码!");
    		return "admin/login";
    	}
    	
	}
}

