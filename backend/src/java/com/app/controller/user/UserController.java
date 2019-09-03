package com.app.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	
	/**
	 * <pre>
	 *   
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 6. 30.
	 */
	@GetMapping("user/login/{id}&{pw}")
	public ModelAndView userLogin(@ModelAttribute("request") CommonVO param, ModelMap map) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		
		CommonVO result = userService.login(param);
		map.remove("request");
		mav.addObject("response", result);
		return mav;
	}
	
	/**
	 * <pre>
	 *    유저 회원가입 요청
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 6. 30.
	 */
	@PostMapping("user/signin")
	public ModelAndView userSignin(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		CommonVO result = userService.signup(param);
		mav.addObject("response", result);
		
		return mav;
	}
}