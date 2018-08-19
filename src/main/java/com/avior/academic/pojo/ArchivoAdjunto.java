package com.avior.academic.pojo;

import java.io.Serializable;

public class ArchivoAdjunto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long idArchivo;
	private String archivoAdjunto;
	private String htmlString;
	
	public long getIdArchivo() {
		return idArchivo;
	}
	public void setIdArchivo(long idArchivo) {
		this.idArchivo = idArchivo;
	}
	public String getArchivoAdjunto() {
		return archivoAdjunto;
	}
	public void setArchivoAdjunto(String archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}
	public String getHtmlString() {
		return htmlString;
	}
	public void setHtmlString(String htmlString) {
		this.htmlString = htmlString;
	}
	
	
}
