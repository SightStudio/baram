package com.app.service.community;

import com.common.collection.CommonVO;

public interface CommunityIF {
	
	// 게시글 목록 조회
	public CommonVO getBBSList(CommonVO param)  throws Exception;
	
	// 게시글 조회
	public CommonVO getBBS(CommonVO param)      throws Exception;
	
	// 게시글 이미지 조회
	public CommonVO getBBSImage(CommonVO param) throws Exception;

	// 게시글 등록
	public CommonVO registerBBS(CommonVO param) throws Exception;
	
	// 게시글 첨부 파일 임시 저장
	public CommonVO bbsTmpSave(CommonVO param)  throws Exception;

	// 게시글 수정
	public CommonVO updateBBS(CommonVO param)   throws Exception;

	// 게시글 삭제
	public CommonVO deleteBBS(CommonVO param)   throws Exception;
}
