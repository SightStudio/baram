package com.common.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.common.collection.CommonLog;

/**
 * Service Layer 공통 클래스
 * 
 * @author Dong-Min Seol
 * @since  2019.05.04
 */
public class CommonService{
	
	@Autowired
	protected CommonLog log;
}