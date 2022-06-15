package com.santos.will.chat.server;

import java.io.IOException;
import java.io.InputStream;

import com.santos.will.chat.Server;
import com.santos.will.chat.server.view.ServerView;

public class Properties {

	public static final String SOCKET_PORT = "socket.port";
	public static final String SOCKET_TIMEOUT = "socket.timeout";

	public static final String CLIENT_SESSION_TIMEOUT = "client.session.timeout";
	public static final String CLIENT_SESSION_TIMER_CHECK = "client.session.timer.check";

	public static final String POOL_THREAD_SIZE_SCHEDULER = "pool.thread.size.scheduler";
	public static final String POOL_THREAD_SIZE = "pool.thread.size";

	public static final String VIEW_REFRESH_RATE_CLIENT_LIST = "view.refresh.rate.client.list";

	private final java.util.Properties properties = new java.util.Properties();

	public Properties() {
	}

	public Integer getSocketPort() {
		return Integer.valueOf(get(SOCKET_PORT, null));
	}

	public Integer getSocketTimeout() {
		return Integer.valueOf(get(SOCKET_TIMEOUT, null));
	}

	public Long getClientSessionTimeout() {
		return Long.valueOf(get(CLIENT_SESSION_TIMEOUT, null));
	}

	public Long getClientSessionTimerCheck() {
		return Long.valueOf(get(CLIENT_SESSION_TIMER_CHECK, null));
	}

	public Integer getPoolThreadSizeScheduler() {
		return Integer.valueOf(get(POOL_THREAD_SIZE_SCHEDULER, Server.DEFAULT_POOL_THREAD_SIZE_SCHEDULER.toString()));
	}

	public Integer getPoolThreadSize() {
		return Integer.valueOf(get(POOL_THREAD_SIZE, Server.DEFAULT_POOL_THREAD_SIZE.toString()));
	}

	public Long getViewRefreshRateClientList() {
		return Long.valueOf(get(VIEW_REFRESH_RATE_CLIENT_LIST, ServerView.DEFAULT_REFRESH_RATE_CLIENT_LIST.toString()));
	}

	public void load(final InputStream stream) throws IOException {
		this.properties.load(stream);
	}

	private String get(final String key, final String defaultValue) {
		return this.properties.getProperty(key, defaultValue);
	}
}
