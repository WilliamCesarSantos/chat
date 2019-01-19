package com.santos.will.chat.client.api;

import com.santos.will.chat.dto.Message;

/**
 * Executa um processamento de mensagem recebida no servidor
 * 
 * @author William
 */
public interface MessageProcess {

	/**
	 * Dispara a ação para fazer o tratamento da mensagem
	 * 
	 * @param message
	 */
	void dispatch(final Message message);
}
