package com.avior.academic.service;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.avior.academic.dao.UserData;
import com.avior.academic.pojo.PreLogin;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {
	
	private static final Logger logger = Logger.getLogger(AuthenticationService.class);
	
	@Autowired
	@Qualifier("loginServiceImpl")
	LoginService loginServiceImpl;
	
	@Autowired
	@Qualifier("userDataImp")
	UserData userDataImp;
	
	@Autowired
	@Qualifier("preLoginServiceImp")
	PreLoginService preLoginServiceImp;
	
	@Autowired
	private PreLogin pre;
	

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String usrName = authentication.getName();
		String password = authentication.getCredentials().toString();
		int idEscuela = -1;
		String eMail = pre.getEmail();
		//Validamos que exista el atributo idEscuela
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		idEscuela = Integer.parseInt((request.getParameter("idEscuela") == null) || (request.getParameter("idEscuela") == "")  
				? "-1" : request.getParameter("idEscuela"));
		
		if(idEscuela != -1){
			//Creamos el objeto PreLogin
			PreLogin prelogin = null;
			try {
				prelogin = preLoginServiceImp.getPreloginObjectSingle(eMail, idEscuela);
			} catch (SQLException e) {
				logger.error("Error al crear el objeto Prelogin: ",e);
			}
			
			if(prelogin != null){
				//Seteamos los datos para el objecto inyectado PreLogin que será utilizado en todos los servicios DAO
				/*********************************************/
				pre.setAcepted(prelogin.isAcepted());
				pre.setBase(prelogin.getBase());
				pre.setMsj(prelogin.getMsj());
				pre.setSchool(prelogin.getSchool());
				pre.setEndPoint(prelogin.getEndPoint());
				pre.setEmail(eMail);
				pre.setCatalogoBD(prelogin.getCatalogoBD());
				pre.setUsrBD(prelogin.getUsrBD());
				pre.setPassBD(prelogin.getPassBD());
				pre.setInstanciaBD(prelogin.getInstanciaBD());
				pre.setPuertoBD(prelogin.getPuertoBD());
				pre.setContextoWeb(prelogin.getContextoWeb());
				/*********************************************/
			}
		}
		
		
		//Validar que mail y idUsuario sean del mismo usuario
		String usrId = null;
		try{
			usrId = userDataImp.getUserIdByUserName(usrName);
			boolean esUsrMail = loginServiceImpl.validaMailUser(pre.getEmail(), usrId);
			if(esUsrMail){
				logger.info("Usuario y mail coinciden");
			}else{
				logger.info("No se encontró coincidencia entre usuario y mail");
				return null;
			}
		}catch(Exception e){
			logger.error("Error al validar usuario y mail", e);
			return null;
		}
		if(loginServiceImpl.getLogin(pre.getEndPoint(), usrName, password)){
			
			Authentication auth = new UsernamePasswordAuthenticationToken(usrName, password, new ArrayList<>());
			
			return auth;
		}else{
			return null;
		}
	}
	
	

}
