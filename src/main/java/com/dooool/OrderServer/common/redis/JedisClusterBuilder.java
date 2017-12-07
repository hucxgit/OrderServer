package com.dooool.OrderServer.common.redis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class JedisClusterBuilder {

	private JedisCluster jedisCluster;
	/**
	 * 集群ip地址列表
	 */
	private String addressConfigs;
	
	/**
	 * 超时时间
	 */
	private Integer timeout;
	
	/**
	 * 最大直连数
	 */
	private Integer maxRedirections;
	/**
	 * 连接池配置
	 */
	private GenericObjectPoolConfig genericObjectPoolConfig;

	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

	/**
	 * 生产单例对象
	 * @return
	 */
	public JedisCluster build(){
		if(jedisCluster == null){
			synchronized (JedisClusterBuilder.class) {
				if(jedisCluster == null){
                    jedisCluster = new JedisClusterAdaptor(parseHostAndPort(), timeout, maxRedirections,genericObjectPoolConfig);
                }
			}
		}
		return jedisCluster;
	}
	public void shutdown(){
		try{
			if(jedisCluster != null){
				jedisCluster.close();
			}
		}catch (IOException e){

		}

	}
	/**
	 * 解析ipandport
	 * @return
	 * @throws Exception
	 */
	private Set<HostAndPort> parseHostAndPort()  {
		try {
			if(StringUtils.isBlank(addressConfigs))
				throw new IllegalArgumentException("未配置redis集群ip地址！");
			String[] ipports = addressConfigs.split(",");
			Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
			for(String ipPort : ipports){
				boolean isIpPort = p.matcher(ipPort).matches();
				if (!isIpPort) {
					throw new IllegalArgumentException("ip 或 port 不合法:"+ipPort);
				}
				String[] ipAndPort = ipPort.split(":");
				HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				hostAndPorts.add(hap);
			}
			return hostAndPorts;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IllegalArgumentException("解析 jedis 配置文件失败", ex);
		}
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}
	public void setAddressConfigs(String addressConfigs) {
		this.addressConfigs = addressConfigs;
	}
	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}
	public static void main(String[] args) {
	}
}