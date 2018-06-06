package com.spartchain.message.util;

import java.util.Random;

public class OtherUtils {

	/*
	 *判断字符串是否为空 
	 * */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() <= 0) {
			return true;
		}
		return false;
	}
	
	
	/*
	 * Object转String
	 * */
	 public static String Object2String(Object obj) {
		return (obj == null) ? "" : obj.toString();
	 }

	 //最小值最大值随机数
	public static String giftAmount(String minCount, String maxCount) {
            float min = Float.valueOf(minCount);
            float max = Float.valueOf(maxCount);
            float floatBounded = min + new Random().nextFloat() * (max - min);
		return String.valueOf(floatBounded);
	}

	/***
	 * 生成uid n位数字
	 */
	public static String generateUID(int n){
		Random random = new Random();
		String result="";
		for(int i=0;i<n;i++){
			//首字母不能为0
			result += (random.nextInt(9)+1);
		}
		return result;
	}
}
