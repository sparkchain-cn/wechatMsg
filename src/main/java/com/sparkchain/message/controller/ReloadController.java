package com.sparkchain.message.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparkchain.message.common.Constants;
import com.sparkchain.message.service.impl.RedisUtils;
import com.sparkchain.message.service.impl.InterfaceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/reload")
@Controller
public class ReloadController {

      @Autowired
      RedisUtils redisService;
      @Autowired
      InterfaceUtils interfaceUtils;



      @RequestMapping("")
      public String page(ModelMap map, HttpServletRequest request){
            map.addAttribute("tokenCode", redisService.get(Constants.TOKEN_CODE));
            map.addAttribute("appcode", redisService.get(Constants.APP_CODE));
            map.addAttribute("appname", redisService.get(Constants.APP_NAME));
            //用户钱包充值个数
            map.addAttribute("accountWalletPushNum", redisService.get(Constants.ACCOUNT_WALLET_PUSH_NUM));
            //app钱包重置个数
            map.addAttribute("appWalletPushNum", redisService.get(Constants.APP_WALLET_PUSH_NUM));
            //红包最低金额
            map.addAttribute("redPacketMin", redisService.get(Constants.RED_PACKET_MIN));
            //红包最高金额
            map.addAttribute("redPacketMax", redisService.get(Constants.RED_PACKET_MAX));
            //发行方地址
            map.addAttribute("IssuerAddress", redisService.get(Constants.ISSUER_ADDRESS));
            //发行方私钥
            map.addAttribute("IssuerSecret", redisService.get(Constants.ISSUER_SECRET));
            //链上浏览器地址
            map.addAttribute("chianUrl", redisService.get(Constants.CHAIN_URL));
            return "reload";
      }


      @RequestMapping("/init")
      @ResponseBody
      public String reload(@RequestParam String tokenCode,@RequestParam String appcode,@RequestParam String appname,@RequestParam String accountWalletPushNum,
                           @RequestParam String appWalletPushNum,@RequestParam String redPacketMin,@RequestParam String redPacketMax,
      @RequestParam String IssuerAddress,@RequestParam String IssuerSecret,@RequestParam String chianUrl){
            redisService.set(Constants.TOKEN_CODE,tokenCode);
            redisService.set(Constants.APP_CODE,appcode);
            redisService.set(Constants.APP_NAME,appname);
            redisService.set(Constants.ACCOUNT_WALLET_PUSH_NUM,accountWalletPushNum);
            redisService.set(Constants.APP_WALLET_PUSH_NUM,appWalletPushNum);
            redisService.set(Constants.RED_PACKET_MIN,redPacketMin);
            redisService.set(Constants.RED_PACKET_MAX,redPacketMax);
            redisService.set(Constants.ISSUER_ADDRESS,IssuerAddress);
            redisService.set(Constants.ISSUER_SECRET,IssuerSecret);
            redisService.set(Constants.CHAIN_URL,chianUrl);

            //清缓存
            redisService.delete(Constants.HUOHUA_TOKEN);
            redisService.delete(Constants.APP_ID);
            redisService.delete(Constants.APP_SECRET);

            String token = interfaceUtils.getAccessToken();
            Map<String,Object> map = interfaceUtils.createAppWallet(token);
            JSONArray array = (JSONArray) map.get("accounts");
            JSONObject job = array.getJSONObject(0);
            String appAccount = (String)job.get("account");
            redisService.set(Constants.APP_WALLET_ACCOUNT,appAccount);
            String appSecret = interfaceUtils.getWalletSecret(appAccount);
            redisService.set(Constants.APP_WALLET_SECRET,appSecret);

            //企业钱包转账
            Map<String,Object> result = interfaceUtils.chainAppCharge(IssuerAddress,IssuerSecret,appAccount,appWalletPushNum);
            if(result.get("code").equals("200")){
                  return "成功！";
            }else{
                  return (String)result.get("message");
            }
            //String appAddress = array.;

           /* String str = "--------修改后的配置：tokenCode="+redisService.get("com.sparkchain.chain.app.token")+",----appAccount="+
                      redisService.get("com.sparkchain.chain.app.account")+",----appSecret="+redisService.get("com.sparkchain.chain.app.secret");*/
      }
}
