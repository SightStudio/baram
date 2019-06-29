package com.app.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.service.user.UserServiceIF;
import com.common.collection.CommonVO;
import com.common.controller.BaseController;

@Controller
public class UserController extends BaseController {
	
	@Autowired
	UserServiceIF userService;
	
	@PostMapping("user/signin")
	public ModelAndView home(@ModelAttribute("param") CommonVO param) {
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("api", param);
		
		return mav;
	}
	
	@GetMapping("user/login/{id}&{pw}")
	public ModelAndView homeRest(@ModelAttribute("param") CommonVO param) {
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("api", param);
		
		return mav;
	}
}