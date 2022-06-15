package com.santos.will.chat.dto;

import java.io.Serializable;

/**
 * Representa um remetente da mensagem
 * 
 * @author William
 */
public class Sender implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String ip;
	private Integer port;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}
