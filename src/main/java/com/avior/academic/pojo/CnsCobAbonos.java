package com.avior.academic.pojo;

import java.io.Serializable;

public class CnsCobAbonos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long iDCargo;
	private String Concepto;
	private String fechaVencimiento;
	private double tarifaImporte;
	private double becaMonto;
	private double recargosTotal;
	private double descuentoMonto;
	private double aPagarTotal;
	private double abonosTotal;
	private double saldoTotal;
	private long reciboNo;
	private long folio;
	private String CFDI;
	private String xmlURL;
	
	
	public long getiDCargo() {
		return iDCargo;
	}
	public void setiDCargo(long iDCargo) {
		this.iDCargo = iDCargo;
	}
	public String getConcepto() {
		return Concepto;
	}
	public void setConcepto(String concepto) {
		Concepto = concepto;
	}
	
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public double getTarifaImporte() {
		return tarifaImporte;
	}
	public void setTarifaImporte(double tarifaImporte) {
		this.tarifaImporte = tarifaImporte;
	}
	public double getBecaMonto() {
		return becaMonto;
	}
	public void setBecaMonto(double becaMonto) {
		this.becaMonto = becaMonto;
	}
	public double getRecargosTotal() {
		return recargosTotal;
	}
	public void setRecargosTotal(double recargosTotal) {
		this.recargosTotal = recargosTotal;
	}
	public double getDescuentoMonto() {
		return descuentoMonto;
	}
	public void setDescuentoMonto(double descuentoMonto) {
		this.descuentoMonto = descuentoMonto;
	}
	public double getaPagarTotal() {
		return aPagarTotal;
	}
	public void setaPagarTotal(double aPagarTotal) {
		this.aPagarTotal = aPagarTotal;
	}
	public double getAbonosTotal() {
		return abonosTotal;
	}
	public void setAbonosTotal(double abonosTotal) {
		this.abonosTotal = abonosTotal;
	}
	public double getSaldoTotal() {
		return saldoTotal;
	}
	public void setSaldoTotal(double saldoTotal) {
		this.saldoTotal = saldoTotal;
	}
	public long getReciboNo() {
		return reciboNo;
	}
	public void setReciboNo(long reciboNo) {
		this.reciboNo = reciboNo;
	}
	public long getFolio() {
		return folio;
	}
	public void setFolio(long folio) {
		this.folio = folio;
	}
	public String getCFDI() {
		return CFDI;
	}
	public void setCFDI(String cFDI) {
		CFDI = cFDI;
	}
	public String getXmlURL() {
		return xmlURL;
	}
	public void setXmlURL(String xmlURL) {
		this.xmlURL = xmlURL;
	}
	
	

}
