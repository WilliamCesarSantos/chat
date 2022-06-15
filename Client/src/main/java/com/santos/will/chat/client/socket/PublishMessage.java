package com.santos.will.chat.client.socket;

import java.util.List;
import java.util.Queue;

import com.santos.will.chat.client.api.ChatServerListener;
import com.santos.will.chat.client.api.MessageProcess;
import com.santos.will.chat.dto.Message;

/**
 * Objeto para publicar a mensagem que foi lida pelo {@link ReadMessage}
 * 
 * @author William
 */
public class PublishMessage implements MessageProcess {

	private final Queue<ChatServerListener> listeners;

	public PublishMessage(final Queue<ChatServerListener> listeners) {
		this.listeners = listeners;
	}

	@Override
	public void dispatch(final Message message) {
		this.listeners.forEach(listener -> listener.messageReceived(message));
	}

}
