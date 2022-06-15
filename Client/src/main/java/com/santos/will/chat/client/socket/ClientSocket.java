package com.santos.will.chat.client.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

import com.santos.will.chat.client.api.ChatClient;
import com.santos.will.chat.dto.Message;
import com.santos.will.chat.dto.Receiver;

/**
 * Cliente do chat baseado em socket
 * 
 * @author William
 */
public class ClientSocket implements ChatClient {

	@Override
	public Boolean sendMessage(final Message message) throws IOException {
		Objects.requireNonNull(message, "message cannot be null");
		Receiver receiver = message.getReceiver();
		try (Socket socket = new Socket(receiver.getIp(), receiver.getPort());
				OutputStream output = socket.getOutputStream();
				ObjectOutputStream objectOut = new ObjectOutputStream(output)) {
			objectOut.writeObject(message);
			return true;
		} catch (IOException e) {
			throw e;
		}
	}

}
