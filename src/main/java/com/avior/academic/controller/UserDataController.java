package com.avior.academic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.avior.academic.pojo.Header;
import com.avior.academic.pojo.PreLogin;
import com.avior.academic.pojo.ResponseAvior;
import com.avior.academic.pojo.UserInfo;
import com.avior.academic.pojo.UserPadreHijo;
import com.avior.academic.service.LoginService;
import com.avior.academic.service.SesionXTokenService;
import com.avior.academic.service.UserDataService;
/**
 * 
 * @author Jose Manuel
 *
 */
@RestController
public class UserDataController {
	
	private static final Logger logger = Logger.getLogger(UserDataController.class);
	
	@Autowired
	@Qualifier("userDataServiceImp")
	UserDataService userDataServiceImp;	
	
	@Autowired
	@Qualifier("sesionXTokenService")
	SesionXTokenService sesionXTokenService;
	
	@Autowired
	@Qualifier("loginServiceImpl")
	LoginService loginServiceImpl;
	
	@Autowired
	private PreLogin pre;
	
	/**
	 * 
	 * 
	 * @return
	 * @throws SQLException
	 */
	
	@ApiOperation(value="getUserData", notes="Obtiene la información relacionada con el usuario actualmente loggeado.")
	@RequestMapping(value = "/getUserData", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<UserInfo> getUserDataById(Authentication authentication,
			@RequestParam(required = false) String token){
		ResponseAvior<UserInfo> ret = null;
		logger.debug(token);
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);			
		}	
		
		logger.debug(authentication);
		
		if(authentication == null){
			//Es posible que el usuario haya cambiado su mail
			//Se crean datos para el usuario dummie
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			UserInfo user = new UserInfo(true);
			ret.setResponsData(user);	
			return ret;
			
		}else{	
			
			try{			
				String idUser = userDataServiceImp.getUserIdByUserName(authentication.getName());
				if(idUser == null){
					logger.error("No se encontro el id del usuario: "+authentication.getName());
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;				
				}
				UserInfo user = userDataServiceImp.getUserbyId(idUser);			
				if(user == null){
					logger.error("No se encontraron datos del usuario con id: "+idUser);
					ret = new ResponseAvior<>(Header.getHeader("3333"));				
					return ret;				
				}			
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				logger.info("Token("+authentication.getName()+", " +
				               user.geteMail() + ", " + (int)pre.getSchool().getIdEscuela() + ")");
				user.setToken(userDataServiceImp.getToken(authentication.getName(),user.geteMail(),(int)pre.getSchool().getIdEscuela()));
				logger.info(user.getToken());
				ret.setResponsData(user);			
			}catch(Exception e){
				ret = new ResponseAvior<>(Header.getHeader("4444"));
				logger.error(e.getMessage(),e);
			}
			return ret;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	@ApiOperation(value="getPadreHijos", notes="Obtiene un arreglo con los hijos del usuario actualmente loggeado.")
	@RequestMapping(value = "/getPadreHijos", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<List<UserPadreHijo>> getUser(Authentication authentication,
			@RequestParam(required = false) String token){
		ResponseAvior<List<UserPadreHijo>> ret = null;
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}	

		if(authentication == null){
			//Es posible que el usuario haya cambiado su mail
			//Se crean datos para el usuario dummie
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			UserPadreHijo uph = new UserPadreHijo(true); 
			
			List<UserPadreHijo> listPadreHijo = new ArrayList<UserPadreHijo>();
			listPadreHijo.add(uph);
			ret.setResponsData(listPadreHijo);	
		
		}else{
		
			try{		
				String  idUser = userDataServiceImp.getUserIdByUserName(authentication.getName());
				if(idUser == null){
					logger.error("No se encontro el id del usuario: "+authentication.getName());
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;
				}
				
				UserInfo userInfo = userDataServiceImp.getUserbyId(idUser);
				if(userInfo == null){
					logger.error("No se encontro informacion del usuario: "+authentication.getName());
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;
				}
				List<UserPadreHijo> listPadreHijo;
				if(userInfo.getRole().equals("Alumno")){
					listPadreHijo = userDataServiceImp.getInfoAlumno(idUser);				
				}else{
					listPadreHijo = userDataServiceImp.getPadresHijos(idUser);
				}
				
				if(listPadreHijo == null || listPadreHijo.isEmpty()){
					logger.error("No se encontraron datos/hijos asociados al usuario: "+idUser);
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;
				}			
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(listPadreHijo);		
			}catch(Exception e){
				ret = new ResponseAvior<>(Header.getHeader("4444"));
				logger.error(e.getMessage(),e);
			}
		}
		
		return 	ret;	
	}
	
	
	
	@RequestMapping(value = "/cambiarPass", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<String> cambiarPass(Authentication authentication,
			@RequestParam(required = false) String token,
			@RequestParam(required = true) String oldPass,
			@RequestParam(required = true) String newPass){
		
		ResponseAvior<String> ret = null;	
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);			
		}	
		if(authentication == null){
			
			//Se crea usuario dummie
			ret = new ResponseAvior<>(Header.getHeader("1111"));
		}else{
		
			
				
			try{			
				String idUser = userDataServiceImp.getUserIdByUserName(authentication.getName());
				if(idUser == null){
					logger.error("No se encontro el id del usuario: "+authentication.getName());
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;				
				}
				UserInfo user = userDataServiceImp.getUserbyId(idUser);			
				if(user == null){
					logger.error("No se encontraron datos del usuario con id: "+idUser);
					ret = new ResponseAvior<>(Header.getHeader("3333"));				
					return ret;				
				}			
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				
				ret.setResponsData(loginServiceImpl.cambiarPass(pre.getEndPoint(), oldPass, newPass, user.getNombreUser()));			
			}catch(Exception e){
				ret = new ResponseAvior<>(Header.getHeader("4444"));
				logger.error(e.getMessage(),e);
			}
		}
		return ret;
	}
}
