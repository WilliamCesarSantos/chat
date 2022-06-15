package com.santos.will.chat.client.api;

import java.io.IOException;
import java.util.Collection;

/**
 * Representa um servidor para o nosso chat
 * 
 * @author William
 */
public interface ChatServer {

	/**
	 * Registra listener no servidor, para que seja possivel receber as
	 * notifica��es
	 * 
	 * @param listener
	 */
	void addListener(final ChatServerListener listener);

	/**
	 * Remove o listener da {@link Collection} para que este n�o seja mais
	 * notificado sobre a a��es que ocorrem no server
	 * 
	 * @param listener
	 */
	void removeListener(final ChatServerListener listener);

	/**
	 * Inicia o servidor de chat
	 */
	void start();

	/**
	 * Finaliza a execu��o do servidor de chat
	 */
	void shutdown() throws IOException;
}
