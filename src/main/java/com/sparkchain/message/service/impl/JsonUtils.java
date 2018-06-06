package com.sparkchain.message.service.impl;



import net.sf.json.JSONObject;

import java.util.Map;

public class JsonUtils {

      public static Map<String,Object> String2Map(String str){
            JSONObject myJson = JSONObject.fromObject(str);
            Map m = myJson;
            return m;
      }
}
