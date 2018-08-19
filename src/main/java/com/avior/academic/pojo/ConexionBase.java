package com.avior.academic.pojo;

import java.io.Serializable;


import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class ConexionBase implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String driverClassName;
	private String url;
	private String userBD;
	private String passBD;
	private String ipBase;
	private String portBase;
	private DriverManagerDataSource dataSource;
	
	
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserBD() {
		return userBD;
	}
	public void setUserBD(String userBD) {
		this.userBD = userBD;
	}
	public String getPassBD() {
		return passBD;
	}
	public void setPassBD(String passBD) {
		this.passBD = passBD;
	}
	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getIpBase() {
		return ipBase;
	}
	public void setIpBase(String ipBase) {
		this.ipBase = ipBase;
	}
	public String getPortBase() {
		return portBase;
	}
	public void setPortBase(String portBase) {
		this.portBase = portBase;
	}
	
	

}
