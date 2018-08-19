package com.avior.academic.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"userName","eMail"})
public class UserInfo {
	
	private String userId;
	private String nombreUser; //El nombre de pila del usuario
	private String photoBase64;
//	private String userName;  //Nombre de usuario del sistema
	private String token;
	private String role;
	private String eMail;
	private String pathPhotoUser;
	
	public UserInfo(boolean esUsuarioDomie){
		if(esUsuarioDomie){
			this.userId = "";
			this.nombreUser = "";
			this.photoBase64 = "";
			this.token ="";
			this.role = "Tutor";
			this.eMail = "";
			this.pathPhotoUser = "";//"https://colegioscadi.academiccontrol.mx/AppData/files/UsersPhoto/Medium/4353C8C1-6617-4E20-93C8-1F727C464AC3.jpg";
			
			
		}
		
	}
	public UserInfo(){
		super();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getNombreUser() {
		return nombreUser;
	}
	public void setNombreUser(String nombreUser) {
		this.nombreUser = nombreUser;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPhotoBase64() {
		return photoBase64;
	}
	public void setPhotoBase64(String photoBase64) {
		this.photoBase64 = photoBase64;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getPathPhotoUser() {
		return pathPhotoUser;
	}
	public void setPathPhotoUser(String pathPhotoUser) {
		this.pathPhotoUser = pathPhotoUser;
	}
	
	
} 