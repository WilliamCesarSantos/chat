package com.santos.will.chat.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.santos.will.chat.client.Properties;
import com.santos.will.chat.client.api.ChatServer;
import com.santos.will.chat.client.api.ChatServerListener;
import com.santos.will.chat.client.api.MessageProcess;
import com.santos.will.chat.dto.Receiver;

/**
 * Implementação do chat baseado em socket para conexão com demais máquinas.
 * Servidor depende de uma porta para conseguir iniciar, não pode ser passado
 * null no construtor
 * 
 * @author William
 */
public class ServerSocket implements ChatServer, Runnable {

	private final Properties properties;

	private final ExecutorService pool;
	private final ScheduledExecutorService scheduler;
	private java.net.ServerSocket server;
	private final Queue<ChatServerListener> listeners;
	private final MessageProcess processMessage;

	public ServerSocket(final Properties properties) {
		Objects.requireNonNull(properties, "properties cannot be null");
		this.properties = properties;

		this.pool = Executors.newFixedThreadPool(properties.getPoolThreadSize());
		this.scheduler = Executors.newScheduledThreadPool(properties.getPoolThreadSizeScheduler());

		this.listeners = new ConcurrentLinkedQueue<>();
		this.processMessage = new PublishMessage(this.listeners);
	}

	@Override
	public void addListener(final ChatServerListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(final ChatServerListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void start() {
		new Thread(this).start();
		try {
			final String address = InetAddress.getLocalHost().getHostAddress();
			final Integer port = this.properties.getSocketPort();
			final String nickname = this.properties.getNickName();
			managerSession(this.properties.getServerUri(), this.properties.getServerPort(),
					this.properties.getNotifyServerTimer(), new Receiver(nickname, address, port));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutdown() throws IOException {
		this.pool.shutdown();
		this.scheduler.shutdown();
		if (this.server != null && !this.server.isClosed()) {
			this.server.close();
			this.listeners.forEach(listener -> listener.serverShutdown(this));
		}
	}

	@Override
	public void run() {
		if (server != null) {
			throw new IllegalStateException("Server already started");
		}
		try {
			this.server = new java.net.ServerSocket(this.properties.getSocketPort());
			this.listeners.forEach(listener -> listener.serverStarted(this));
			while (!server.isClosed()) {
				final Socket connection = server.accept();
				this.pool.execute(() -> {
					try {
						new ReadMessage(processMessage).read(connection);
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
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void managerSession(final String serverUri, final Integer serverPort, final Integer timer,
			final Receiver receiver) throws IOException {
		scheduler.scheduleAtFixedRate(() -> {
			try (final Socket connection = new Socket(serverUri, serverPort)) {
				notifySession(connection, receiver);
				final List<Receiver> receivers = readReceivers(connection);
				this.listeners.forEach(listener -> listener.updateReceivers(receivers));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, 0, timer, TimeUnit.SECONDS);
	}

	private void notifySession(final Socket connection, final Receiver receiver) throws IOException {
		ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
		output.writeObject(receiver);
	}

	@SuppressWarnings("unchecked")
	private List<Receiver> readReceivers(final Socket connection) throws IOException {
		try {
			final ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
			final Object object = input.readObject();
			if (!List.class.isAssignableFrom(object.getClass())) {
				throw new IllegalStateException("Client version incompatible of Server version");
			}
			return (List<Receiver>) object;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
