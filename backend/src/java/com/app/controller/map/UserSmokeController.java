package com.app.controller.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.service.userSmoke.UserSmokeIF;
import com.common.collection.CommonVO;
import com.common.controller.BaseController;

@Controller
public class UserSmokeController extends BaseController {
	
	@Autowired
	UserSmokeIF userSmokeService;
	
	/**
	 * <pre>
	 *    사용자 지정 구역 가져오기  
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 8. 20.
	 */
	@GetMapping("map/non-official/{sw_lat}&{sw_lng}&{ne_lat}&{ne_lng}/{somkeType}")
	public ModelAndView getUserSmoke(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result = userSmokeService.getUserSmokeList(param);
		mav.addObject("response", result);
		return mav;
	}
	
	/**
	 * <pre>
	 *   사용자 등록 이미지 가져오기
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 6. 30.
	 */
	@GetMapping("map/non-official/image/{smoke_seq}")
	public ModelAndView getUserSmokeImage(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result = userSmokeService.getUserSmokeImageList(param);
		mav.addObject("response", result);
		return mav;
	}
	
	/**
	 * 최근  
	 */
	@GetMapping("user-smoke/recent/{page_start}-{page_end}")
	public ModelAndView getRecentUserSmoke(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result = userSmokeService.getRecentUserSmokeList(param);
		mav.addObject("response", result);
		return mav;
	}
	
	/**
	 * 최근  
	 */
	@GetMapping("user-smoke/recent/count/{day}")
	public ModelAndView getRecentUserSmokeCNT(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result = userSmokeService.getRecentUserSmokeCNT(param);
		mav.addObject("response", result);
		return mav;
	}
	
	/**
	 * <pre>
	 *   유저 회원가입 요청
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 6. 30.
	 */
	@PostMapping("user-smoke/register")
	public ModelAndView userSignin(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result = userSmokeService.registerArea(param);
		mav.addObject("response", result);
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @author Dong-Min Seool
	 * @since  2019-08-27
	 */
	@PostMapping("user-smoke/temp/img")
	public ModelAndView userSmokeImageTempSave(@ModelAttribute("request") CommonVO param, @RequestParam("img") MultipartFile file) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		param.put("uploadFile", file);
		
		CommonVO result = userSmokeService.saveTempImage(param);
		mav.addObject("response", result);

		param.remove("uploadFile");
		return mav;
	}
}