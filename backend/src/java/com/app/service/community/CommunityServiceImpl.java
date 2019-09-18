package com.app.service.community;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import com.common.collection.CommonVO;
import com.common.dao.CommonDaoIF;
import com.common.service.BaseService;
import com.common.util.DateMo;
import com.common.util.RandomMo;
import com.common.util.aws.AwsS3Mo;

@Service
public class CommunityServiceImpl extends    BaseService 
							      implements CommunityIF {
	
	@Autowired
	CommonDaoIF dao;

	@Value("#{common['FILE_PATH']}")
	String filePath;
	
	@Autowired
	AwsS3Mo awsS3;
	
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
		
		// [rollback] 롤백시 업로드한 이미지 제거
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCompletion(int status) {
				if (status == STATUS_ROLLED_BACK) {
					log.e("비정상 적인 입력입니다. 롤백 처리되었습니다.");
	            }
			};
		});
		
		// [1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		
		// [2] 사용자 구역 입력 정보 저장
		dao.insert("com.app.mapper.community.registerBBS", param);
		
		// [3] 등록할 이미지 가져오기
		if(!param.getString("imgListStr").isBlank()) 
		{
			String[] imgTempURLArr = param.getString("imgListStr").split(",");
			String[] imgOutputArr = 
					Stream.of(imgTempURLArr)
					      .map(imgTempPath -> {
					    	 String [] temp = imgTempPath.split("/");
				    	     String imgTempFileName = temp[temp.length-1];
				    	     String destinationPath = String.format("static/userSmoke/%s/%s", param.getString("bbs_seq"), imgTempFileName);
					    	 return awsS3.moveTo(imgTempPath, destinationPath);  
					      })
					      .toArray(String[]::new);
			
			param.put("imgNameList", Arrays.asList(imgTempURLArr));
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
		String fileName = DateMo.getYYYYMMDD("_") + RandomMo.getRandomString(20)+ "_" + fileInput.getOriginalFilename();
		String folder   = filePath + "/temp/bbs/";
		
		File folderObj  = new File(folder);

		// [3] 폴더 미존재시 폴더 생성
		if( !folderObj.exists() ) folderObj.mkdirs();
		
		String s3UploadedURL = awsS3.upload(fileInput, "static/temp/gmap", fileName);
		result.put("fileName", s3UploadedURL);
		return result;
	}

}















