package com.avior.academic.pojo;

import java.io.Serializable;

public class Notificacion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String titulo;
	private String fVencimiento;
	
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getfVencimiento() {
		return fVencimiento;
	}
	public void setfVencimiento(String fVencimiento) {
		this.fVencimiento = fVencimiento;
	}
	
	
	

}
