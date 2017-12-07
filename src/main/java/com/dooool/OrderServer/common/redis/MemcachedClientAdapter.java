package com.dooool.OrderServer.common.redis;

import java.util.Collection;
import java.util.Map;

/**
 * @author chen
 *
 */
public interface MemcachedClientAdapter {
	
	public void set(String key, Object value, int cacheTime);

	/**
	 * @param key
	 * @param value
	 * @param cacheTime 秒为单位
	 */
	public void replace(String key, Object value, int cacheTime);

	public boolean keyExists(String key);

	public void delete(String key);

	public Object get(String key);

	public String getString(String key);

	public <T> Map<String, T> getMulti(String[] keys);

	public <T> Map<String, T> getMulti(Collection<String> keys);

	public Object[] getMultiArray(String[] keys);

	public long incr(String key);

	public long decr(String key);
	
	public Object getMemcached();
	
	public boolean add(String key, int exp, Object value);
}