package com.avior.academic.service;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tempuri.LoginWebServiceSoapProxy;

import com.avior.academic.dao.LoginData;

@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService {
	
	private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	@Autowired
	@Qualifier("loginDataImp")
	LoginData loginDataImp;

	@Override
	public boolean getLogin(String endPoint, String usrName, String password) {
		boolean ret = false;
		try {
			logger.info("Se inicia la petición al web service de academic para login");
			LoginWebServiceSoapProxy loginService = new LoginWebServiceSoapProxy();
			loginService.setEndpoint(endPoint);
			logger.info("Se crea la instancia con el endPoint: "+endPoint);
//			String resp = loginService.esLoginAutentico("ADMINISTRADOR", "*ac2015+-");
			String resp = loginService.esLoginAutentico(usrName, password);
//			String resp = "True";
			if(resp.equals("True")){
				ret = true;
			}
		} catch (Exception e) {
			logger.error("Error al consumir el web service: ",e);
		}
		return ret;
	}
	
	@Override
	public String cambiarPass(String endPoint, String oldPass, String newPass, String userName) {
		
		String ret =null;
		try{
			
			LoginWebServiceSoapProxy loginService = new LoginWebServiceSoapProxy();
			loginService.setEndpoint(endPoint);
			ret = loginService.cambiarPass(oldPass,newPass, userName);
			
			
		}catch(Exception e){
			logger.error("Error al cambiar el password: ", e);
		}
		
		return ret;
	}

	@Override
	public boolean validaMailUser(String mail, String usrId) throws Exception {
		
		return loginDataImp.validaMailUser(mail, usrId);
	}

	

}
