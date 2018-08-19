package com.avior.academic.pojo;

import java.io.Serializable;

public class School implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long idEscuela;
	private String nombreEscuela;
	private String pathLogo;
	
	
	public long getIdEscuela() {
		return idEscuela;
	}

	public void setIdEscuela(long idEscuela) {
		this.idEscuela = idEscuela;
	}

	public String getNombreEscuela() {
		return nombreEscuela;
	}

	public void setNombreEscuela(String nombreEscuela) {
		this.nombreEscuela = nombreEscuela;
	}

	public String getPathLogo() {
		return pathLogo;
	}

	public void setPathLogo(String pathLogo) {
		this.pathLogo = pathLogo;
	}
	
	

}
