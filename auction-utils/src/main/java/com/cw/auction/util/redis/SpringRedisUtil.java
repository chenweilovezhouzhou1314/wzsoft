package com.cw.auction.util.redis;

import java.io.Serializable;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**@Comments ：redis工具类
 * @Author ：陈伟
 * @Group : A组
 * @Worker: 1001
 * @Date ：2016年12月4日 上午10:31:48
 * @Project ：ktshop-service 
 * @Company ：枣庄康婷控股有限公司
 */
@SuppressWarnings("unchecked")
public class SpringRedisUtil {
	
	
	private static RedisTemplate<Serializable, Serializable> readRedisTemplate = 
				(RedisTemplate<Serializable, Serializable>) ApplicationContextUtil
						.getBean("readRedisTemplate");
	
	private static RedisTemplate<Serializable, Serializable> writeRedisTemplate = 
				(RedisTemplate<Serializable, Serializable>) ApplicationContextUtil
						.getBean("writeRedisTemplate");
	
	public static void save(final String key, Object value) {

		final byte[] vbytes = SerializeUtil.serialize(value);
		writeRedisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(writeRedisTemplate.getStringSerializer().serialize(key), vbytes);
				return null;
			}
		});
	}

	public static <T> T get(final String key, Class<T> elementType) {
		return readRedisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] keybytes = readRedisTemplate.getStringSerializer().serialize(key);
				if (connection.exists(keybytes)) {
					byte[] valuebytes = connection.get(keybytes);
					T value = (T) SerializeUtil.unserialize(valuebytes);
					return value;
				}
				return null;
			}
		});
	}

}
