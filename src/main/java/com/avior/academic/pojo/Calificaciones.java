package com.avior.academic.pojo;

import java.io.Serializable;

public class Calificaciones implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String alumno;
	private String nivel;
	private String gradoX;
	private String clave;
	private String claveMat;
	private String mNombreC;
	private String calif;
	private String pNumber;
	
	
	public String getAlumno() {
		return alumno;
	}
	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getGradoX() {
		return gradoX;
	}
	public void setGradoX(String gradoX) {
		this.gradoX = gradoX;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getClaveMat() {
		return claveMat;
	}
	public void setClaveMat(String claveMat) {
		this.claveMat = claveMat;
	}
	public String getmNombreC() {
		return mNombreC;
	}
	public void setmNombreC(String mNombreC) {
		this.mNombreC = mNombreC;
	}
	public String getCalif() {
		return calif;
	}
	public void setCalif(String calif) {
		this.calif = calif;
	}
	public String getpNumber() {
		return pNumber;
	}
	public void setpNumber(String pNumber) {
		this.pNumber = pNumber;
	}
	
	

}
