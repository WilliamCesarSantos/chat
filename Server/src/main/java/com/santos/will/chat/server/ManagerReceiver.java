package com.santos.will.chat.server;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.santos.will.chat.dto.Receiver;

/**
 * Controla os usuários ativos do app
 * 
 * @author William.Santos
 */
public class ManagerReceiver {

	private final Long clientSessionTimeout;
	private final List<ReceiverSession> sessions = new ArrayList<>();

	public ManagerReceiver(final Long clientSessionTimeout) {
		this.clientSessionTimeout = clientSessionTimeout;
	}

	/**
	 * Adiciona ou atualizar um novo recevier na lista
	 * 
	 * @param receiver
	 * @return
	 */
	public ManagerReceiver addOrUpdate(final Receiver receiver) {
		Objects.requireNonNull(receiver, "Receiver cannot be null");
		
		ReceiverSession session = getSession(receiver.getName()).orElse(null);
		if(session == null) {
			session = new ReceiverSession(receiver);
			this.sessions.add(session);
		}
		session.setLastAccess(LocalDateTime.now());
		return this;
	}

	/**
	 * Procura por receivers ativos na lista
	 * 
	 * @param name
	 * @return
	 */
	public Receiver getActive(final String name) {
		Objects.requireNonNull(name, "name cannot be null");
		return getSession(name).map(ReceiverSession::getReceiver).orElse(null);
	}

	private Optional<ReceiverSession> getSession(final String name) {
		Objects.requireNonNull(name, "name cannot be null");
		return this.sessions.stream().filter(session -> name.equalsIgnoreCase(session.getReceiver().getName())).findAny();
	}
	
	/**
	 * Remove os receivers com sessao expirada
	 */
	public void removeExpired() {
		final LocalDateTime now = LocalDateTime.now();
		this.sessions.removeIf(session -> {
			final LocalDateTime lastAccess = session.getLastAccess();
			final LocalDateTime limit = lastAccess.plus(clientSessionTimeout, ChronoUnit.SECONDS);
			return limit.isBefore(now);
		});
	}

	/**
	 * Remove receiver
	 * 
	 * @param receiver
	 * @return
	 */
	public boolean remove(final Receiver receiver) {
		Objects.requireNonNull(receiver, "receiver cannot be null");
		return this.sessions.removeIf(session -> session.getReceiver().getName().equalsIgnoreCase(receiver.getName()));
	}

	/***
	 * Retorna todos os receivers na lista, inclusive os com sessao expirada
	 * 
	 * @return
	 */
	public List<Receiver> getReceivers() {
		return this.sessions.stream().map(ReceiverSession::getReceiver).collect(Collectors.toList());
	}
}
