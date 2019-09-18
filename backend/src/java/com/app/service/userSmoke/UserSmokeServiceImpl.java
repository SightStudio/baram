package com.app.service.userSmoke;

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
public class UserSmokeServiceImpl extends    BaseService 
							      implements UserSmokeIF {
	@Autowired
	CommonDaoIF dao;

	@Value("#{common['FILE_PATH']}")
	String filePath;
	
	@Autowired
	AwsS3Mo awsS3;
	
	/**
	 * <pre>
	 *   사용자 지정 구역 정보를 가져오는 함수 
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 8. 20.
	 */
	@Override
	public CommonVO getUserSmokeList(CommonVO param) throws Exception {

		// [1] 결과 container 세팅
		CommonVO result = new CommonVO();

		// [2] 실행 결과 저장
		List<CommonVO> smokeList = dao.selectList("com.app.mapper.userSmoke.getUserSmokeList", param);
		result.put("smokeList", smokeList);
		
		return result;
	}
	
	/**
	 * <pre>
	 *   사용자 지정 구역 첨부파일을 가져오는 함수
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 8. 20.
	 */
	@Override
	public CommonVO getUserSmokeImageList(CommonVO param) throws Exception {
		
		// [1] 결과 container 세팅
		CommonVO result = new CommonVO();

		// [2] 실행 결과 저장
		List<CommonVO> smokeList = dao.selectList("com.app.mapper.userSmoke.getUserSmokeImageList", param);
		result.put("imgList", smokeList);
		
		return result;
	}
	
	/**
	 * 최근 등록된 사용자 금연/ 흡연구역 개수 가져오기
	 * @author Dong-Min Seool
	 * @since  2019-08-29
	 */
	@Override
	public CommonVO getRecentUserSmokeCNT(CommonVO param) throws Exception {
		
		// [1] 결과 container 세팅
		CommonVO result = new CommonVO();
		
		// [2] 실행 결과 저장
		param.put("day", param.getInt("day"));
		
		CommonVO cnt = dao.select("com.app.mapper.userSmoke.getRecentSmokeCNT", param);
		result.put("cnt", cnt);
		return result;
	}
	
	/**
	 * 최근 등록된 사용자 금연/ 흡연구역 가져오기
	 * @author Dong-Min Seool
	 * @since  2019-08-29
	 */
	@Override
	public CommonVO getRecentUserSmokeList(CommonVO param) throws Exception {
		
		// [1] 결과 container 세팅
		CommonVO result = new CommonVO();

		// [2] 실행 결과 저장
		param.put("page_start", param.getInt("page_start"));
		param.put("page_end"  , param.getInt("page_end"));

		List<CommonVO> smokeList = dao.selectList("com.app.mapper.userSmoke.getRecentSmokeList", param);
		result.put("recentSmokeList", smokeList);
		
		return result;
	}
	
	/**
	 * <pre>
	 *   구역을 등록하는 서비스
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 8. 16.
	 */
	@Override
	@Transactional(rollbackFor={Exception.class})
	public CommonVO registerArea(CommonVO param) throws Exception {

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
		dao.insert("com.app.mapper.userSmoke.registerArea", param);
		
		// [3] 등록할 이미지 가져오기
		String[] imgTempURLArr = param.getString("imgListStr").split(",");

		// [4] 임시 저장했던 파일 해당 폴더로 저장
		String[] imgOutputArr = 
				Stream.of(imgTempURLArr)
				      .map(imgTempPath -> {
				    	 String [] temp = imgTempPath.split("/");
			    	     String imgTempFileName = temp[temp.length-1];
			    	     String destinationPath = String.format("static/userSmoke/%s/%s", param.getString("SMOKE_SEQ"), imgTempFileName);
				    	 return awsS3.moveTo(imgTempPath, destinationPath);  
				      })
				      .toArray(String[]::new);

		param.put("imgList", Arrays.asList(imgOutputArr));
		
		// [5] 사용자 입력 이미지 등록
		dao.insert("com.app.mapper.userSmoke.registerAreaImage", param);

		return result;
	}
	
	/**
	 * <pre>
	 *   임시로 이미지를 저장하는 서비스
	 * </pre>
	 * @author Dong-Min-Seol
	 * @since  2019. 8. 15.
	 */
	@Override
	public CommonVO saveTempImage(CommonVO param) throws Exception {
		
		//[1] 결과 컨테이너 세팅
		CommonVO result = new CommonVO();
		
		// [2] 파일 가져오기
		MultipartFile fileInput  = (MultipartFile) param.get("uploadFile");
		String fileName = DateMo.getYYYYMMDD("_") + RandomMo.getRandomString(20)+ "_" + fileInput.getOriginalFilename();
		String folder   = filePath + "/temp/gmap/";
		
		File folderObj  = new File(folder);

		// [3] 폴더 미존재시 폴더 생성
		if( !folderObj.exists() ) folderObj.mkdirs();
		
		String s3UploadedURL = awsS3.upload(fileInput, "static/temp/gmap", fileName);
		result.put("fileName", s3UploadedURL);
		return result;
	}
}















