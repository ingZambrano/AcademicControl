package com.avior.academic.pojo;

import java.io.Serializable;

public class Avisos implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long idCircular;
	private String fVencimiento;
	private String fPublicacion;
	private String titulo;
	private String status;
	private int	statusInt;
	private int reviso;
		
	
	public long getIdCircular() {
		return idCircular;
	}
	public void setIdCircular(long idCircular) {
		this.idCircular = idCircular;
	}
	public String getfVencimiento() {
		return fVencimiento;
	}
	public void setfVencimiento(String fVencimiento) {
		this.fVencimiento = fVencimiento;
	}
	public String getfPublicacion() {
		return fPublicacion;
	}
	public void setfPublicacion(String fPublicacion) {
		this.fPublicacion = fPublicacion;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStatusInt() {
		return statusInt;
	}
	public void setStatusInt(int statusInt) {
		this.statusInt = statusInt;
	}
	public int getReviso() {
		return reviso;
	}
	public void setReviso(int reviso) {
		this.reviso = reviso;
	}
	
	
	

}
