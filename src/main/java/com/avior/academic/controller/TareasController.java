package com.avior.academic.controller;

import java.util.ArrayList;
import java.util.List;

import com.avior.academic.comunes.HtmlUtils;

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
import com.avior.academic.pojo.Header;
import com.avior.academic.pojo.PreLogin;
import com.avior.academic.pojo.ResponseAvior;
import com.avior.academic.pojo.Tarea;
import com.avior.academic.pojo.TareaContesta;
import com.avior.academic.pojo.TareaMateriaGrupo;
import com.avior.academic.pojo.UserInfo;
import com.avior.academic.service.SesionXTokenService;
import com.avior.academic.service.TareasService;
import com.avior.academic.service.UserDataService;

/**
 * 
 * @author jozambrano
 *
 */

@RestController
public class TareasController {
	private static final Logger logger = Logger.getLogger(TareasController.class);

//    private final String context = "http://cem.academiccontrol.mx";


	@Autowired
	@Qualifier("tareasServiceImpl")
	TareasService tareasServiceImpl;
	
	@Autowired
	@Qualifier("userDataServiceImp")
	UserDataService userDataServiceImp;	
	
	@Autowired
	@Qualifier("sesionXTokenService")
	SesionXTokenService sesionXTokenService;
	
	@Autowired
	private PreLogin pre;
	
	private long idALumno = 0;

	@RequestMapping(value="/getTareasXMateria", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<List<TareaMateriaGrupo>> getTareas(
			@RequestParam(required = false) String desde,
			@RequestParam(required = false) String hasta,
			@RequestParam(required = true) long idAlumno,
			Authentication authentication,
			@RequestParam(required = false) String token){
		
		this.idALumno = idAlumno;
		ResponseAvior<List<TareaMateriaGrupo>> ret = null;
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}	
		if(authentication == null){
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			List<TareaMateriaGrupo> listTareas = new ArrayList<TareaMateriaGrupo>();
			ret.setResponsData(listTareas);
		}else{
			
		
				
			if(desde == null || hasta == null){			
				desde = GeneradorFechas.generarFechaDesde();			
				hasta = GeneradorFechas.generarFechaHasta();			
			}	
			try{
				String  idUser = userDataServiceImp.getUserIdByUserName(authentication.getName());			
				if(idUser == null){
					logger.error("No se encontro el id del usuario: "+authentication.getName());
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;				
				}
				List<TareaMateriaGrupo> listTareas = tareasServiceImpl.getTareasXMateriaT(desde, hasta, idAlumno, idUser);
				if(listTareas.isEmpty() || listTareas == null){
					logger.info("No se encontraron tareas para el rango de fechas");
				}
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(listTareas);
				
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				ret = new ResponseAvior<>(Header.getHeader("4444"));
			}		
		}			
		return ret;
		
	}
	
	@RequestMapping(value="/getTareasDetalle", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<Tarea> getTareasDetalle(
			@RequestParam(required = true) long idTarea,
			Authentication authentication,
			@RequestParam(required = false) String token){
		
		ResponseAvior<Tarea> ret = null;
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}	

		if(authentication == null){
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			Tarea tarea = new Tarea();
			ret.setResponsData(tarea);
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
					logger.error("No se encontro informacion del usuario con id: "+idUser);
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;
				}
			
				Tarea tarea = tareasServiceImpl.getTareaXIdDet(idTarea, idUser, userInfo.getRole(), this.idALumno);
				String htmlClean = HtmlUtils.limpiaTags(tarea.gettDescripcionT());
				tarea.settDescripcionT(htmlClean);
	            	
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(tarea);
				
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				ret = new ResponseAvior<>(Header.getHeader("4444"));
			}
		}
		return ret;
	}
	
	
	@RequestMapping(value="/getComentarios", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<TareaContesta> getComentarios(
			@RequestParam(required = true) long idTarea,
			Authentication authentication,
			@RequestParam(required = false) String token){
		
		ResponseAvior<TareaContesta> ret = null;
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}	

		if(authentication == null){
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			TareaContesta tareaContesta = new TareaContesta();
			ret.setResponsData(tareaContesta);
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
					logger.error("No se encontro informacion del usuario con id: "+idUser);
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;
				}
				
				TareaContesta tareaContesta = tareasServiceImpl.getComentarios(idTarea, idUser, userInfo.getRole());
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(tareaContesta);
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				ret = new ResponseAvior<>(Header.getHeader("4444"));
			}
		}
			return ret;
		
	}
	
	
	@RequestMapping(value="/updateComentario", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<Boolean> updateComentario(
			@RequestParam(required = true) long idTarea,
			@RequestParam(required = true) String comentario,
			@RequestParam(required = false) String comentarioDetalle,
			Authentication authentication,
			@RequestParam(required = false) String token){
		
		ResponseAvior<Boolean> ret = null;
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}	

		if(authentication == null){
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			ret.setResponsData(false);
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
					logger.error("No se encontro informacion del usuario con id: "+idUser);
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;
				}
	
				TareaContesta tareaContesta = tareasServiceImpl.getComentarios(idTarea, idUser, userInfo.getRole());
	            logger.info("El idTareaA es " + tareaContesta.getIdTareaA());
				Boolean bool = tareasServiceImpl.updateComentario(comentario, idUser, tareaContesta.getIdTareaA(), comentarioDetalle);
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(bool);
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				ret = new ResponseAvior<>(Header.getHeader("4444"));
			}
		}
		return ret;
		
	}


}
