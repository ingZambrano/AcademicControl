package com.avior.academic.controller;

import io.swagger.annotations.ApiOperation;

import java.sql.SQLException;
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

import com.avior.academic.pojo.Calificaciones;
import com.avior.academic.pojo.Header;
import com.avior.academic.pojo.ReporteTutor;
import com.avior.academic.pojo.ResponseAvior;
import com.avior.academic.pojo.UserInfo;
import com.avior.academic.service.CalificacionesService;
import com.avior.academic.service.SesionXTokenService;
import com.avior.academic.service.UserDataService;

/**
 * 
 * @author jozambrano
 *
 */

@RestController
public class CalificacionesController {

	private static final Logger logger = Logger.getLogger(CalificacionesController.class);
	
	@Autowired
	@Qualifier("calificacionesServiceImp")
	CalificacionesService calificacionesServiceImp;
	
	
	@Autowired
	@Qualifier("userDataServiceImp")
	UserDataService userDataServiceImp;	
	
	@Autowired
	@Qualifier("sesionXTokenService")
	SesionXTokenService sesionXTokenService;
	
	/**
	 * 
	 * @param idAlumno
	 * @return
	 */
	@ApiOperation(value="getReporteTutor", notes="Obtiene un arreglo con el reporte de calificaciones del hijo del usuario actualmente loggeado.")
	@RequestMapping(value = "/getReporteTutor", method = RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<List<ReporteTutor>> getReporteTutor(
			@RequestParam(required = true) String idAlumno,
			Authentication authentication,
			@RequestParam(required = false) String token){
		ResponseAvior<List<ReporteTutor>> ret = null;
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}	
		
		if(authentication == null){
			//EL usuario cambio de eMail
			List<ReporteTutor> listRep = new ArrayList<ReporteTutor>();
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			ret.setResponsData(listRep);
		}else{
			try{
				String idUser = userDataServiceImp.getUserIdByUserName(authentication.getName());
				if(idUser == null){
					logger.error("No se encontro el id del usuario: "+authentication.getName());
					ret = new ResponseAvior<>(Header.getHeader("3333"));
					return ret;				
				}
				UserInfo userInfo = userDataServiceImp.getUserbyId(idUser);			
				if(userInfo == null){
					logger.error("No se encontraron datos del usuario con id: "+idUser);
					ret = new ResponseAvior<>(Header.getHeader("3333"));				
					return ret;				
				}	
				List<ReporteTutor> reporteTutor;
				if(userInfo.getRole().equals("Alumno")){
					reporteTutor = calificacionesServiceImp.getReporteUserAlumno(idUser);		
				}else{
					reporteTutor = calificacionesServiceImp.getReporteTutor(idAlumno);	
					
				}
				
				if(reporteTutor.isEmpty() || reporteTutor == null){
					logger.info("No existen calificaciones asociadas al alumno: "+idAlumno);
				}
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(reporteTutor);			
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				ret = new ResponseAvior<>(Header.getHeader("4444"));
			}
		}
		return ret;
	}



	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	@ApiOperation(value="getPeriodos", notes="Obtiene un arreglo de los periodos escolares.")
	@RequestMapping(value="/getPeriodos", method = RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<List<String>> getPeriodos(
			Authentication authentication,
			@RequestParam(required = false) String idAlumno,
			@RequestParam(required = false) String token)throws SQLException{		
		
		ResponseAvior<List<String>> ret = null;
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}
		
		if(authentication == null){
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			List<String> periodos = new ArrayList<String>();
			periodos.add("0");
			ret.setResponsData(periodos);
		}else{
		
			try{
				if(idAlumno == null){
					idAlumno = "467";
				}
				
				List<String> periodos = calificacionesServiceImp.getPeriodos(idAlumno);
				if(periodos.isEmpty() || periodos == null){
					logger.info("No hay periodos para mostrar");
				}
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(periodos);
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				ret = new ResponseAvior<>(Header.getHeader("4444"));
			}
			
		}
		return ret;
	}

	
	/**
	 * 
	 * @param periodo
	 * @param matricula
	 * @return
	 */
	@ApiOperation(value="getCalificaciones", notes="Obtiene el arreglo de calificaciones correspondiente al periodo y matricula de alumno enviado.")
	@RequestMapping(value="/getCalificaciones", method = RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseAvior<List<Calificaciones>> getCalificaciones(
			Authentication authentication,
			@RequestParam(required = true) int periodo,
			@RequestParam(required = true) String matricula,
			@RequestParam(required = false) String token){
		
		ResponseAvior<List<Calificaciones>> ret = null;
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}	
		
		if(authentication == null){
			List<Calificaciones> calif = new ArrayList<Calificaciones>();
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			ret.setResponsData(calif);
		}else{
			
			try{
				List<Calificaciones> calif = calificacionesServiceImp.getCalificacionesXMatriculaXPeriodo(matricula, periodo);
				if(calif.isEmpty() || calif == null){
					logger.info("No se encontraron calificaciones para el periodo y matricula: "+periodo+" "+matricula);
				}
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(calif);
			}catch(Exception e){
				logger.error(e.getMessage(),e);			
				ret = new ResponseAvior<>(Header.getHeader("4444"));
			}
			
		}
		return ret;

	}

}
