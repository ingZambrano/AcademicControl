package com.avior.academic.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@JsonIgnoreProperties({"endPoint","email","catalogoBD","instanciaBD","usrBD","passBD"})
public class PreLogin implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean acepted;
	private String msj;
	private String base;
	private String contextoWeb;
	private School school;
	private String endPoint;
	private String email;	
	private String catalogoBD;
	private String instanciaBD;
	private String usrBD;
	private String passBD;
	private String puertoBD;
	
	public boolean isAcepted() {
		return acepted;
	}
	public void setAcepted(boolean acepted) {
		this.acepted = acepted;
	}
	public String getMsj() {
		return msj;
	}
	public void setMsj(String msj) {
		this.msj = msj;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}

	@ApiModelProperty(notes = "La escuela a la que pertenece el usuario")
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCatalogoBD() {
		return catalogoBD;
	}
	public void setCatalogoBD(String catalogoBD) {
		this.catalogoBD = catalogoBD;
	}
	public String getInstanciaBD() {
		return instanciaBD;
	}
	public void setInstanciaBD(String instanciaBD) {
		this.instanciaBD = instanciaBD;
	}
	public String getUsrBD() {
		return usrBD;
	}
	public void setUsrBD(String usrBD) {
		this.usrBD = usrBD;
	}
	public String getPassBD() {
		return passBD;
	}
	public void setPassBD(String passBD) {
		this.passBD = passBD;
	}
	public String getPuertoBD() {
		return puertoBD;
	}
	public void setPuertoBD(String puertoBD) {
		this.puertoBD = puertoBD;
	}
	public String getContextoWeb() {
		return contextoWeb;
	}
	public void setContextoWeb(String contextoWeb) {
		this.contextoWeb = contextoWeb;
	}
	
	
	
}
