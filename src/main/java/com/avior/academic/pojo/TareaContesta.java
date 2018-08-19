package com.avior.academic.pojo;

import java.io.Serializable;


public class TareaContesta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long idTareaA;
	private long idTarea;
	private String taComenMa;
	private String taComenAlu;
	private String taComenTut;
	private long idAlumnoGrupo;
	private float taCalific;
	private String taContenido;
	private String userId;
	
	
	public long getIdTareaA() {
		return idTareaA;
	}
	public void setIdTareaA(long idTareaA) {
		this.idTareaA = idTareaA;
	}
	public long getIdTarea() {
		return idTarea;
	}
	public void setIdTarea(long idTarea) {
		this.idTarea = idTarea;
	}
	public String getTaComenMa() {
		return  taComenMa != null ? taComenMa : "";
	}
	public void setTaComenMa(String taComenMa) {
		this.taComenMa = taComenMa;
	}
	public String getTaComenAlu() {
		return taComenAlu != null ? taComenAlu : "";
	}
	public void setTaComenAlu(String taComenAlu) {
		this.taComenAlu = taComenAlu;
	}
	public String getTaComenTut() {
		return taComenTut != null ? taComenTut : "";
	}
	public void setTaComenTut(String taComenTut) {
		this.taComenTut = taComenTut;
	}
	public long getIdAlumnoGrupo() {
		return idAlumnoGrupo;
	}
	public void setIdAlumnoGrupo(long idAlumnoGrupo) {
		this.idAlumnoGrupo = idAlumnoGrupo;
	}
	public float getTaCalific() {
		return taCalific;
	}
	public void setTaCalific(float taCalific) {
		this.taCalific = taCalific;
	}
	public String getTaContenido() {
		return taContenido != null ? taContenido : "";
	}
	public void setTaContenido(String taContenido) {
		this.taContenido = taContenido;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
