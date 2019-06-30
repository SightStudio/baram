package com.app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.collection.CommonVO;
import com.common.dao.CommonDaoIF;
import com.common.exception.BizException;
import com.common.service.BaseService;

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
	
	@Override
	public CommonVO login(CommonVO param) throws Exception {
		
		// [1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		
		// [2] id로 유저 검색
		CommonVO userInfo = dao.select("com.app.mapper.user.selectUser", param);
		
		// [3] 아이디 검증
		if( userInfo.getString("id").isEmpty()) {
			throw new BizException("000002", "해당 아이디가 존재하지 않습니다.");
		}
		
		// [4] 비밀번호 검증			
		if( !userInfo.getString("pw").equals(param.getString("pw"))) {
			throw new BizException("000003", "비밀번호가 일치하지 않습니다.");
		}
		
		return result;
	}
}
