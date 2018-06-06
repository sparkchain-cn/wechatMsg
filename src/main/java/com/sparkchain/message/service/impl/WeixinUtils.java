package com.sparkchain.message.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.sparkchain.message.common.PropertyConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;
import com.spartchain.message.util.HttpRequestUtil;

@Service
public class WeixinUtils {

	String appid = PropertyConstants.getWeixinAppid();
	String appsecret = PropertyConstants.getWeixinAppSecret();
	String redictUri = PropertyConstants.getWeixinRedictUri();

	public String getOpenIdUrl(String username) throws ClientProtocolException, IOException {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

		url = url.replace("APPID", appid);
		if(StringUtils.isEmpty(username)){
			username = "1";
		}
		url = url.replace("STATE", "1");
		url = url.replace("REDIRECT_URI", URLEncoder.encode(redictUri, "utf-8"));
		System.out.println("----------微信取用户url="+url);
		return url;
	}

	public String getOpendId(String code) throws ParseException, IOException {

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=AppId&secret=AppSecret&code=CODE&grant_type=authorization_code";
		url = url.replace("AppId", appid).replace("AppSecret", appsecret).replace("CODE", code);

		Map<String, String> headersParams = new HashMap<>();
		String result = HttpRequestUtil.sendPost(url, headersParams);

		//Map<String, Object> map = JSON.parseObject(result, Map.class);
		Map<String, Object> map = JsonUtils.String2Map(result);

		String openid = "";
		if (map.get("openid") != null) {
			openid = map.get("openid").toString();
		}
		return openid;
	}
}