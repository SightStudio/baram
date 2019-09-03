package com.app.controller.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.app.service.officialSmoke.OfficialSmokeIF;
import com.common.collection.CommonVO;
import com.common.controller.BaseController;

@Controller
public class OfficialSmokeController extends BaseController {
	
	@Autowired
	OfficialSmokeIF nonSmokeservice;
	
	@GetMapping("map/official/{sw_lat}&{sw_lng}&{ne_lat}&{ne_lng}/{somkeType}")
	public ModelAndView getOfficialSmoke(@ModelAttribute("request") CommonVO param) throws Exception 
	{
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result = nonSmokeservice.getOfficialSmokeList(param);
		mav.addObject("response", result);
		return mav;
	}
}