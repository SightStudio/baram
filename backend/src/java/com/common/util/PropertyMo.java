package com.common.util;

import java.io.IOException;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.PropertiesBuilderParameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.convert.ListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.core.io.ClassPathResource; 
import org.springframework.core.io.Resource;

/**
 * [프로퍼티 파일 처리 관련 모듈]
 * 
 * 스프링 컨테이너가 관리하지 않는 pure java class 상에서 property 값을 사용하기 위함 
 * 
 * @author Dong-Min Seol
 * @since  2019.05.28
 */
public class PropertyMo {
	
	/**
	 * 프로퍼티 파일을 읽어서 설정값을 리턴하는 함수
	 * eg. PropertyMo.getPropertyFromFile("propertis/aws.properties")
	 * 
	 * @author Dong-Min Seool
	 * @since  2019-09-11
	 */
	public static PropertiesConfiguration getPropertiesFromFile(String propertyPath) {
		
		PropertiesConfiguration result = null;
		
		//[1] 프로터피 파일 가져오기
		Resource prop = new ClassPathResource(propertyPath);
		ListDelimiterHandler delimiter = new DefaultListDelimiterHandler(',');

		// [2] property 가져올때 설정 추가 및 빌더 생성
		PropertiesBuilderParameters propertyParameters = new Parameters().properties();
		propertyParameters.setThrowExceptionOnMissing(true);
		propertyParameters.setListDelimiterHandler(delimiter);
		
		try {
			propertyParameters.setFile(prop.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileBasedConfigurationBuilder<PropertiesConfiguration> builder 
		= new FileBasedConfigurationBuilder<PropertiesConfiguration>(PropertiesConfiguration.class);
		
		builder.configure(propertyParameters);

		// [3] config 설정 저장
		try {
			result = builder.getConfiguration();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}

