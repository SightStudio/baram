package com.common.util.aws;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.common.util.PropertyMo;

/**
 * AWS Credential 파일
 *  
 * @author Dong-Min Seool
 * @since  2019-09-11
 */
@Component
public class AwsConfig {

	// [0] log
	private static final Logger log = LoggerFactory.getLogger(AwsConfig.class);

	// [1] fields
	private PropertiesConfiguration props;
	private AWSCredentials 			creds;
	private AWSCredentialsProvider  credsProvider;

	// [2] Constructors
	public AwsConfig() {
		this.props         = PropertyMo.getPropertiesFromFile("properties/aws.properties");
		this.creds         = new BasicAWSCredentials(props.getString("ACCESS_KEY"), props.getString("SECRET_ACCESS_KEY"));
		this.credsProvider = new AWSStaticCredentialsProvider(this.creds); 
	}
	
    // [3] method
    
    /**
     * properties/aws.properties에 위치한 프로퍼티의 값을 가져오는 함수
     * 
     * @author Dong-Min Seool
     * @since  2019-09-11
     */
    public String getAwsProperty(String propertyName) {
    	return this.props.getString(propertyName);
    }
    
    // [4] getters && setters 
    public AWSCredentials getCreds() {
    	return this.creds;
    }
    
    public AWSCredentialsProvider getCredsProvider() {
    	return this.credsProvider;
    }
}
