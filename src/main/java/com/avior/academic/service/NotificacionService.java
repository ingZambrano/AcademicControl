package com.avior.academic.service;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.Notificacion;
import com.avior.academic.pojo.PreLogin;

public interface NotificacionService {
	
	public Notificacion getNotificacion(String tipo, long id, PreLogin pre) throws SQLException;
	public List<String> getTokensUsuariosNoti(String tipo, long id, PreLogin pre) throws SQLException;
	public PreLogin createPrelogin(long idEscuela) throws SQLException;

}
