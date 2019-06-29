package com.app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.collection.CommonVO;
import com.common.dao.CommonDaoIF;
import com.common.enumtype.BizExceptionCode;
import com.common.exception.BizException;
import com.common.service.BaseService;

@Service
public class UserServiceImpl extends BaseService implements UserServiceIF {
	
	@Autowired
	CommonDaoIF dao;
	
	@Override
	public CommonVO signup(CommonVO param) {
		
		
		
		return null;
	}

	@Override
	public CommonVO login(CommonVO param) {
		
		// [1] 결과 코드 및 container 세팅
		String REPL_CD  = "000000";
		String REPL_MSG = "정상";
		CommonVO result = new CommonVO();
		
		try {
			
			// [1] id로 유저 검색
			CommonVO userInfo = dao.select("com.app.mapper.user.selectUser", param);
			
			// [2] 아이디 검증
			if( userInfo.getString("id").isEmpty()) {
				throw new BizException("000002", "해당 아이디가 존재하지 않습니다.");
			}
			
			// [2] 비밀번호 검증			
			if( !userInfo.getString("pw").equals(param.getString("pw"))) {
				throw new BizException("000003", "비밀번호가 일치하지 않습니다.");
			}
			
		} catch (BizException biz) {
			REPL_CD  = biz.getErrCode();
			REPL_MSG = biz.getErrMsg();
			biz.printStackTrace();
			log.e(biz.getMessage());
		} catch (Exception e) {
			REPL_CD   = "000001";
			REPL_MSG  = BizExceptionCode.search(REPL_CD).getErrMsg();
			e.printStackTrace();
			log.e(e.getMessage());
		} finally {
			result.put("REPL_CD", REPL_CD);
			result.put("REPL_MSG", REPL_MSG);
		}
		return result;
	}

	@Override
	public CommonVO logout(CommonVO param) {
		return null;
	}
}
