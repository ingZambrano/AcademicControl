package com.avior.academic.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.avior.academic.pojo.Header;
import com.avior.academic.pojo.PreLogin;
import com.avior.academic.pojo.ResponseAvior;
import com.avior.academic.pojo.TokenLogin;
import com.avior.academic.service.FirebaseMessaging;
import com.avior.academic.service.PreLoginService;
import com.avior.academic.service.SesionXTokenService;


@RestController
public class LoginTokenController {
	
	Logger logger = Logger.getLogger(LoginTokenController.class);
	
	@Autowired
	@Qualifier("preLoginServiceImp")
	PreLoginService preLoginServiceImp;
	
	@Autowired
	@Qualifier("sesionXTokenService")
	SesionXTokenService sesionXTokenService;
	
	@Autowired
	@Qualifier("firebaseCloudMessaging")
	FirebaseMessaging messaging;

	@Autowired
	private PreLogin pre;
	
	@RequestMapping(value = "/loginToken", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	private @ResponseBody ResponseAvior<TokenLogin> loginToken(@RequestParam(required = true) String token) {
		logger.info("Acceso al sistema a traves del token: "+token);
		
		ResponseAvior<TokenLogin> ret = null;
		TokenLogin tokenLogin = sesionXTokenService.obtenerSesionXToken(token);
		if(tokenLogin == null){
			//Posiblemente el usuario cambio de eMail
//			ret = new ResponseAvior<>(Header.getHeader("4444"));
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			tokenLogin = sesionXTokenService.dameTokenLoginDumie(token);
			ret.setResponsData(tokenLogin);
			//enviar notificacion indicando que cierre sesión vuela abrir
		
		}else{
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			ret.setResponsData(tokenLogin);
		}
		return ret;

	}
	
	
	

}
