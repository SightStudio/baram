package com.app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.collection.CommonVO;
import com.common.dao.CommonDaoIF;
import com.common.exception.BizException;
import com.common.service.BaseService;
import com.common.util.JwtMo;

@Service
public class UserServiceImpl extends BaseService 
							 implements UserServiceIF {
	
	@Autowired
	CommonDaoIF dao;
	
	/**
	 * <pre>
	 *     유저 회원 가입 메서드  
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 6. 30.
	 */
	@Override
	public CommonVO signup(CommonVO param) throws Exception {
		
		// [1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		
		// [2] 회원 가입
		dao.insert("com.app.mapper.user.signup", param);
		
		return result;
	}
	
	/**
	 * <pre>
	 *     유저 로그인 메서드  
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 7. 1.
	 */
	@Override
	public CommonVO login(CommonVO param) throws Exception {
		
		// [1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		
		// [2] 유저 검색
		CommonVO userInfo = dao.select("com.app.mapper.user.selectUser", param);
		
		// [3] 아이디 검증
		if( userInfo == null )
			throw new BizException("000002", "[로그인 오류] 아이디와 비밀번호를 다시확인해주세요");
		
		result.put("USER_JWT" , JwtMo.encryptJWT(userInfo));
		result.put("USER_INFO", userInfo);
		return result;
	}
}
