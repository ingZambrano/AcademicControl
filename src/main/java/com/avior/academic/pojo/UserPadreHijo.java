package com.avior.academic.pojo;

import java.io.Serializable;

public class UserPadreHijo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	private String userId;
	private String matricula;
	private long idAlumno;
	private String nombreAlumno;
	private String nombrePadre;	
	private String userName;
	
	
	public UserPadreHijo(){
		super();
	}
	
	public UserPadreHijo(boolean esDummie){
		if(esDummie){
			this.userId = "";
			this.matricula = "";
			this.idAlumno = -1;
			this.nombreAlumno = "";
			this.nombrePadre = "";
			this.userName = "";
		}
	}
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public long getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(long idAlumno) {
		this.idAlumno = idAlumno;
	}
	public String getNombreAlumno() {
		return nombreAlumno;
	}
	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}
	public String getNombrePadre() {
		return nombrePadre;
	}
	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}
	
	

}
