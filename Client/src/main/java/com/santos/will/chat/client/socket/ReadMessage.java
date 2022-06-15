package com.santos.will.chat.client.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;

import com.santos.will.chat.client.api.MessageProcess;
import com.santos.will.chat.dto.Message;

/**
 * Objeto responsável por tratar as mensagens que chegaram ao servidor
 * 
 * @author William
 */
public class ReadMessage {

	private final MessageProcess processor;

	public ReadMessage(final MessageProcess processor) {
		Objects.requireNonNull(processor, "processor cannot be null");
		this.processor = processor;
	}

	public void read(final Socket connection) throws IOException {
		Objects.requireNonNull(connection, "connection cannot be null");
		try (InputStream input = connection.getInputStream();
				ObjectInputStream objectInput = new ObjectInputStream(input)) {
			Message message = (Message) objectInput.readObject();
			this.processor.dispatch(message);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
