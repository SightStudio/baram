package com.common.intercepter;

import java.util.Map; 
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.common.collection.CommonVO;


/**
 * Controller 호출 전 Handler를 통해 
 * 로그인 처리, VO 세팅 등 공통작업을 수행하는 인터셉터
 * 
 * @author Dong-Min Seol
 * @since  2019.05.03
 */
@Controller
public class FrontInterceptor extends HandlerInterceptorAdapter {

	/**
	 * <pre> 
	 * 컨트롤러 호출 전 아래 메소드를 실행
	 * </pre>
	 * 
	 * @author  Dong-Min Seol
	 * @since   2019.05.03  
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		CommonVO vo = new CommonVO();
		Map<String, String[]> requestMap = req.getParameterMap();
		
		// [1] request param CommonVO에 세팅
		requestMap.forEach( (k, v) -> {
			if(v.length == 1) {
				vo.put(k, v[0]);
			} else {
				vo.put(k, v);
			}
		});
		
		// [2] @PathVariables값 CommonVO에 세팅
		@SuppressWarnings("unchecked")
		Map<String, String> pathVariables = (Map<String, String>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		if (pathVariables != null) 
			pathVariables.forEach(vo::put);
		
		// [3] 필요정보 aBox객체에 저장함
		vo.put("requestAddr"  , getIP(req)); 			 // Client의 Host IP
		vo.put("requestURI"   , req.getRequestURI()); 	 // Client가 요청한 URI
		vo.put("requestMethod", req.getMethod());     	 // Client가 요청한 Method (GET, POST, PUT, DELETE)
		
		// [4] 세팅된 VO request 영역에 저장 후 Controller로 전송
		req.setAttribute("param", vo);
		
		return true;
	}
	
	
	/**
	 * <pre> 
	 *  사용자의 IP를 가져오는 메서드
	 *  웹서버를 경유하여 왔을 경우[리버스 프록시], 따로 웹서버에서 넘겨준 헤더에서 IP를 가져옴  
	 * </pre>
	 * 
	 * @author  Dong-Min Seol
	 * @since   2019.05.03  
	 */ 
	private String getIP (HttpServletRequest req) {
		
		return Optional.ofNullable(req.getHeader("X-Forwarded-For"))
					   .orElse(req.getRemoteAddr());
	}
}
