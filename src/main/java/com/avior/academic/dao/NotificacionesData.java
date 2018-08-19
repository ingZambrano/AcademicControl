package com.avior.academic.dao;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.Notificacion;
import com.avior.academic.pojo.PreLogin;

public interface NotificacionesData {
	
	public Notificacion getNotificacion(String tipo, long id, PreLogin pre) throws SQLException;
	public List<String> getEmailUsuariosNoti(String tipo, long id, PreLogin pre) throws SQLException;


}
