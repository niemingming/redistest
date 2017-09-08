package com.redis.test;

import java.util.HashMap;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * redis的java使用通过jedis.jar实现。具体maven引用参看pom.xml文件。
 * redis是一个key-value格式的缓存数据库。下载地址：
 * https://github.com/MSOpenTech/redis/releases。
 * 下载完后，加压到指定文件夹，进入后，执行 redis-server.exe redis.windows.conf 即启动redis服务。
 * 默认情况下，redis是没有password的校验，可以通过requirepass foobared设置
 * 
 * redis是一个高性能的key-value数据库，支持数据的持久化出力，据测试：其读取能力为110000/s，其写入数据为81000/s
 * 支持的数据格式为String，hash，list，set，zset
 * 
 * 我们通过jedis实现。
 * @author nmm
 *
 */
public class RedisStudy {
	
	private Jedis jedis;
	
	public RedisStudy(String host){
		this(host,null);
	}
	
	public RedisStudy(String host,String password){
		jedis = new Jedis(host);
		if(password != null){//校验密码
			jedis.auth(password);
		}
	}
	/**
	 * 设置字符串key-value值
	 * @param key
	 * @param value
	 */
	public void setStringValue(String key,String value){
		jedis.set(key, value);
		
//		jedis.del(key);删除
//		jedis.expire(key, 10);设置过期时间
	}
	/**
	 * 判断是否存在key
	 * @param key
	 * @return
	 */
	public boolean existsKey(String key){
		return jedis.exists(key);
	}
	
	public String getStringValue(String key){
		return jedis.get(key);
	}
	/**
	 * jedis操作示例
	 */
	public void apishili(){
		//操作list
		jedis.lpush("key", "value","value");//从开始压入，类似栈
		jedis.rpush("key", "value","value");//从尾部追加.
		jedis.lrange("key", 0, 100);//获取列表数据
		//操作配置文件
		jedis.configSet("paramname", "value");//设置配置项
		jedis.configGet("pattern");//获取配置项
		//操作hash
		jedis.hmset("key", new HashMap<String, String>());//注意，map不能为空集合
		jedis.hget("key", "field");
		jedis.hdel("key", "field");//删除操作
		jedis.hgetAll("key");//获取所有
		//操作集合
		jedis.sadd("key", "value");//set集合中添加，会有去重功能。
		jedis.smembers("key");//获取set集合数据
		//操作有序集合
		jedis.zadd("key", new HashMap<String, Double>());
		jedis.zrange("key", 0, 100);
		
		//统计基数
		jedis.pfadd("key", "datakey");//基数名称、key值
		jedis.pfcount("key");
		
		//订阅与消费
		JedisPubSub jps = new JedisPubSub() {//消息消费者
			@Override
			public void onMessage(String channel, String message) {
				System.out.println(channel);
				System.out.println(message);
			}
		};
		
		jedis.subscribe(jps, "channelname");//订阅某个频道，处理函数为jps
		jedis.publish("channelname", "message");//向某个频道发送消息
		
		
	}
}
