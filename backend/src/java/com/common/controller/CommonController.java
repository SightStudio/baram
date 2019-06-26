package com.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.common.collection.CommonLog;
import com.common.collection.CommonVO;

@Controller
public class CommonController {
	
	@Autowired
	public HttpServletRequest req;
	
	@Autowired
	public HttpSession session;
	
	@Autowired
	public CommonLog log;
	
	@ModelAttribute("defaultVO")
	public CommonVO initBoxs() {

		return (CommonVO) req.getAttribute("defaultVO");
	}
	
}
