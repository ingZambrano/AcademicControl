package com.avior.academic.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avior.academic.pojo.Header;
import com.avior.academic.pojo.ResponseAvior;
import com.avior.academic.pojo.PreLogin;
import com.avior.academic.service.PreLoginService;

@RestController
@Scope(value = "session")
public class PreLoginController {

	private static final Logger logger = Logger
			.getLogger(PreLoginController.class);

	@Autowired
	@Qualifier("preLoginServiceImp")
	PreLoginService preLoginServiceImp;

	@Autowired
	private PreLogin pre;

	/**
	 * 
	 * @param eMail
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "findMail", notes = "Obtiene los datos de conexion para la app. (Dependiente de la escuela)")
	@RequestMapping(value = "/findMail", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiImplicitParams({ @ApiImplicitParam(name = "eMail", required = true, value = "E-mail del usuario", dataType = "String", paramType = "query") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se ha encontrado la correspondencia del usuario", response = PreLogin.class),
			@ApiResponse(code = 400, message = "Error en los parámetros"),
			@ApiResponse(code = 404, message = "Se ha realizado la busqueda pero no se han encontrado los datos") })
	public ResponseEntity<ResponseAvior> requestMail(
			@RequestParam(value = "eMail", required = true) String eMail) {

		logger.info("Solicitud de entrada al sistema con el mail: " + eMail);

		ResponseAvior ret = null;
		try {
			List<PreLogin> preloginList = preLoginServiceImp.getPreloginObject(eMail);
			PreLogin prelogin = null;
			if (preloginList.isEmpty() || preloginList == null 	|| preloginList.size() == 0) {
				ret = new ResponseAvior<>(Header.getHeader("3333"));
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ret);
			} else if (preloginList.size() == 1) {
				prelogin = preloginList.get(0);
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(prelogin);
				
				
			} else {
//				prelogin = preloginList.get(0);
				ret = new ResponseAvior<>(Header.getHeader("1111"));
				ret.setResponsData(preloginList);
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
			}else{
				pre.setEmail(eMail);
			}
			logger.info("Acceso al usuario con mail: " + eMail);
			return ResponseEntity.ok(ret);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			ret = new ResponseAvior<>(Header.getHeader("4444"));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ret);
		}

	}

}
