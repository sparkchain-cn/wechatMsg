package com.sparkchain.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.sparkchain.message.common.Constants;
import com.sparkchain.message.common.PropertyConstants;
import com.sparkchain.message.service.impl.RedisUtils;
import com.spartchain.message.util.HttpRequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InterfaceUtils {

      @Autowired
      RedisUtils redisService;

      private static String ACCESS_URL = "http://tapi3.sparkchain.cn";
      private static String ACCESS_TOKEN = "/v1/app/access";
      private static String APP_CHAIN_INIT_URL = "/v1/app/init";
      private static String CHAIN_ACCOUNT_CHARGE = "/v1/account/charge";;
      private static String CREATE_APP_WALLET = "/v1/app/createAppWallet";
      private static String GET_WALLET_SECRET = "/v1/account/secret";

      //获取token并存入缓存
      public String getAccessToken(){
            //先查缓存，如没有再取token
            if(StringUtils.isEmpty(redisService.get(Constants.HUOHUA_TOKEN))){
                  String url = ACCESS_URL + ACCESS_TOKEN;
                  Map<String,String> params = new HashMap<>();
                  //如果缓存里没有appid,则需要通过接口获得
                  if(StringUtils.isEmpty(redisService.get(Constants.APP_ID))){
                        params = getAppIdAndSecret();
                  }else{
                        params.put("appid",redisService.get(Constants.APP_ID));
                        params.put("appsecret",redisService.get(Constants.APP_SECRET));
                  }
                  try {
                        Map<String, String> para2 = new HashMap<>();
                        para2.put("appid", params.get("appid"));
                        para2.put("appsecret", params.get("appsecret"));
                        //把appid和appsecret塞入缓存
                        redisService.set(Constants.APP_ID,params.get("appid"));
                        redisService.set(Constants.APP_SECRET,params.get("appsecret"));
                        String result = HttpRequestUtil.sendPost(url, params);
                        Map<String, Object> map = JSON.parseObject(result, Map.class);
                        if (map == null || map.get("data") == null) {
                              return null;
                        }
                        Map<String, Object> data = (Map<String, Object>) map.get("data");
                        String token = (String) data.get("accessToken");
                        //塞入token
                        redisService.set(Constants.HUOHUA_TOKEN, token, 1 * 60 * 60);
                        return token;
                  } catch (Exception e) {
                        return null;
                  }
            }else{
                  return redisService.get(Constants.HUOHUA_TOKEN);
            }
      }

      //获取appid appsecret
      public Map<String,String> getAppIdAndSecret() {
            String url = ACCESS_URL + APP_CHAIN_INIT_URL;
            Map<String, String> params = new HashMap<>();
            params.put("appcode", redisService.get(Constants.APP_CODE));
            params.put("appname", redisService.get(Constants.APP_NAME));
            try {
                  String result = HttpRequestUtil.sendPost(url, params);
                  Map<String, Object> map = JSON.parseObject(result, Map.class);
                  if(map.get("code").equals("200")){
                        return (Map<String, String>) map.get("data");
                  }
                  return null;
            } catch (Exception e) {
                  return null;
            }
      }
      //转账
      public Map<String,Object> chainAppCharge(String srcAccount,String srcPrivateKey,String destAccount,String amount){
            String url = ACCESS_URL + CHAIN_ACCOUNT_CHARGE;
            Map<String, String> params = new HashMap<>();
            params.put("srcAccount", srcAccount);
            params.put("srcPrivateKey", srcPrivateKey);
            params.put("destAccount", destAccount);
            params.put("amount", amount);
            params.put("chainCode", PropertyConstants.getGiftChain());
            params.put("tokenCode", redisService.get(Constants.TOKEN_CODE));
            params.put("bizId",String.valueOf(System.currentTimeMillis()));
            try {
                  String result = HttpRequestUtil.sendPost(url, params);
                  Map<String, Object> map = JSON.parseObject(result, Map.class);
                  return map;
            } catch (Exception e) {
                  return null;
            }
      }
      //创建企业钱包
      public Map<String,Object> createAppWallet(String token){
            String url = ACCESS_URL + CREATE_APP_WALLET;
            Map<String, String> params = new HashMap<>();
            params.put("accessToken", token);
            params.put("chainCodes", PropertyConstants.getGiftChain());
            try {
                  String result = HttpRequestUtil.sendPost(url, params);
                  Map<String, Object> map = JSON.parseObject(result, Map.class);
                  if(map.get("code").equals("200")){
                        return (Map<String, Object>) map.get("data");
                  }
                  return null;
            } catch (Exception e) {
                  return null;
            }
      }
      //查询私钥
      public String getWalletSecret(String account){
            String url = ACCESS_URL + GET_WALLET_SECRET;
            Map<String, String> params = new HashMap<>();
            params.put("account", account);
            params.put("chainCode", PropertyConstants.getGiftChain());
            try {
                  String result = HttpRequestUtil.sendPost(url, params);
                  Map<String, Object> map = JSON.parseObject(result, Map.class);
                  if(map.get("code").equals("200")){
                        Map<String,Object> m =(Map<String, Object>) map.get("data");
                        return (String)m.get("secret");
                  }
                  return null;
            } catch (Exception e) {
                  return null;
            }
      }
}
