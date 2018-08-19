package com.avior.academic.pojo;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class VersionServicios implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String empresa;
	private String version;
	private String programador;
	private String fecha;
	private String mensaje;
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getProgramador() {
		return programador;
	}
	public void setProgramador(String programador) {
		this.programador = programador;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
