package com.sparkchain.message.service.impl;

import java.util.*;

import com.sparkchain.message.common.Constants;
import com.sparkchain.message.common.PropertyConstants;
import com.spartchain.message.util.OtherUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparkchain.message.service.PostMessageService;
import com.spartchain.message.util.HttpRequestUtil;

/**
 * <pre>
 * 接入应用的业务实现
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("postMessageService")
public class PostMessageServiceImpl implements PostMessageService {

	//@Value("${com.sparkchain.chain.access.url:http://tapi3.sparkchain.cn}")
	//String accessUrl = "http://10.104.21.223:8070";
	String accessUrl = "http://tapi3.sparkchain.cn";

	@Value("${com.sparkchain.chain.access.defaultPassword:123456}")
	String defaultPassword;

	String chain = PropertyConstants.getGiftChain();


	protected final static Logger logger = LoggerFactory.getLogger(PostMessageServiceImpl.class);

	@Autowired
	RedisUtils redisService;
	@Autowired
	InterfaceUtils interfaceUtils;

	PostMessageServiceImpl() {
		super();

	}

	@Override
	public Map<String, Object> getWallet(String openId) {
		Map<String, Object> result = new HashMap<>();
		String key = "msg_hash_" + openId;
		String hash = redisService.get(key);
		if (StringUtils.isEmpty(hash)) {
			String account = getAccount(openId);
			if (StringUtils.isEmpty(account)) {
				String userAccount = createWallet(openId);
				//企业钱包往用户钱包转钱
				String appAccount = redisService.get(Constants.APP_WALLET_ACCOUNT);
				String appSecret = redisService.get(Constants.APP_WALLET_SECRET);
				interfaceUtils.chainAppCharge(appAccount,appSecret,userAccount,redisService.get(Constants.ACCOUNT_WALLET_PUSH_NUM));
			}
			result.put("isSendMessage", "false");
		} else {
			result.put("isSendMessage", "true");
			String account = getAccount(openId);
			result.put("account", account);
			String secret = interfaceUtils.getWalletSecret(account);
			result.put("secret", secret);
			String balance = getBalance(account, chain);
			result.put("balance", balance);
			result.put("hash", hash);
		}
		return result;
	}

	private String getAccount(String openId) {
		String url = accessUrl + "/v1/wallet/accounts";
		Map<String, String> params = new HashMap<>();
		params.put("accessToken",interfaceUtils.getAccessToken());
		params.put("userId", openId);
		try {
			Map<String, ?> map = accessBaas(url, params);
			if (map == null || map.get("data") == null) {
				return null;
			}
			Map<String, Object> data = (Map<String, Object>)map.get("data");
			List<Map<String, String>> list = (List<Map<String, String>>) data.get("accounts");
			for (Map<String, String> account : list) {
				if (chain.equals(account.get("chainCode"))) {
					return account.get("accountAddr");
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/*private String getSecret(String account) {
		String url = accessUrl + "/v1/account/secret";
		Map<String, String> params = new HashMap<>();
		params.put("chainCode", chain);
		params.put("account", account);
		try {
			Map<String, ?> map = accessBaas(url, params);
			if (map == null || map.get("data") == null) {
				return null;
			}
			Map<String, String> data = (Map<String, String>) map.get("data");
			return data.get("secret");
		} catch (Exception e) {
			return null;
		}
	}*/

	private String getBalance(String account, String tokenCode) {
		String url = accessUrl + "/v1/account/balance";
		Map<String, String> params = new HashMap<>();
		params.put("chainCode", chain);
		params.put("account", account);
		params.put("tokenCode", redisService.get(Constants.TOKEN_CODE));
		try {
			Map<String, ?> map = accessBaas(url, params);
			if (map == null || map.get("data") == null) {
				/*
				 * 如果为空，则2秒后再查一次，循环5次
				 * author:shiruojiang 2018-05-28
				 * */
				for(int i=0;i<5;i++) {
					Thread.sleep(2000);
					map = accessBaas(url, params);
					System.out.println("url="+url+",params="+params+",result="+map+",次数="+i);
					if(map.get("data")!=null) {
						Map<String, String> data = (Map<String, String>) map.get("data");
						return data.get("balance"); 
					}
				}
				return "暂无数据";
			}
			Map<String, String> data = (Map<String, String>) map.get("data");
			return data.get("balance");
		} catch (Exception e) {
			return "暂无数据";
		}
	}

	@Override
	public Map<String, Object> sendMessage(String message, String phone, String openId) {

		Map<String, Object> retMap = new HashMap<>();
		String key = "msg_hash_" + openId;
		String hash = redisService.get(key);
		if (StringUtils.isNotEmpty(hash)) {
			retMap.put("success", "false");
			retMap.put("message", "用户已经留过言了，不能重复留言。");
			return retMap;
		}
		//double giftAmount = getGiftAmount(message.trim());
		//文本上链接口
		String url = accessUrl + "/v1/text/record";
		Map<String, String> params = new HashMap<>();
		String userAccount = getAccount(openId);
		params.put("accessToken", interfaceUtils.getAccessToken());
		params.put("srcAccount",userAccount);
		params.put("payPassword", "yuelian@123");
		//params.put("srcPrivateKey", appSecret);
		params.put("chainCode", chain);
		params.put("tokenCode", redisService.get(Constants.TOKEN_CODE));
		params.put("destAccount", redisService.get(Constants.APP_WALLET_ACCOUNT));
		String memo = getMemo(message, phone);
		params.put("memo", memo);
		params.put("bizId", OtherUtils.generateUID(15));
		//params.put("amount", String.valueOf(giftAmount));
		//params.put("amount", OtherUtils.giftAmount(redisService.get(Constants.RED_PACKET_MIN),redisService.get(Constants.RED_PACKET_MAX)));
		try {
			Map<String, ?> map = accessBaas(url, params);
			if (map == null || map.get("data") == null) {
				return null;
			}
			Map<String, Object> data = (Map<String, Object>) map.get("data");
			hash = (String)data.get("hash");
			redisService.set(key, hash);
			Map<String, String> hashMap = new HashMap<>();
			hashMap.put("openId", openId);
			hashMap.put("memo", memo);
			hashMap.put("hash", hash);
			//hashMap.put("amount", String.valueOf(giftAmount));
			String amount = OtherUtils.giftAmount(redisService.get(Constants.RED_PACKET_MIN),redisService.get(Constants.RED_PACKET_MAX));
			hashMap.put("amount",amount);
			redisService.lpush("message_list", hashMap);
			//app钱包向用户转帐
			String appAccount = redisService.get(Constants.APP_WALLET_ACCOUNT);
			String appSecret = redisService.get(Constants.APP_WALLET_SECRET);
			interfaceUtils.chainAppCharge(appAccount,appSecret,userAccount, amount);
			retMap.put("success", "true");
			return retMap;
		} catch (Exception e) {
			retMap.put("success", "false");
			retMap.put("message", e.getMessage());
			return retMap;
		}
	}
	
	private String getMemo(String message, String phone) {
		String memo = message.trim();
		if (StringUtils.isNotEmpty(phone)) {
			memo += "【" + phone + "】";
		} else {
			memo += "【匿名用户】";
		}
		return memo;
	}

	private String createWallet(String openId) {
		String url = accessUrl + "/v1/app/createWallet";
		Map<String, String> params = new HashMap<>();
		params.put("accessToken", interfaceUtils.getAccessToken());
		params.put("userId", openId);
		params.put("password", defaultPassword);
		try {
			Map<String, ?> map = accessBaas(url, params);
			if (map == null || map.get("data") == null) {
				return null;
			}
			Map<String, Object> data = (Map<String, Object>) map.get("data");
                  JSONArray array = (JSONArray) data.get("accounts");
                  JSONObject job = array.getJSONObject(0);
                  String appAccount = (String)job.get("account");
			return appAccount;
		} catch (Exception e) {
			return null;
		}
	}


	public Map<String, ?> accessBaas(String url, Map<String, String> params) {
		try {
		      //String result = HttpUtils.post(url,params);
			String result = HttpRequestUtil.sendPost(url, params);
			//Map<String, Object> map = JSON.parseObject(result, Map.class);
			Map<String, Object> map = JsonUtils.String2Map(result);
			if (map != null && map.get("code") != null && "5001".equals((String)map.get("code"))) {
				//params.put("accessToken", getAccessTokenFromSystem());
				params.put("accessToken", interfaceUtils.getAccessToken());
				map = (Map<String, Object>)accessBaas(url, params);
			}
			
			return map;
		} catch (Exception e) {
			return null;
		}
	}
}