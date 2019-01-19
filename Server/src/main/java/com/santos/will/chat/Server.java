package com.santos.will.chat;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.santos.will.chat.server.ManagerReceiver;
import com.santos.will.chat.server.Properties;
import com.santos.will.chat.server.ReadReceiver;
import com.santos.will.chat.server.view.ServerView;

/**
 * Parte servidor de dados e regras de acesso
 * 
 * @author William
 */
public class Server {

	public static final Integer DEFAULT_POOL_THREAD_SIZE_SCHEDULER = 1;
	public static final Integer DEFAULT_POOL_THREAD_SIZE = 4;

	private final ExecutorService threadPool;
	private final ScheduledExecutorService scheduledThreadPool;

	public Server(final Integer poolThreadSize, final Integer poolThreadSizeSchedule) {
		this.threadPool = Executors.newFixedThreadPool(poolThreadSize);
		this.scheduledThreadPool = Executors.newScheduledThreadPool(poolThreadSizeSchedule);
	}

	public void initServer(final Integer port, final ManagerReceiver manager) throws IOException {
		Objects.requireNonNull(port, "Port cannot be null");
		final ServerSocket server = new ServerSocket(port);
		while (!server.isClosed()) {
			final Socket connection = server.accept();
			threadPool.submit(() -> {
				final ReadReceiver receiver = new ReadReceiver(manager);
				try {
					receiver.read(connection);
					receiver.write(connection);
				} catch (IOException e) {
					throw new RuntimeException(e);
				} finally {
					try {
						connection.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
		threadPool.shutdown();
		scheduledThreadPool.shutdown();
	}

	public ManagerReceiver initManager(final Long clientSessionTimeout, final Long clientSessionTimerCheck) {
		final ManagerReceiver manager = new ManagerReceiver(clientSessionTimeout);
		this.scheduledThreadPool.scheduleAtFixedRate(() -> manager.removeExpired(), clientSessionTimerCheck,
				clientSessionTimerCheck, TimeUnit.SECONDS);
		return manager;
	}

	public ServerView initView(final Long refreshRate, final ManagerReceiver manager) {
		final ServerView frame = new ServerView();
		frame.setVisible(true);
		this.scheduledThreadPool.scheduleAtFixedRate(() -> frame.updateReceivers(manager.getReceivers()), refreshRate, refreshRate, TimeUnit.SECONDS);
		return frame;
	}

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
		try {
			final InputStream stream = Server.class.getClassLoader().getResourceAsStream("config.properties");
			final Properties properties = new Properties();
			properties.load(stream);

			final Server server = new Server(properties.getPoolThreadSize(), properties.getPoolThreadSizeScheduler());
			final ManagerReceiver manager = server.initManager(properties.getClientSessionTimeout(), properties.getClientSessionTimerCheck());
			server.initView(properties.getViewRefreshRateClientList(), manager);
			server.initServer(properties.getSocketPort(), manager);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
