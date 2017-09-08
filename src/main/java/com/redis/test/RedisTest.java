package com.redis.test;


import java.util.HashMap;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;

public class RedisTest {

	public static Jedis jedis = new Jedis("127.0.0.1");
	
	public static void main(String[] args) {
//		testString();
//		testList();
//		JedisPubSub jps = new JedisPubSub() {
//			//通过订阅和接收，也可以完成简单的通信服务。
//			public void onMessage(String channel, String message) {
//				super.onMessage(channel, message);
//				System.out.println(channel);
//				System.out.println(message);
//			}
//			
//			
//		};
//		jedis.subscribe(jps, "channel1");
		
		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("one", "one");
//		jedis.hmset("map", map);
//		System.out.println(jedis.hgetAll("map"));
		
		RedisUtil ru = new RedisUtil();
		jedis = ru.getJedis();
		System.out.println(jedis.lrange("list", 0, 10));
//		
//		Transaction tran = jedis.multi();
//		tran.watch("");//监视key值，在监视key值出现变化时，取消该次事物。
////		tran.discard();
//		tran.exec();
		
		
//		jedis.bgsave();//后台保存
	}
	/**
	 * 测试字符串存取
	 */
	private static void testString() {
		jedis.set("username", "tiantian");
		System.out.println(jedis.get("username"));
		jedis.set("password", "password");
		System.out.println(jedis.get("password"));
		//删除操作
		jedis.del("username");
		System.out.println(jedis.get("username"));
		//判断是否存在
		System.out.println(jedis.exists("username"));
		//设置过期时间
		jedis.expire("password", 10);
		//查找制定格式key
//		jedis.keys("");
	}
	/**
	 * list存储
	 */
	private static void testList(){
//		//头部添加，类似栈入
//		jedis.lpush("list", "one","two");
//		//尾部添加
//		jedis.rpush("list", "three","four");
		List<String> list = jedis.lrange("list", 0, 10);
		System.out.println(list);
		System.out.println(jedis.type("list"));
		
	}

}
