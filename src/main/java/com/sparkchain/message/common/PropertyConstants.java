package com.sparkchain.message.common;

import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertyConstants {
	private static Resource resource = new ClassPathResource("application.properties");
	private static Properties props = null;
	static{
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static Properties getConfigPropertis() {
		return props;
	}
	public static String getValue(String key) {
		if(null!=key&&!"".equals(key)&&props.containsKey(key)){
			return (String) props.get(key);
		}
		return null;
	}

	
	public static String getWeixinAppid(){
		return PropertyConstants.getValue("com.tencent.weixin.appid");
	}
	public static String getWeixinAppSecret(){
		return PropertyConstants.getValue("com.tencent.weixin.appsecret");
	}
	public static String getWeixinRedictUri(){
		return PropertyConstants.getValue("com.tencent.weixin.redictUri");
	}
	public static String getChainAccessAppid(){
		return PropertyConstants.getValue("com.sparkchain.chain.access.appid");
	}
	public static String getChainAccessSecret(){
		return PropertyConstants.getValue("com.sparkchain.chain.access.secret");
	}
	public static String getGiftChain(){
		return PropertyConstants.getValue("com.sparkchain.gift.chain");
	}
	public static String getGiftToken(){
		return PropertyConstants.getValue("com.sparkchain.gift.token");
	}
	public static String getChainAppAcount(){
		return PropertyConstants.getValue("com.sparkchain.chain.app.account");
	}
	public static String getChainAppSecret(){
		return PropertyConstants.getValue("com.sparkchain.chain.app.secret");
	}
	public static String getChainAppTokenCode() {
		return PropertyConstants.getValue("com.sparkchain.chain.app.token");
	}

	//重置tokenCode,appAcount和appSecret
	public static void setValue(String tokenCode,String appAccount,String appSecret){
		props.setProperty("com.sparkchain.chain.app.token",tokenCode);
		props.setProperty("com.sparkchain.chain.app.account",appAccount);
		props.setProperty("com.sparkchain.chain.app.secret",appSecret);
	}


}
