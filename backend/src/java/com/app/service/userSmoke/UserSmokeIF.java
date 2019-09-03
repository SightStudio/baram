package com.app.service.userSmoke;

import com.common.collection.CommonVO;

public interface UserSmokeIF {
	
	// 사용자 지정 구역 가져오기
	public CommonVO getUserSmokeList(CommonVO param) throws Exception;

	// 사용자 지정 구역 첨부 이미지 가져오기
	public CommonVO getUserSmokeImageList(CommonVO param) throws Exception;
	
	// 최근 등록된 사용자 지정 구역 목록 가져오기
	public CommonVO getRecentUserSmokeList(CommonVO param) throws Exception;
	
	// 최근 등록된 사용자 지정 구역 개수 가오기
	public CommonVO getRecentUserSmokeCNT(CommonVO param) throws Exception;
	
	// 사용자 지정 구역 등록
	public CommonVO registerArea(CommonVO param) throws Exception;
	
	// 사용자 지정 구역 첨부파일 임시저장
	public CommonVO saveTempImage(CommonVO param) throws Exception;
}
