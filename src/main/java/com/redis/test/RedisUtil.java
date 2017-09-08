package com.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 创建redis连接池
 * @author nmm
 *
 */
public class RedisUtil {

	//服务器IP
	private static String addr = "127.0.0.1";
	//端口号
	private static int port = 6379;
	//访问密码
	private static String auth = null;
	//最大可用连接数，默认为8，-1表示不限制，如果pool达到最大连接数，pool状态为耗尽exhausted
	private static int MAX_ACTIVE =1024;
	//一个pool最多有多少个空闲idle的jedis实例。默认为8
	private static int MAX_IDLE = 200;
	//等待可用连接的最大时间，单位毫秒，默认为-1不限制，如果超时，抛出JedisConnectionException
	private static int MAX_WAIT = 10000;
	private static int TIMEOUT = 100000;
	//在borrow一个jedis实例时，是否提前校验可用性，如果为true，则得到的jedis实例都是可用的。
	private static boolean TEST_ON_BORROW = true;
	
	private static JedisPool pool = null;
	
	static{
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(MAX_IDLE);
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			pool = new JedisPool(config, addr, port, TIMEOUT, auth);
		} catch (Exception e) {
			
		}
	}
	
	public Jedis getJedis(){
		if(pool != null){
			return pool.getResource();
		}
		return null;
	}
	
	public void returnResource(Jedis jedis){
		pool.returnResource(jedis);
	}
}
