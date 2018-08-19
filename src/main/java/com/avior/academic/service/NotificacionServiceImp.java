package com.avior.academic.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avior.academic.dao.NotificacionesData;
import com.avior.academic.dao.PreloginDataImp;
import com.avior.academic.pojo.Notificacion;
import com.avior.academic.pojo.PreLogin;

@Service("notificacionServiceImp")
public class NotificacionServiceImp implements NotificacionService {

	private Logger log = LoggerFactory.getLogger(this.getClass());


	@Autowired
	@Qualifier("notificacionesDataImp")
	NotificacionesData notificacionesDataImp;
	
	@Autowired
	@Qualifier("preloginDataImp")
	PreloginDataImp preloginDataImp;

	@Override
	public Notificacion getNotificacion(String tipo, long id, PreLogin pre)
			throws SQLException {
		
		return notificacionesDataImp.getNotificacion(tipo, id, pre);
	}

	@Override
	public List<String> getTokensUsuariosNoti(String tipo, long id, PreLogin pre)
			throws SQLException {

		List<String> listaEmails = notificacionesDataImp.getEmailUsuariosNoti(tipo, id, pre);


		StringBuffer sb = new StringBuffer();
		for(String s : listaEmails){
			sb.append(s);
			sb.append(", ");
		}
		log.debug("Destinatarios: " + sb.toString());

		return preloginDataImp.getTokensUsuariosNoti(listaEmails, pre.getSchool().getIdEscuela());
	}

	@Override
	public PreLogin createPrelogin(long idEscuela) throws SQLException {
		
		return preloginDataImp.createPrelogin(idEscuela);
	}

}
