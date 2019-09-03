package com.app.controller.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.service.community.CommunityIF;
import com.common.collection.CommonVO;
import com.common.controller.BaseController;

@Controller
public class CommunityController extends BaseController {
	
	@Autowired
	CommunityIF communityService;
	
	/**
	 * 페이지 조회
	 * @author Dong-Min Seool
	 * @since  2019-08-26
	 */
	@GetMapping("community/list/{page_start}-{page_end}")
	public ModelAndView getBBSList(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result  = communityService.getBBSList(param);
		mav.addObject("response", result);
		return mav;
	}
	
	/**
	 * 게시글 조회
	 * @author Dong-Min Seool
	 * @since  2019-08-26
	 */
	@GetMapping("community/view/{bbs_seq}")
	public ModelAndView getBBS(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result  = communityService.getBBS(param);
		mav.addObject("response", result);
		return mav;
	}
	
	/**
	 * 게시글 이미지 조회
	 * @author Dong-Min Seool
	 * @since  2019-08-26
	 */
	@GetMapping("community/image/{bbs_seq}")
	public ModelAndView getBBSImage(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result  = communityService.getBBSImage(param);
		mav.addObject("response", result);
		return mav;
	}
	
	/**
	 * 게시판 글 작성
	 * @author Dong-Min Seool
	 * @since  2019-08-27
	 */
	@PostMapping("community/insert")
	public ModelAndView registerBBS(@ModelAttribute("request") CommonVO param) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		CommonVO result  = communityService.registerBBS(param);
		mav.addObject("response", result);
		return mav;
	}
	
	/**
	 * 게시판 업로드 이미지 임시저장
	 * @author Dong-Min Seool
	 * @since  2019-08-27
	 */
	@PostMapping("community/temp/img")
	public ModelAndView bbsTempFileSave(@ModelAttribute("request") CommonVO param, @RequestParam("img") MultipartFile file) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		param.put("uploadFile", file);
		
		CommonVO result = communityService.bbsTmpSave(param);
		mav.addObject("response", result);

		param.remove("uploadFile");
		return mav;
	}
	
	
	
	
}