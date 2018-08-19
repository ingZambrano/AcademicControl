package com.avior.academic.service;

public interface LoginService {
	
	public boolean getLogin(String endPoint, String usrName, String password);
	public boolean validaMailUser(String mail, String usr) throws Exception;
	public String cambiarPass(String endPoint, String oldPass, String newPass, String userName);

}
