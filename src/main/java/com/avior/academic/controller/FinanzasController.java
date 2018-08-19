package com.avior.academic.controller;

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

import com.avior.academic.pojo.CnsCobAbonos;
import com.avior.academic.pojo.CobroCargosXAlumno;
import com.avior.academic.pojo.Header;
import com.avior.academic.pojo.ResponseAvior;
import com.avior.academic.service.FinanzasService;
import com.avior.academic.service.SesionXTokenService;

/**
 * 
 * @author jozambrano
 *
 */
@RestController
public class FinanzasController {

	private static final Logger logger = Logger.getLogger(FinanzasController.class);

	@Autowired
	@Qualifier("finanzasServiceImp")
	FinanzasService finanzasServiceImp;
	
	@Autowired
	@Qualifier("sesionXTokenService")
	SesionXTokenService sesionXTokenService;
	
	/**
	 * 
	 * @param idAlumno
	 * @return
	 */
	@ApiOperation(value="getFinanzasXAlumno", notes="Obtiene un arreglo con las finanzas del alumno seleccionado")
	@RequestMapping(value = "/getFinanzasXAlumno", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseAvior<List<CobroCargosXAlumno>> getFinanzasXAlumno(
			Authentication authentication,
			@RequestParam(required = true) long idAlumno,
			@RequestParam(required = false) String token){
		ResponseAvior<List<CobroCargosXAlumno>> ret = null;
		if(token != null){
//			sesionXTokenService.obtenerSesionXToken(token);
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}
		if(authentication == null){
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			List<CobroCargosXAlumno> cobroCargos = new ArrayList<CobroCargosXAlumno>();
			ret.setResponsData(cobroCargos);
		}else{

				
			try{		
				
				List<CobroCargosXAlumno> cobroCargos = finanzasServiceImp.getFinanzasXAlumno(idAlumno);
				if (cobroCargos.isEmpty() || cobroCargos == null) {
					logger.info("No hay registros de finanzas para el alumno seleccionado: "+idAlumno);
				} 
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(cobroCargos);			
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
	 * @param idCargo
	 * @return
	 */
	@ApiOperation(value="getDetalleMonto", notes="Obtiene el detalle del monto seleccionado por idCargo.")
	@RequestMapping(value = "/getDetalleMonto", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseAvior<CnsCobAbonos> getDetalleMonto(
			Authentication authentication, @RequestParam(required = true) long idCargo,
			@RequestParam(required = false) String token){
		
		ResponseAvior<CnsCobAbonos> ret = null;	
		
		if(token != null){
			authentication = sesionXTokenService.obtenerAuthentication(token);
			
		}	
		
		if(authentication == null){
			ret = new ResponseAvior<>(Header.getHeader("1111"));
			CnsCobAbonos abonos = new CnsCobAbonos();
			ret.setResponsData(abonos);
		}else{		
			
			try{
				CnsCobAbonos abonos = finanzasServiceImp.getDetalleMonto(idCargo);
				if(abonos == null){
					logger.info("No se encontro el detalle para el idCargo: "+idCargo);
				}
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(abonos);
						
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
			
		}
		
		return ret;
	}

}
