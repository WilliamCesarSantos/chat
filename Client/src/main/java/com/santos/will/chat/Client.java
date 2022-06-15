package com.santos.will.chat;

import java.io.InputStream;

import com.santos.will.chat.client.Properties;
import com.santos.will.chat.client.api.ChatServer;
import com.santos.will.chat.client.socket.ClientSocket;
import com.santos.will.chat.client.socket.ServerSocket;
import com.santos.will.chat.client.view.ChatController;

public class Client {

	public static final Integer POOL_THREAD_SIZE_SCHEDULER_DEFAULT = 1;
	public static final Integer POOL_THREAD_SIZE_DEFAULT = 4;
	public static final Integer NOTIFY_SERVER_TIMER_DEFAULT = 3;

	public static void main(String[] args) throws Exception {
		Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
			exception.printStackTrace();
		});
		final InputStream stream = Client.class.getClassLoader().getResourceAsStream("config.properties");
		final Properties properties = new Properties();
		properties.load(stream);

		final ChatServer chatServer = new ServerSocket(properties);
		chatServer.start();

		final ChatController controller = new ChatController(new ClientSocket(), properties.getNickName());
		chatServer.addListener(controller);
		controller.setVisible(true);
	}

}
