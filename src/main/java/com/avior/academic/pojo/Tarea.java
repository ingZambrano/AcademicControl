package com.avior.academic.pojo;

import java.io.Serializable;

public class Tarea implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private long iDTarea; 
	private long iDGrupo; 
	private String claveMat; 
	private String tFEntrega; 
	private String tFGuarda; 
	private int TEstatus; 
	private String tTituloT; 
	private String tDescripcionT;
	private String userId;
	private String usuario;
	private String calNombre;
	private long iDCalendario;
	private String altaPor;
	private String tFAlta;
	private String materiaX;
	private ArchivoAdjunto archivoAdjunto;
	
	
	public long getiDTarea() {
		return iDTarea;
	}
	public void setiDTarea(long iDTarea) {
		this.iDTarea = iDTarea;
	}
	public long getiDGrupo() {
		return iDGrupo;
	}
	public void setiDGrupo(long iDGrupo) {
		this.iDGrupo = iDGrupo;
	}
	public String getClaveMat() {
		return claveMat;
	}
	public void setClaveMat(String claveMat) {
		this.claveMat = claveMat;
	}
	
	public int getTEstatus() {
		return TEstatus;
	}
	public void setTEstatus(int tEstatus) {
		TEstatus = tEstatus;
	}
	public String gettTituloT() {
		return tTituloT;
	}
	public void settTituloT(String tTituloT) {
		this.tTituloT = tTituloT;
	}
	public String gettDescripcionT() {
		return tDescripcionT;
	}
	public void settDescripcionT(String tDescripcionT) {
		this.tDescripcionT = tDescripcionT;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCalNombre() {
		return calNombre;
	}
	public void setCalNombre(String calNombre) {
		this.calNombre = calNombre;
	}
	public long getiDCalendario() {
		return iDCalendario;
	}
	public void setiDCalendario(long iDCalendario) {
		this.iDCalendario = iDCalendario;
	}
	public String getAltaPor() {		
		
		return altaPor != null ? altaPor : "";
	}
	public void setAltaPor(String altaPor) {
		this.altaPor = altaPor;
	}
	
	public String getMateriaX() {
		return materiaX;
	}
	public void setMateriaX(String materiaX) {
		this.materiaX = materiaX;
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
	public String gettFAlta() {
		return tFAlta;
	}
	public void settFAlta(String tFAlta) {
		this.tFAlta = tFAlta;
	}
	public ArchivoAdjunto getArchivoAdjunto() {
		return archivoAdjunto;
	}
	public void setArchivoAdjunto(ArchivoAdjunto archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}
	
	

}
