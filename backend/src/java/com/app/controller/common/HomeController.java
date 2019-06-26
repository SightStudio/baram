package com.app.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.common.collection.CommonVO;
import com.common.controller.CommonController;

@Controller
public class HomeController extends CommonController {
	
	@GetMapping("index")
	public ModelAndView home(@ModelAttribute("defaultVO") CommonVO vo) {
		ModelAndView mav = new ModelAndView("home");
		
		mav.addObject("defaultVO", vo);
		
		return mav;
	}
}