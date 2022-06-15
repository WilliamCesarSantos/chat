package com.santos.will.chat.server;

import java.time.LocalDateTime;

import com.santos.will.chat.dto.Receiver;

/**
 * @author William
 */
public class ReceiverSession {

	private final Receiver receiver;
	private LocalDateTime lastAccess;

	public ReceiverSession(final Receiver receiver) {
		this.receiver = receiver;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public LocalDateTime getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(final LocalDateTime lastAccess) {
		this.lastAccess = lastAccess;
	}
}
