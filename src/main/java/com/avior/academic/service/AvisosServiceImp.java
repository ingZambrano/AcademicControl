package com.avior.academic.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avior.academic.dao.AvisosData;
import com.avior.academic.pojo.Avisos;
import com.avior.academic.pojo.AvisosDet;
import com.avior.academic.pojo.PreLogin;

@Service("avisosServiceImp")
public class AvisosServiceImp implements AvisosService {
	
	private static final Logger logger = Logger.getLogger(AvisosServiceImp.class);
	
	@Autowired
	@Qualifier("avisosDataImp")
	AvisosData avisosDataImp;
	
	@Autowired
	private PreLogin pre;
	
//	private final static String context = "http://cem.academiccontrol.mx/";
	
	private List<Avisos> avisosGlobal;
	
	@Override
	public List<Avisos> getAvisosXuser(String idUser, String desde, String hasta) throws SQLException {
		
		List<Avisos> ret = new ArrayList<Avisos>();
		for(Avisos a : avisosDataImp.getAvisosXuser(idUser, desde, hasta)){
			if(a.getStatus().equals("statvenc")){
				a.setStatus("VENCIDA");
				a.setStatusInt(1);
			}else if(a.getStatus().equals("statsinv")){
				a.setStatus("PENDIENTE");
				a.setStatusInt(2);
			}else if(a.getStatus().equals("statvist")){
				a.setStatus("VISTA");
				a.setStatusInt(3);
			}else if(a.getStatus().equals("statvive")){
				a.setStatus("VISTA VENCIDA");
				a.setStatusInt(4);
			}else{
				a.setStatus("VENCIDA");
				a.setStatusInt(1);
			}
			
			ret.add((Avisos)ServicioGenerico.changeNullValues(a));
		}
		this.avisosGlobal = ret;
		return ret;
	}

	@Override
	public AvisosDet getDetAviso(String idUser, String nameUser, long idAviso) throws SQLException {
		
		AvisosDet ret = avisosDataImp.getDetAviso(idAviso);
		String txtPlano = avisosDataImp.getTxtPlanoDetalle(nameUser, idAviso);
//		String html = ret.getDescripcion();
		ret.setDescripcion(txtPlano);
		ret.setHtmlString(txtPlano);
		if(txtPlano != null){
			if(txtPlano.contains("href")){			
				ret.setTieneLinkDescarga(true);
//				ret.setLinkDescarga(HtmlUtils.getURLRelativa(txtPlano));
				ret.setLinkDescarga("");
			}else{			
				ret.setTieneLinkDescarga(false);
				ret.setLinkDescarga("");
			}
		}
		
		//Se actualiza el estatus de la circular
		Avisos avisoSelected = null;
		for(Avisos av: avisosGlobal){
			if(av.getIdCircular() == idAviso){
				avisoSelected = av;
				logger.info("Se encontro el aviso");
				break;
			}
		}
		if(avisoSelected != null){
			if(avisoSelected.getReviso() == 0){
				logger.info("Se va a insertar");
				avisosDataImp.insertarNotificacion(idUser, idAviso);
			}else{
				logger.info("Se va a actualizar");
				avisosDataImp.actualizarNotificacion(idUser, idAviso);
			}
		}
		
		return (AvisosDet)ServicioGenerico.changeNullValues(ret);
	}
}
