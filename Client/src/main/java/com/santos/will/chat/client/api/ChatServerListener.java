package com.santos.will.chat.client.api;

import java.util.List;

import com.santos.will.chat.dto.Message;
import com.santos.will.chat.dto.Receiver;

/**
 * Listener para notificação de ações que ocorram no servidor do chat
 * 
 * @author William
 */
public interface ChatServerListener {

	/**
	 * Notifica que o server iniciou, esta aberto a receber mensagens
	 * 
	 * @param server
	 */
	void serverStarted(final ChatServer server);

	/**
	 * Noficação de que o servidor recebeu uma nova mensagem de algum cliente
	 * 
	 * @param message
	 */
	void messageReceived(final Message message);

	/**
	 * Notifica que o server parou a execução, não é possível mais comunicação
	 * 
	 * @param server
	 */
	void serverShutdown(final ChatServer server);

	/**
	 * Atualiza a lista de destinatários on-line para receber mensagens
	 * 
	 * @param receivers
	 */
	void updateReceivers(final List<Receiver> receivers);
}
