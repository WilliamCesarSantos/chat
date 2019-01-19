package com.santos.will.chat.dto;

import java.io.Serializable;

/**
 * Representa a mensagem que será trafega entre as máquina, sendo esta um
 * DTO(data transfer object)
 * 
 * @author William
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String text;
	private Receiver receiver;
	private Sender sender;

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public Sender getSender() {
		return sender;
	}
}
