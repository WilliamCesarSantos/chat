package com.santos.will.chat.dto;

import java.io.Serializable;

/**
 * Object representa um destinatario para a mensagem
 * 
 * @author William.Santos
 */
public class Receiver implements Serializable {

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
