package com.common.aop;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.collection.CLog;
import com.common.collection.CommonVO;
import com.common.enumtype.BizExceptionCode;
import com.common.exception.BizException;

@Component
@Aspect
public class CommonAspect {
	
	@Autowired
	CLog log;
	
	@Around("execution(* com.app.service..*.*(..))")
	public CommonVO aroundMethod(ProceedingJoinPoint pjp) {
		Signature sig = pjp.getSignature();
		
		long startTime = System.currentTimeMillis();
		long endTime   = 0;
		CommonVO result  = null;
		
		String REPL_CD  = "000000";
		String REPL_MSG = "정상";
		
		log.i("========================= 서비스 로직 START =============================");
		log.i("호출명 : " + sig.toString());
		log.i("메소드 파라미터: " + Arrays.toString(pjp.getArgs()));
		
		try {
			result = (CommonVO) pjp.proceed();
			
		} catch (BizException biz) {
			/* 1. Business Logic Exception */
			result   = new CommonVO();
			REPL_CD  = biz.getErrCode();
			REPL_MSG = biz.getErrMsg();
			log.e(biz.getMessage());
			
		} catch (Exception e) {
			/* 2. System Exception */
			result   = new CommonVO();
			REPL_CD   = "000001";
			REPL_MSG  = BizExceptionCode.search(REPL_CD).getErrMsg();
			log.e(e.getMessage());
			
		} catch (Throwable t) {
			/* 3. TOP Level 'ERROR' */
			result   = new CommonVO();
			REPL_CD  = "999999";
			REPL_MSG = "[System Failure Unknown Error]";
			t.printStackTrace();
			log.e(t.getMessage());
		} finally {
			endTime = System.currentTimeMillis();
			result.put("REPL_CD", REPL_CD);
			result.put("REPL_MSG", REPL_MSG);
		}

		log.i("서비스 메소드 수행 시간    : " + ( endTime - startTime) + "/ms");
		log.i("서비스 메소드 수행 결과값 : " + result.toJson());
	    log.i("========================= 서비스 로직 END =============================");
	    return result;
	}
}
