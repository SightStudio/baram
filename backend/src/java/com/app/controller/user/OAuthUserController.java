package com.app.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.app.service.user.UserServiceIF;
import com.common.controller.BaseController;

@Controller
public class OAuthUserController extends BaseController {
	
	@Autowired
	UserServiceIF userService;
}