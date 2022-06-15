package com.santos.will.chat.client;

import java.io.IOException;
import java.io.InputStream;

import com.santos.will.chat.Client;

public class Properties {

	public static final String SOCKET_PORT = "socket.port";
	public static final String SOCKET_TIMEOUT = "socket.timeout";

	public static final String POOL_THREAD_SIZE_SCHEDULER = "pool.thread.size.scheduler";
	public static final String POOL_THREAD_SIZE = "pool.thread.size";

	public static final String SERVER_URI = "server.uri";
	public static final String SERVER_PORT = "server.port";

	public static final String USER_NICKNAME = "user.nickname";
	
	public static final String NOTIFY_SERVER_TIMER = "notify.server.timer";

	private final java.util.Properties properties = new java.util.Properties();

	public Integer getSocketPort() {
		return Integer.valueOf(get(SOCKET_PORT, null));
	}
	
	public Integer getSocketTimeout() {
		return Integer.valueOf(get(SOCKET_TIMEOUT, null));
	}
	
	public Integer getPoolThreadSizeScheduler() {
		return Integer.valueOf(get(POOL_THREAD_SIZE_SCHEDULER, Client.POOL_THREAD_SIZE_SCHEDULER_DEFAULT.toString()));
	}
	
	public Integer getPoolThreadSize() {
		return Integer.valueOf(get(POOL_THREAD_SIZE, Client.POOL_THREAD_SIZE_DEFAULT.toString()));
	}
	
	public String getServerUri() {
		return get(SERVER_URI, null);
	}
	
	public Integer getServerPort() {
		return Integer.valueOf(get(SERVER_PORT, null));
	}
	
	public String getNickName() {
		return get(USER_NICKNAME, null);
	}
	
	public Integer getNotifyServerTimer() {
		return Integer.valueOf(get(NOTIFY_SERVER_TIMER, Client.NOTIFY_SERVER_TIMER_DEFAULT.toString())); 
	}
	
	public void load(final InputStream stream) throws IOException {
		this.properties.load(stream);
	}

	private String get(final String key, final String defaultValue) {
		return this.properties.getProperty(key, defaultValue);
	}

}
