package com.dooool.OrderServer.common.redis;

import org.apache.log4j.Logger;
import redis.clients.jedis.JedisCluster;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zds 说明：redis作为缓存时，在异常时有以后默认返回 1。单个object：返回null 2.long或int时：返回-1 3.map：返回空map 4：list：返回空list 5：数据时：返回空数组
 */
public class RedisClientAdapterImpl implements MemcachedClientAdapter {

	/**
	 * 日志
	 */
	private final static Logger logger = Logger.getLogger(RedisClientAdapterImpl.class);
	/**
	 * redis集群客户端
	 */
	private JedisCluster jedisCluster;

	/**
	 * @param jedisCluster
	 */
	public RedisClientAdapterImpl(JedisCluster jedisCluster) {
		super();
		this.jedisCluster = jedisCluster;
	}

	 
	public void set(String key, Object value, int cacheTime) {
		try {
			String str = value == null ? "" : String.valueOf(value);
			if (cacheTime == 0) {
				jedisCluster.set(key, str);
			} else {
				jedisCluster.setex(key, cacheTime, str);
			}
		} catch (Exception e) {
			logger.error("RedisClient set error!", e);
		}
	}

	 
	public void replace(String key, Object value, int cacheTime) {
		try {
			String str = value == null ? "" : String.valueOf(value);
			if (cacheTime == 0) {
				jedisCluster.set(key, str);
			} else {
				jedisCluster.set(key, str, "XX", "PX", (long) cacheTime);
			}
		} catch (Exception e) {
			logger.error("RedisClient replace error!", e);
		}
	}

	 
	public boolean keyExists(String key) {
		try {
			return jedisCluster.exists(key);
		} catch (Exception e) {
			logger.error("RedisClient keyExists error!", e);
			return false;
		}
	}

	public void delete(String key) {
		try {
			jedisCluster.del(key);
		} catch (Exception e) {
			logger.error("RedisClient del error!", e);
		}
	}

	//
	public Object get(String key) {
		try {
			return jedisCluster.get(key);
		} catch (Exception e) {
			logger.error("RedisClient get error!", e);
			return null;
		}
	}

	//
	public String getString(String key) {
		try {
			// TODO 有object
			return jedisCluster.get(key);
		} catch (Exception e) {
			logger.error("RedisClient getString error!", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	//
	public <T> Map<String, T> getMulti(String[] keys) {
		if(jedisCluster instanceof JedisClusterAdaptor){
			return ((JedisClusterAdaptor) jedisCluster).getMulti(keys);
		}else{
			Map<String, T> rltMap = new HashMap<String, T>();
			String tmpValue = null;
			try {
				if (keys == null) {
					logger.error("get multi error form redis ,keys is null!");
					return rltMap;
				}
				for (String key : keys) {
					tmpValue = jedisCluster.get(key);
					rltMap.put(key, tmpValue == null ? null : (T) tmpValue);
				}
			} catch (Exception e) {
				logger.error("RedisClient getMulti error!", e);
			}
			return rltMap;
		}
	}

	@SuppressWarnings("unchecked")
	//
	public <T> Map<String, T> getMulti(Collection<String> keys) {
		Map<String, T> rltMap = new HashMap<String, T>();
		String tmpValue = null;
		try {
			if (keys == null) {
				logger.error("get multi error form redis ,keys is null!");
				return rltMap;
			}
			for (String key : keys) {
				tmpValue = jedisCluster.get(key);
				rltMap.put(key, tmpValue == null ? null : (T) tmpValue);
			}
		} catch (Exception e) {
			logger.error("RedisClient getMulti error!", e);
		}
		return rltMap;
	}

	//
	public Object[] getMultiArray(String[] keys) {
		Object[] rltArray = new Object[0];
		try {
			if (keys == null) {
				logger.error("get multi error form redis ,keys is null!");
				return rltArray;
			}
			rltArray = new Object[keys.length];
			for (int i = 0; i < keys.length; i++) {
				rltArray[i] = jedisCluster.get(keys[i]);
			}
		} catch (Exception e) {
			logger.error("RedisClient getMultiArray error!", e);
		}
		return rltArray;
	}

	//
	public long incr(String key) {
		try {
			return jedisCluster.incr(key);
		} catch (Exception e) {
			logger.error("RedisClient incr error!", e);
			return -1;
		}
	}

	//
	public boolean add(String key, int exp, Object value) {
		try {
			String str = value == null ? "" : String.valueOf(value);
			if (exp == 0) {
				return jedisCluster.setnx(key, str) != null;
			} else {
				return jedisCluster.set(key, str, "NX", "PX", (long) exp) != null;
			}
		} catch (Exception e) {
			logger.error("RedisClient add error!", e);
			return false;
		}
	}

	//
	public long decr(String key) {
		try {
			return jedisCluster.decr(key);
		} catch (Exception e) {
			logger.error("RedisClient decr error!", e);
			return -1;
		}
	}

	//
	public Object getMemcached() {
		return jedisCluster;
	}

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

}
