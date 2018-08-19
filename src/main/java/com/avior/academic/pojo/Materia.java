package com.avior.academic.pojo;

import java.io.Serializable;

public class Materia implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String claveMat;
	private String materiaX;
	
	public String getClaveMat() {
		return claveMat;
	}
	public void setClaveMat(String claveMat) {
		this.claveMat = claveMat;
	}
	public String getMateriaX() {
		return materiaX;
	}
	public void setMateriaX(String materiaX) {
		this.materiaX = materiaX;
	}
	
	
	

}
