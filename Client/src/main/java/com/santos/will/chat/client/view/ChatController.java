package com.santos.will.chat.client.view;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import com.santos.will.chat.client.api.ChatClient;
import com.santos.will.chat.client.api.ChatServer;
import com.santos.will.chat.client.api.ChatServerListener;
import com.santos.will.chat.dto.Message;
import com.santos.will.chat.dto.Receiver;
import com.santos.will.chat.dto.Sender;

/**
 * Faz o controle da parte visual do chat
 * 
 * @author wcsantos
 * @since 1.0.0 09/11/16
 */
public class ChatController implements ChatServerListener {

	private final String nickname;
	private final ChatClient client;
	private final ChatView view;

	public ChatController(final ChatClient client, final String nickname) {
		Objects.requireNonNull(client, "client cannot be null");
		Objects.requireNonNull(nickname, "nickname cannot be null");
		this.client = client;
		this.nickname = nickname;
		view = new ChatView(this);
	}

	public void sendMessage(final String message) {
		final Receiver receiver = view.getReceiver();
		if (receiver == null) {
			JOptionPane.showMessageDialog(view, "Destinatário não informado");
			return;
		}
		Sender sender = new Sender();
		sender.setName(this.nickname);
		Message messageToSend = new Message();
		messageToSend.setReceiver(receiver);
		messageToSend.setSender(sender);
		messageToSend.setText(message);
		try {
			this.client.sendMessage(messageToSend);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "Ocorreu um erro inesperado no envio");
		}
	}

	public void setVisible(final boolean visible) throws IOException {
		this.view.setVisible(visible);
	}

	@Override
	public void serverStarted(ChatServer server) {
		Objects.requireNonNull(server, "server cannot be null");
		System.out.println("Servidor iniciado");
	}

	@Override
	public void messageReceived(Message message) {
		Objects.requireNonNull(message, "message cannot be null");
		Objects.requireNonNull(message.getSender(), "sender cannot be null");
		String textMessage = String.format("%s disse: %s", message.getSender().getName(), message.getText());
		// William disse: eae pessoal, bão
		this.view.getModel().addElement(textMessage);
	}

	@Override
	public void serverShutdown(ChatServer server) {
		Objects.requireNonNull(server, "server cannot be null");
		System.out.println("Servidor finalizado");
	}

	@Override
	public void updateReceivers(final List<Receiver> receivers) {
		receivers.removeIf(receiver -> this.nickname.equals(receiver.getName()));
		this.view.updateReceivers(receivers);
	}
}
