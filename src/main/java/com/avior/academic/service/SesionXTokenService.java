package com.avior.academic.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.avior.academic.comunes.Cifrador;
import com.avior.academic.pojo.PreLogin;
import com.avior.academic.pojo.School;
import com.avior.academic.pojo.TokenLogin;

@Service("sesionXTokenService")
public class SesionXTokenService {
	
	Logger logger = Logger.getLogger(SesionXTokenService.class);
	
	@Autowired
	@Qualifier("preLoginServiceImp")
	PreLoginService preLoginServiceImp;

	@Autowired
	private PreLogin pre;
	
	public Authentication obtenerAuthentication(String token){
		
		Authentication ret = null;
		String username = "";		
		String password = "123456*";
		String eMail = "";
		int idEscuela = -1;
				
	    try {
	    	String[] descifrado = Cifrador.desencriptar(token).split("\\|\\|\\|");    	
	    	username = descifrado[0];
	    	eMail = descifrado[1];
	    	idEscuela = Integer.parseInt(descifrado[2]);

			logger.info("UserName: "+ username);
			logger.info("eMail: " + eMail);
			logger.info("idEscuela: " + idEscuela);


			PreLogin prelogin = preLoginServiceImp.getPreloginObjectSingle(eMail, idEscuela);
			
			
			if (prelogin == null) {
				return null;
			} 
			
			//Seteamos los datos para el objecto inyectado PreLogin que será utilizado en todos los servicios DAO
			/*********************************************/
//			pre.setAcepted(prelogin.isAcepted());
//			pre.setBase(prelogin.getBase());
//			pre.setMsj(prelogin.getMsj());
//			pre.setSchool(prelogin.getSchool());
//			pre.setEndPoint(prelogin.getEndPoint());
//			pre.setEmail(eMail);
//			pre.setCatalogoBD(prelogin.getCatalogoBD());
//			pre.setUsrBD(prelogin.getUsrBD());
//			pre.setPassBD(prelogin.getPassBD());
//			pre.setInstanciaBD(prelogin.getInstanciaBD());
//			pre.setPuertoBD(prelogin.getPuertoBD());
//			pre.setContextoWeb(prelogin.getContextoWeb());
			this.setPrelogin(prelogin);
			/*********************************************/
			logger.info("Acceso al usuario con mail: " + eMail);
			
			
	        ret = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
	        SecurityContextHolder.getContext().setAuthentication(ret);	        
	        
	    } catch (Exception e) {
	    	logger.error("Error al desencriptar el token o el usuario es inexistente: ",e);
	        SecurityContextHolder.getContext().setAuthentication(null);
	        
	    }
	    
	    return ret;

		
	}
	public TokenLogin dameTokenLoginDumie(String token){
		TokenLogin ret = null;
		
		ret = new TokenLogin();
		ret.setAccesoCorrecto(true);
		ret.setMensaje("Acceso con token dummy");
		
		String username = "";		
//		String password = "123456*";
		String eMail = "";
		int idEscuela = -1;
		
		try {
		  	String[] descifrado = Cifrador.desencriptar(token).split("\\|\\|\\|");    	
		  	username = descifrado[0];
		   	eMail = descifrado[1];
		   	idEscuela = Integer.parseInt(descifrado[2]);
			logger.info("UserName: "+ username);
			logger.info("eMail: " + eMail);
			logger.info("idEscuela: " + idEscuela);
			
			//Seteamos en el prelogin el eMail y el idEscuela
			PreLogin prelogin = new PreLogin();
			prelogin.setEmail(eMail);
			School sc = new School();
			sc.setIdEscuela(idEscuela);
			prelogin.setSchool(sc);
			/******/
			pre.setEmail(prelogin.getEmail());
			pre.setSchool(prelogin.getSchool());
			/******/
			
		}catch(Exception e){
			logger.error(e);
		}
		
		return ret;
	}
	
	
	public TokenLogin obtenerSesionXToken(String token){
		
		TokenLogin ret = null;
		String username = "";		
		String password = "123456*";
		String eMail = "";
		int idEscuela = -1;
				
	    try {
	    	ret = new TokenLogin();
	    	String[] descifrado = Cifrador.desencriptar(token).split("\\|\\|\\|");    	
	    	username = descifrado[0];
	    	eMail = descifrado[1];
	    	idEscuela = Integer.parseInt(descifrado[2]);
			List<PreLogin> preloginList = preLoginServiceImp.getPreloginObject(eMail);
			PreLogin prelogin = null;
			
			if (preloginList.isEmpty() || preloginList == null 	|| preloginList.size() == 0) {
				return null;
			} else if (preloginList.size() == 1) {				 
				ret.setAccesoCorrecto(true);
				ret.setMensaje("Acceso con token correcto");
				prelogin = preloginList.get(0);				
			} else {
				ret.setAccesoCorrecto(true);
				ret.setMensaje("Acceso con token correcto");
				//prelogin = preloginList.get(0);
				prelogin = preLoginServiceImp.getPreloginObjectSingle(eMail, idEscuela);
			}
			
			//Seteamos los datos para el objecto inyectado PreLogin que será utilizado en todos los servicios DAO
			/*********************************************/
//			pre.setAcepted(prelogin.isAcepted());
//			pre.setBase(prelogin.getBase());
//			pre.setMsj(prelogin.getMsj());
//			pre.setSchool(prelogin.getSchool());
//			pre.setEndPoint(prelogin.getEndPoint());
//			pre.setEmail(eMail);
//			pre.setCatalogoBD(prelogin.getCatalogoBD());
//			pre.setUsrBD(prelogin.getUsrBD());
//			pre.setPassBD(prelogin.getPassBD());
//			pre.setInstanciaBD(prelogin.getInstanciaBD());
//			pre.setPuertoBD(prelogin.getPuertoBD());
			this.setPrelogin(prelogin);
			/*********************************************/
			logger.info("Acceso al usuario con mail: " + eMail);
			
			
	        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
	        SecurityContextHolder.getContext().setAuthentication(auth);	        
	        
	    } catch (Exception e) {
	    	logger.error("Error al desencriptar el token o el usuario es inexistente: ",e);
	        SecurityContextHolder.getContext().setAuthentication(null);
	        
	    }
	    
	    return ret;

		
	}
	
	private void setPrelogin(PreLogin prelogin){
		
		pre.setAcepted(prelogin.isAcepted());
		pre.setBase(prelogin.getBase());
		pre.setMsj(prelogin.getMsj());
		pre.setSchool(prelogin.getSchool());
		pre.setEndPoint(prelogin.getEndPoint());
		pre.setEmail(prelogin.getEmail());
		pre.setCatalogoBD(prelogin.getCatalogoBD());
		pre.setUsrBD(prelogin.getUsrBD());
		pre.setPassBD(prelogin.getPassBD());
		pre.setInstanciaBD(prelogin.getInstanciaBD());
		pre.setPuertoBD(prelogin.getPuertoBD());
		pre.setContextoWeb(prelogin.getContextoWeb());
		
	}
	
	
	
	
}
