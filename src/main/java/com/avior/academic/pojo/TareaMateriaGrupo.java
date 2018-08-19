package com.avior.academic.pojo;

import java.io.Serializable;

public class TareaMateriaGrupo implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private long iDTarea; 
	private String tFEntrega; 
	private String tFGuarda;
	private int tEstatus;
	private String tTituloT;
	private String claveMat;
	private String materiaX;
	private String claveGrupo;
	private String profesor;
	private String calNombre;
	public long getiDTarea() {
		return iDTarea;
	}
	public void setiDTarea(long iDTarea) {
		this.iDTarea = iDTarea;
	}
	
	public String gettFEntrega() {
		return tFEntrega;
	}
	public void settFEntrega(String tFEntrega) {
		this.tFEntrega = tFEntrega;
	}
	public String gettFGuarda() {
		return tFGuarda;
	}
	public void settFGuarda(String tFGuarda) {
		this.tFGuarda = tFGuarda;
	}
	public int gettEstatus() {
		return tEstatus;
	}
	public void settEstatus(int tEstatus) {
		this.tEstatus = tEstatus;
	}
	public String gettTituloT() {
		return tTituloT;
	}
	public void settTituloT(String tTituloT) {
		this.tTituloT = tTituloT;
	}
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
	public String getClaveGrupo() {
		return claveGrupo;
	}
	public void setClaveGrupo(String claveGrupo) {
		this.claveGrupo = claveGrupo;
	}
	public String getProfesor() {
		return profesor;
	}
	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}
	public String getCalNombre() {
		return calNombre;
	}
	public void setCalNombre(String calNombre) {
		this.calNombre = calNombre;
	}
	
	

}
