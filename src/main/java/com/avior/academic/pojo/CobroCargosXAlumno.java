package com.avior.academic.pojo;

import java.io.Serializable;

public class CobroCargosXAlumno implements Serializable{

	private static final long serialVersionUID = 1L;
	private long idCargo;
	private String concepto; 
	private String fechaVencimineto;
	private double saldoTotal;
//	private double abonosTotal;
	private boolean verDetalle;
	private String color;
	
	public long getIdCargo() {
		return idCargo;
	}
	public void setIdCargo(long idCargo) {
		this.idCargo = idCargo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	public String getFechaVencimineto() {
		return fechaVencimineto;
	}
	public void setFechaVencimineto(String fechaVencimineto) {
		this.fechaVencimineto = fechaVencimineto;
	}
	public double getSaldoTotal() {
		return saldoTotal;
	}
	public void setSaldoTotal(double saldoTotal) {
		this.saldoTotal = saldoTotal;
	}
//	public double getAbonosTotal() {
//		return abonosTotal;
//	}
//	public void setAbonosTotal(double abonosTotal) {
//		this.abonosTotal = abonosTotal;
//	}
	public boolean isVerDetalle() {
		return verDetalle;
	}
	public void setVerDetalle(boolean verDetalle) {
		this.verDetalle = verDetalle;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
