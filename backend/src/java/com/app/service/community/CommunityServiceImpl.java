package com.app.service.community;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.common.collection.CommonVO;
import com.common.dao.CommonDaoIF;
import com.common.service.BaseService;
import com.common.util.DateMo;
import com.common.util.RandomMo;

@Service
public class CommunityServiceImpl extends    BaseService 
							      implements CommunityIF {
	
	@Autowired
	CommonDaoIF dao;

	@Value("#{common['FILE_PATH']}")
	String filePath;
	
	/**
	 * 게시글 목록 조회 로직
	 * @author Dong-Min Seool
	 * @since  2019-08-27
	 */
	@Override
	public CommonVO getBBSList(CommonVO param)  throws Exception {
		
		// [1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		param.put("page_start", param.getInt("page_start"));
		param.put("page_end"  , param.getInt("page_end"));
		
		// [2] 게시글 조회
		List<CommonVO> communityList = dao.selectList("com.app.mapper.community.getBBSList", param);
		
		result.put("communityList", communityList);
		return result;
	}
	
	@Override
	public CommonVO getBBS(CommonVO param) throws Exception {
		
		// [1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		
		// [2] 게시글 데이터 및 이미지 가져오기
		CommonVO bbs  = dao.select("com.app.mapper.community.getBBS", param);
		
		result.put("bbs", bbs);
		return result;
	}
	
	@Override
	public CommonVO getBBSImage(CommonVO param) throws Exception {

		// [1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		
		// [2] 이미지 리스트 가져오기
		List<CommonVO> bbsImgList = dao.selectList("com.app.mapper.community.getBBSImageList", param);
		result.put("bbsImgList", bbsImgList);
		
		return result;
	}
	

	@Override
	@Transactional(rollbackFor={Exception.class})
	public CommonVO registerBBS(CommonVO param) throws Exception {
		// [1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		
		// [2] 사용자 구역 입력 정보 저장
		dao.insert("com.app.mapper.community.registerBBS", param);
		
		// [3] 등록할 이미지 가져오기
		if(!param.getString("imgListStr").isBlank()) 
		{
			String[] imgNameArr = param.getString("imgListStr").split(",");
			String[] imgTempArr = Stream.of(imgNameArr)
									     .map(imgName -> String.format("%s/temp/bbs/%s", filePath, imgName))
									     .toArray(String[]::new);
			
			String[] imgOutputArr = Stream.of(imgNameArr)
					 				       .map(imgName -> String.format("/userSmoke/%s/%s", param.getString("bbs_seq"), imgName))
					 				       .toArray(String[]::new);
			
			// [4] 임시 저장했던 파일 해당 폴더로 저장
			File folder = new File(String.format("%s/userSmoke/%s", filePath, param.getString("bbs_seq")));
			folder.mkdirs();
			for(int i = 0 ; i < imgNameArr.length; i++) {
				File tempFile = new File(imgTempArr[i]);
				File output   = new File(filePath+imgOutputArr[i]);
				FileCopyUtils.copy(tempFile , output);
			}
			param.put("imgNameList", Arrays.asList(imgNameArr));
			param.put("imgList"    , Arrays.asList(imgOutputArr));
			
			// [5] 사용자 입력 이미지 등록
			dao.insert("com.app.mapper.community.registerBBSImage", param);
		}
		
		return result;
	}

	@Override
	public CommonVO updateBBS(CommonVO param)   throws Exception {
		return null;
	}

	@Override
	public CommonVO deleteBBS(CommonVO param)   throws Exception {
		return null;
	}

	@Override
	public CommonVO bbsTmpSave(CommonVO param) throws Exception  {
		
		//[1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		
		// [2] 파일 가져오기
		MultipartFile fileInput  = (MultipartFile) param.get("uploadFile");
		String fileName = DateMo.getYYYYMMDD("_") + RandomMo.getRandomString(10)+ "_" + fileInput.getOriginalFilename();
		String folder   = filePath + "/temp/bbs/";
		String path     = folder   + fileName;
		
		File folderObj  = new File(folder);
		File fileOutput = new File(path);
		
		// [3] 폴더 미존재시 폴더 생성
		if( !folderObj.exists() ) folderObj.mkdirs();

		FileCopyUtils.copy(fileInput.getBytes(), fileOutput);
		result.put("fileName", fileName);
		
		folderObj  = null;
		fileOutput = null;
		return result;
	}

}















