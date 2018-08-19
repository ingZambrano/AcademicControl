package com.avior.academic.pojo;

import java.io.Serializable;

public class AvisosDet implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long idCircular;
	private String idUser;
	private String fVencimiento;
	private String fPublicacion;
	private String titulo;
	private String descripcion;
	private String usuarioPublico;
	private String linkDescarga;
	private boolean tieneLinkDescarga;
	private String htmlString;
	
	
	public long getIdCircular() {
		return idCircular;
	}
	public void setIdCircular(long idCircular) {
		this.idCircular = idCircular;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUsuarioPublico() {
		return usuarioPublico;
	}
	public void setUsuarioPublico(String usuarioPublico) {
		this.usuarioPublico = usuarioPublico;
	}
	public String getLinkDescarga() {
		return linkDescarga;
	}
	public void setLinkDescarga(String linkDescarga) {
		this.linkDescarga = linkDescarga;
	}
	public boolean isTieneLinkDescarga() {
		return tieneLinkDescarga;
	}
	public void setTieneLinkDescarga(boolean tieneLinkDescarga) {
		this.tieneLinkDescarga = tieneLinkDescarga;
	}
	public String getHtmlString() {
		return htmlString;
	}
	public void setHtmlString(String htmlString) {
		this.htmlString = htmlString;
	}
	
		

}
