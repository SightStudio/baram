package com.app.service.officialSmoke;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.collection.CommonVO;
import com.common.config.NoLogging;
import com.common.dao.CommonDaoIF;
import com.common.service.BaseService;

@Service
public class OfficialSmokeServiceImpl extends    BaseService 
							          implements OfficialSmokeIF {
	
	@Autowired
	CommonDaoIF dao;

	/**
	 * <pre>
	 *   운영진 측에서 만든 금역 / 흡연구역 데이터 가져오기  
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 8. 18.
	 */
	@Override
	@NoLogging
	public CommonVO getOfficialSmokeList(CommonVO param) throws Exception {
		
		// [1] 결과 container 세팅
		CommonVO result   = new CommonVO();
		String   mapperID = "";
		
		// [2] 검색 조건에 맞는 Mapper 세팅
		switch(param.getString("somkeType"))
		{
		case "smoke"    :
			mapperID = "com.app.mapper.officialSmoke.getSmokeList";
			break;
			
		case "non-smoke":
			mapperID = "com.app.mapper.officialSmoke.getNonSmokeList";
			break;
		}

		// [3] 실행 결과 저장
		if(!mapperID.isBlank()) {
			List<CommonVO> smokeList = dao.selectList(mapperID, param);
			result.put("smokeList", smokeList);
		}
		return result;
	}
}















