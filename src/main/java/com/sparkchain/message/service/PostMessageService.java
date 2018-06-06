package com.sparkchain.message.service;

import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 接入应用的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface PostMessageService{
	
	//Boolean createToken(String tokenCode, String tokenName, Long amount);

	Map<String, Object> sendMessage(String message, String phone, String openId);
	
	Map<String, Object> getWallet(String openId);


}