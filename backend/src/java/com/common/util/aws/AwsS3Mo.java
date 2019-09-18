package com.common.util.aws;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * AWS S3 유틸 파일 
 *  
 * @author Dong-Min Seool
 * @since  2019-09-11
 */
@Component
public class AwsS3Mo {
	
	// [0] Logger
	private static final Logger log = LoggerFactory.getLogger(AwsS3Mo.class);
	
	// [1] fileds
	private AwsConfig awsConfig;
    private AmazonS3  s3Client;
	private String    defaultBucketName;
	 
	@Value("#{common['FILE_TEMP_PATH']}")
	String fileTempPath;
	
    // [2] constructors
	@Autowired
	public AwsS3Mo(AwsConfig awsConfig) {
		this.awsConfig = awsConfig;
		s3Client = AmazonS3ClientBuilder.standard()
					 .withCredentials(this.awsConfig.getCredsProvider())
					 .withRegion(Regions.AP_NORTHEAST_2)
					 .build();
		this.defaultBucketName = awsConfig.getAwsProperty("S3_BUCKET_NAME");
	}
	
    // [3] public methods
	
	/**
	 * AWS S3 파일 업로드 함수
	 * @return 업로드 완료된 파일의 S3 URL
	 * @author Dong-Min Seool
	 * @since  2019-09-12
	 */
    public String upload(MultipartFile multipartFile, String s3DirName, String fileName) throws IOException {
    	File uploadFile = convert(multipartFile, fileName)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환 실패되었습니다."));

        return upload(uploadFile, s3DirName);
    }
    
    /**
	 * AWS S3 파일 복사 함수
	 * @return 복사가 완료된 파일의 S3 URL
	 * @author Dong-Min Seool
	 * @since  2019-09-12
	 */
    public String copy(String s3PathFrom, String s3PathTo) {
    	CopyObjectRequest copyObjRequest = new CopyObjectRequest(defaultBucketName, s3PathFrom, defaultBucketName, s3PathTo)
    											.withCannedAccessControlList(CannedAccessControlList.PublicRead);
		s3Client.copyObject(copyObjRequest);
    	return s3Client.getUrl(defaultBucketName, s3PathTo).getPath().substring(1);
    }
    
    /**
	 * AWS S3 파일 이동 함수 (복사 + 원본 삭제)
	 * @return 이동이 완료된 파일의 S3 URL
	 * @author Dong-Min Seool
	 * @since  2019-09-12
	 */
    public String moveTo(String s3PathFrom, String s3PathTo) {
    	this.copy(s3PathFrom, s3PathTo);
    	s3Client.deleteObject(new DeleteObjectRequest(defaultBucketName, s3PathFrom));
    	return s3Client.getUrl(defaultBucketName, s3PathTo).toString();
    }
    
    // [4] private methods
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }
    
    private String putS3(File uploadFile, String fileName) {
    	PutObjectRequest putReq = new PutObjectRequest(defaultBucketName, fileName, uploadFile)
    									.withCannedAcl(CannedAccessControlList.PublicRead);
    	s3Client.putObject(putReq);
        return s3Client.getUrl(defaultBucketName, fileName).getPath().substring(1);
    }
    
    private void removeNewFile(File targetFile) {
        if (targetFile.delete())
            log.info("파일이 삭제되었습니다.");
        else
        	log.info("파일이 삭제되지 못했습니다.");
    }
    
    /**
     * Multipart 객체를  File 객체로 변환하는 함수
     * 
     * @author Dong-Min Seool
     * @since  2019-09-11
     */
    private Optional<File> convert(MultipartFile file, String fileName) throws IOException {
    	String tempFile = fileTempPath+"/"+fileName;
    	File convertFile = new File(tempFile);
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}