package com.avior.academic.pojo;

import java.io.Serializable;

public class ReporteTutor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String IDWLink;
	private String WLurl;
	private String WLFecha;
	private String IDAlumno;
	private String IDWReport;
	private String BoletaNombre;
	
	
	public String getIDWLink() {
		return IDWLink;
	}
	public void setIDWLink(String iDWLink) {
		IDWLink = iDWLink;
	}
	public String getWLurl() {
		return WLurl;
	}
	public void setWLurl(String wLurl) {
		WLurl = wLurl;
	}
	
	
	public String getWLFecha() {
		return WLFecha;
	}
	public void setWLFecha(String wLFecha) {
		WLFecha = wLFecha;
	}
	public String getIDAlumno() {
		return IDAlumno;
	}
	public void setIDAlumno(String iDAlumno) {
		IDAlumno = iDAlumno;
	}
	public String getIDWReport() {
		return IDWReport;
	}
	public void setIDWReport(String iDWReport) {
		IDWReport = iDWReport;
	}
	public String getBoletaNombre() {
		return BoletaNombre;
	}
	public void setBoletaNombre(String boletaNombre) {
		BoletaNombre = boletaNombre;
	}
	
	

}
