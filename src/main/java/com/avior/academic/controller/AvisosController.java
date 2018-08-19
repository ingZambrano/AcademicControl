package com.avior.academic.controller;

import com.avior.academic.comunes.HtmlUtils;

import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

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

import com.avior.academic.comunes.GeneradorFechas;
import com.avior.academic.pojo.Avisos;
import com.avior.academic.pojo.AvisosDet;
import com.avior.academic.pojo.Header;
import com.avior.academic.pojo.ResponseAvior;
import com.avior.academic.service.AvisosService;
import com.avior.academic.service.SesionXTokenService;
import com.avior.academic.service.UserDataService;

/**
 * 
 * @author jozambrano
 *
 */
@RestController
public class AvisosController {
	
	private static final Logger logger = Logger.getLogger(AvisosController.class);
	
	@Autowired
	@Qualifier("avisosServiceImp")
	AvisosService avisosServiceImp;
	
	@Autowired
	@Qualifier("userDataServiceImp")
	UserDataService userDataServiceImp;	
	
	@Autowired
	@Qualifier("sesionXTokenService")
	SesionXTokenService sesionXTokenService;
	
	/**
	 * 
	 * @param authentication
	 * @return
	 */
	@ApiOperation(value="getAvisosXUsuario", notes="Obtiene un arreglo con los avisos, de una semana previa y dos después, del usuario actualmente loggeado.")
	@RequestMapping(value = "/getAvisosXUsuario", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseAvior<List<Avisos>> getAvisosXUsuario(Authentication authentication,
			@RequestParam(required = false) String token){
		ResponseAvior<List<Avisos>>  ret = null;
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}
		 
		if(authentication == null){
			List<Avisos> avisos = new ArrayList<Avisos>();
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			ret.setResponsData(avisos);
			return ret;
		}
		
		
		String desde = GeneradorFechas.generarFechaDesde();
		String hasta = GeneradorFechas.generarFechaHasta();		
		
		try{
			String  idUser = userDataServiceImp.getUserIdByUserName(authentication.getName());			
			if(idUser == null){
				logger.error("No se encontro el id del usuario: "+authentication.getName());
				ret = new ResponseAvior<>(Header.getHeader("3333"));
				return ret;				
			}
			List<Avisos> avisos = avisosServiceImp.getAvisosXuser(idUser, desde, hasta);
			if(avisos.isEmpty() || avisos == null){
				logger.info("No hay avisos para mostrar para el usuario: "+idUser+" entre las fechas: "+desde+" y hasta: "+hasta);
			}
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			ret.setResponsData(avisos);			
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			ret = new ResponseAvior<>(Header.getHeader("4444"));
		}		
		return ret;
	}
	
	/**
	 * 
	 * @param authentication
	 * @param desde
	 * @param hasta
	 * @return
	 */
	@ApiOperation(value="getAvisosXUsuarioXFecha", notes="Obtiene un arreglo con los avisos, del usuario actualmente loggeado, de acuerdo a las fechas enviadas")
	@RequestMapping(value = "/getAvisosXUsuarioXFecha", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseAvior<List<Avisos>> getAvisosXUsuarioXFecha(
			Authentication authentication,
			@RequestParam(required = true) String desde,
			@RequestParam(required = true) String hasta,
			@RequestParam(required = false) String token){
		
		ResponseAvior<List<Avisos>>  ret = null;
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}		
		
		if(authentication == null){
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			List<Avisos> avisos = new ArrayList<Avisos>();
			ret.setResponsData(avisos);
		}else{
		
		
			try{
				String  idUser = userDataServiceImp.getUserIdByUserName(authentication.getName());			
				if(idUser == null){
					logger.error("No se encontro el id del usuario: "+authentication.getName());
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;				
				}			
				List<Avisos> avisos = avisosServiceImp.getAvisosXuser(idUser, desde, hasta);
				if(avisos.isEmpty() || avisos == null){
					logger.info("No hay avisos para mostrar para el usuario: "+idUser+" entre las fechas: "+desde+" y hasta: "+hasta);
				}
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(avisos);			
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				ret = new ResponseAvior<>(Header.getHeader("4444"));
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param authentication
	 * @param idAviso
	 * @return
	 */
	@ApiOperation(value="getDetalleAviso", notes="Obtiene el detalle del aviso seleccionado.")
	@RequestMapping(value="/getDetalleAviso", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseAvior<AvisosDet> getDetalleAviso(Authentication authentication,
			@RequestParam(required = true) long idAviso,
			@RequestParam(required = false) String token){
		
		ResponseAvior<AvisosDet> ret = null;
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}	
		
		if(authentication == null){
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			AvisosDet avisosDet = new AvisosDet();
			ret.setResponsData(avisosDet);
			
			
		}else{
			
			
			
			try{
				String idUser = userDataServiceImp.getUserIdByUserName(authentication.getName());			
				if(idUser == null){
					logger.error("No se encontro el id del usuario: "+authentication.getName());
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;				
				}
				String nameUser = authentication.getName();
				AvisosDet avisosDet = avisosServiceImp.getDetAviso(idUser,nameUser, idAviso);
				if(avisosDet == null){
					logger.error("No se encontro el detalle del aviso con id: "+idAviso);				
				}
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				String cleanHTML = HtmlUtils.limpiaTags(avisosDet.getDescripcion());
				avisosDet.setDescripcion(cleanHTML);
				ret.setResponsData(avisosDet);
				
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
			
		}
		return ret;		
	}

}
