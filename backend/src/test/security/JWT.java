package security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.common.collection.CLog;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = { "classpath*:springmvc/root-context.xml", "classpath*:spring/context-datasource.xml",
					  "classpath*:spring/context-context.xml", "classpath*:spring/context-mybatis.xml"})
public class JWT {

	@Autowired
	CLog log;
	
	/**
	 * <pre>
	 *   JWT 생성 테스트 
	 *   다음 프로젝트에서는 스프링 시큐리티로..쓰자 꼭
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 6. 30.
	 */
	@Ignore
	@Test
	public void JWT_encryptionTest() throws Exception {
		List<HashMap<String, String>> authList = new ArrayList<>();
		
		String jwt = Jwts.builder()
						 .setIssuer("sight-spring-server")
						 .setSubject("sight-request-client")
						 .claim("scope", authList)
						 .claim("name", "설동민")
						 .setIssuedAt(Date.from(Instant.now()))
						 .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)))
						 .signWith(SignatureAlgorithm.HS256, "sight".getBytes())
						 .compact();
	
		
		// 암호화
		log_with_beautify(jwt);
		
		Claims claims = Jwts.parser().setSigningKey("secret".getBytes("UTF-8")).parseClaimsJws(jwt).getBody();
		
		// 복호화
		log_with_beautify(claims.toString());
	}
	
	
	
	private void log_with_beautify(String print) {
		log.i(
				"\n\n\n\n\n------------------------------ junit user logging start  --------------------------------\n\n\n\n\n" 
				
																  + print +
				
				"\n\n\n\n\n------------------------------ junit user logging finish --------------------------------\n\n\n\n\n"
		);
	}
}