package com.santos.will.chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

import com.santos.will.chat.dto.Receiver;

/**
 * Faz a leitura da mensagem recebida do cliente
 * 
 * @author William
 */
public class ReadReceiver {

	private final ManagerReceiver manager;

	public ReadReceiver(final ManagerReceiver manager) {
		Objects.requireNonNull(manager, "manager cannot be null");
		this.manager = manager;
	}

	/**
	 * Faz a leitura do receiver recebido e adiciona na lista de usuários
	 */
	public void read(final Socket connection) throws IOException {
		Objects.requireNonNull(connection, "connection cannot be null");
		try {
			ObjectInputStream objInput = new ObjectInputStream(connection.getInputStream());
			// Receiver é o DTO de comunicação entre os projetos
			Object object = objInput.readObject();
			if (!object.getClass().equals(Receiver.class)) {
				throw new IllegalStateException("Trafego com o servidor deve ser com a classe Receiver");
			}
			Receiver receiver = (Receiver) object;

			this.manager.addOrUpdate(receiver);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Envia para o cliente a lista de clientes existentes no server
	 */
	public void write(final Socket connection) throws IOException {
		Objects.requireNonNull(connection, "connection cannot be null");
		ObjectOutputStream objOutput = new ObjectOutputStream(connection.getOutputStream());
		objOutput.writeObject(new ArrayList<>(this.manager.getReceivers()));
		objOutput.flush();
	}
}
