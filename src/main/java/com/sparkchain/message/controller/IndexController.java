package com.sparkchain.message.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sparkchain.message.service.PostMessageService;
import com.sparkchain.message.service.impl.WeixinUtils;
import com.spartchain.message.util.OtherUtils;

@RequestMapping("/")
@Controller
public class IndexController {

	@Autowired
	PostMessageService messageService;
	@Autowired
	WeixinUtils weixinUtils;
	
	@RequestMapping("/init")
	public void init(HttpServletResponse response) throws IOException {
		String weixinUrl = weixinUtils.getOpenIdUrl("");
	    response.sendRedirect(weixinUrl);
	}

	@RequestMapping("/")
	public String getOpenId(ModelMap map, HttpServletRequest request) throws ParseException, IOException {
		String code = OtherUtils.Object2String(request.getParameter("code"));
		String openId = weixinUtils.getOpendId(code);
		System.out.println("打印code---------------------------------------code="+code+".openId="+openId);
		//openId存储session
		//String openId = "rrrsduuererjuttsY432";
		request.getSession().setAttribute("openId",openId);
		Map<String, Object> wMap = messageService.getWallet(openId);
		if ("false".equals((String) wMap.get("isSendMessage"))) {
			map.addAttribute("openId", openId);
			return "message";
		}
		map.addAttribute("wallet", wMap);
		return "wallet";
	}
	
	@RequestMapping("/message/send")
	public @ResponseBody Map<String,Object> sendMessage(HttpServletRequest request) {
		String message = OtherUtils.Object2String(request.getParameter("message"));
		String phone = OtherUtils.Object2String(request.getParameter("phone"));
		String openId = OtherUtils.Object2String(request.getParameter("openId"));
		Map<String, Object> sMap = messageService.sendMessage(message, phone, openId);

		Map<String,Object> resultMap = new HashMap<>();
		if(sMap==null){
			resultMap.put("success",false);
			resultMap.put("message","无法创建钱包！");
			return resultMap;
		}
		if (!"true".equals(OtherUtils.Object2String( sMap.get("success")))) {
			resultMap.put("success",false);
			resultMap.put("message",sMap.get("message"));
			return resultMap;
		}
		Map<String, Object> wMap = messageService.getWallet(openId);
		if (!"true".equals(OtherUtils.Object2String( wMap.get("isSendMessage")))) {
			resultMap.put("success",false);
			resultMap.put("message","无法创建钱包！");
			return resultMap;
		}
		resultMap.put("success",true);
		return resultMap;
	}

	//提交钱包完毕，跳转到message页面
	@RequestMapping("/wallet")
	public String toMessage(ModelMap map, HttpServletRequest request) throws ParseException, IOException {
		//取出openId
		String openId = (String)request.getSession().getAttribute("openId");
		System.out.println("------------------------session中取出的openId="+openId);
		Map<String, Object> wMap = messageService.getWallet(openId);
		System.out.println("-----------------提交之后wMap="+wMap);
		/*if ("false".equals((String) wMap.get("isSendMessage"))) {
			map.addAttribute("openId", openId);
			return "message";
		}*/
		if(wMap!=null){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		map.addAttribute("wallet", wMap);
		return "wallet";
	}

	/*@RequestMapping("/message/list")
	public String getMessageList(ModelMap map, HttpServletRequest request) {
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		int pageno = 1;
		int pagesize = 10;
		if (OtherUtils.isEmpty(pageNo)) {
			pageno = Integer.parseInt(pageNo);
		}
		if (OtherUtils.isEmpty(pageSize)) {
			pagesize = Integer.parseInt(pageSize);
		}
		List<Map> list = messageService.getMessageList(pageno, pagesize);
		map.addAttribute("messageList", list);
		return "messageList";
	}*/

	/*@RequestMapping("/token/create")
	public String message(ModelMap map, HttpServletRequest request) {
		String tokenCode = request.getParameter("tokenCode");
		String tokenName = request.getParameter("tokenName");
		String strAmount = request.getParameter("amount");
		Long amount = Long.parseLong(strAmount);

		if (OtherUtils.isEmpty(tokenCode) && OtherUtils.isEmpty(tokenName)
				&& OtherUtils.isEmpty(strAmount)) {
			Boolean result = messageService.createToken(tokenCode, tokenName, amount);
			map.addAttribute("tokenCode", tokenCode);
			map.addAttribute("tokenName", tokenName);
			map.addAttribute("amount", amount);
			map.addAttribute("result", result);
		} else {
			map.addAttribute("result", false);
		}

		return "createToken";
	}
*/
}
