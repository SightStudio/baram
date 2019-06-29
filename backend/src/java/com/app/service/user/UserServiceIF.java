package com.app.service.user;

import com.common.collection.CommonVO;

public interface UserServiceIF {
	
	public CommonVO signup(CommonVO param);
	
	public CommonVO login(CommonVO param);
	
	public CommonVO logout(CommonVO param);
}
