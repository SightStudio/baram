package com.app.service.user;

import com.common.collection.CommonVO;

public interface UserServiceIF {
	
	public CommonVO signup(CommonVO param) throws Exception;
	
	public CommonVO login(CommonVO param)  throws Exception;
}
