package com.common.aop;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.collection.CommonLog;

@Component
@Aspect
public class CommonAspect {
	@Autowired
	CommonLog log;
	
	@Around("execution(* com.app.service..*.*(..))")
	public Object  aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		Signature sig = pjp.getSignature();
		
		long startTime = System.currentTimeMillis();
		
		log.i("[CommonAop] ========================= 서비스 호출 헤더 =============================");
		log.i("[CommonAop] 메소드 명 : " + sig.getName());
		log.i("[CommonAop] 메소드 파라미터: " + Arrays.toString(pjp.getArgs()));		
	    Object result  =  pjp.proceed();
	    long endTime = System.currentTimeMillis();
	    
	    log.i("[CommonAop] 메소드 수행 시간 : " + ( endTime - startTime) + "/ms");
	    log.i("[CommonAop] ========================= 서비스 호출 푸터 =============================");
	    return result;
	}
}
