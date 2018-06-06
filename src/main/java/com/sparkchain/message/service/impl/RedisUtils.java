package com.sparkchain.message.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
@Service
public class RedisUtils {

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 一周有多少秒
	 */
	private static final long WEEK_SECONDS = 7 * 24 * 60 * 60;

	/**
	 * 将 key，value 存放到redis数据库中，默认设置过期时间为一周
	 *
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		if (value instanceof String) {
			redisTemplate.opsForValue().set(key, (String) value, WEEK_SECONDS, TimeUnit.SECONDS);
		} else {
			redisTemplate.opsForValue().set(key, JSON.toJSONString(value), WEEK_SECONDS, TimeUnit.SECONDS);
		}
	}

	/**
	 * 将 key，value 存放到redis数据库中，设置过期时间单位是秒
	 *
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public void set(String key, Object value, long expireTime) {
		if (value instanceof String) {
			redisTemplate.opsForValue().set(key, (String) value, expireTime, TimeUnit.SECONDS);
		} else {
			redisTemplate.opsForValue().set(key, JSON.toJSONString(value), expireTime, TimeUnit.SECONDS);
		}
	}

	/**
	 * 判断 key 是否在 redis 数据库中
	 *
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 获取与 key 对应的对象
	 *
	 * @param key
	 * @param clazz
	 *            目标对象类型
	 * @param <T>
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz) {
		String s = get(key);
		if (s == null) {
			return null;
		}
		return JSON.parseObject(s, clazz);
	}

	/**
	 * 获取 key 对应的字符串
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String result = redisTemplate.opsForValue().get(key);
		if(StringUtils.isEmpty(result)){
			result = "";
		}
		return result;
	}

	/**
	 * 删除 key 对应的 value
	 *
	 * @param key
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	public void lpush(String key, Object value) {
    	BoundListOperations<String, String> ops = redisTemplate.boundListOps(key);
    	ops.leftPush(JSON.toJSONString(value));
    }
	
	public void rpush(String key, Object value) {
    	BoundListOperations<String, String> ops = redisTemplate.boundListOps(key);
    	ops.rightPush(JSON.toJSONString(value));
    }
	
	public void setByIndex(String key, Object value, long index) {
    	BoundListOperations<String, String> ops = redisTemplate.boundListOps(key);
    	ops.set(index, JSON.toJSONString(value));
    }
	
	public <T> T lpop(String key, Class<T> clazz) {
		BoundListOperations<String, String> ops = redisTemplate.boundListOps(key);
		String s = ops.leftPop();
		if (s == null) {
			return null;
		}
		return JSON.parseObject(s, clazz);
	}
	
	public <T> T rpop(String key, Class<T> clazz) {
		BoundListOperations<String, String> ops = redisTemplate.boundListOps(key);
		String s = ops.rightPop();
		if (s == null) {
			return null;
		}
		return JSON.parseObject(s, clazz);
	}
	
	public <T> List<T> range(String key, Class<T> clazz, long start, long end) {
		BoundListOperations<String, String> ops = redisTemplate.boundListOps(key);
		List<String> rawList = ops.range(start, end);
		List<T> list = new ArrayList<>();
		for (String s : rawList) {
			list.add(JSON.parseObject(s, clazz));
		}
		return list;
	}
	
	public long size(String key) {
		BoundListOperations<String, String> ops = redisTemplate.boundListOps(key);
		return ops.size();
	}
}