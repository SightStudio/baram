package com.app.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.collection.CommonVO;
import com.common.dao.CommonDaoIF;
import com.common.exception.BizException;
import com.common.service.BaseService;

@Service
public class CommonServiceImpl extends BaseService 
							   implements CommonServiceIF {
	
	@Autowired
	CommonDaoIF dao;
	
	/**
	 * <pre>
	 *     Exception Handling 용 테스트 메서드 
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 6. 30.
	 */
	@Override
	public CommonVO exceptionTest(CommonVO param) throws Exception {
		
		// [1] 결과 코드 및 container 세팅
		CommonVO result = new CommonVO();
		
		// [2] 강제 Exception 발생 
		if("error".equals(param.getString("msg")))
			throw new BizException("900000", "[Test Exception]");
		
		return result;
	}
}
