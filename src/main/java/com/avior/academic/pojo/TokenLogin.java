package com.avior.academic.pojo;

import java.io.Serializable;

public class TokenLogin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private boolean accesoCorrecto;
	private String mensaje;
	public boolean isAccesoCorrecto() {
		return accesoCorrecto;
	}
	public void setAccesoCorrecto(boolean accesoCorrecto) {
		this.accesoCorrecto = accesoCorrecto;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
}
