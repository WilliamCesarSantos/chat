package com.santos.will.chat.client.api;

import java.io.IOException;

import com.santos.will.chat.dto.Message;

/**
 * Definição do comportamento do cliente no aplicativo
 * 
 * @author William
 */
public interface ChatClient {

	/**
	 * Faz o envio da mensagem para o destinatario. <br />
	 * Destinatario deve estar presente na propria mensagem
	 * 
	 * @param message
	 * @return retorna verdadeiro caso a mensagem tenha sido enviada com sucesso
	 */
	Boolean sendMessage(final Message message) throws IOException;
}
