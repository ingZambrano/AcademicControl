package com.avior.academic.dao;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.Avisos;
import com.avior.academic.pojo.AvisosDet;

public interface AvisosData {
	
	public List<Avisos> getAvisosXuser(String idUser, String desde, String hasta) throws SQLException;
	public AvisosDet getDetAviso(long idAviso) throws SQLException;
	public String getTxtPlanoDetalle(String userName, long idAviso) throws SQLException;
	public boolean actualizarNotificacion(String idUser, long idAviso) throws SQLException;
	public boolean insertarNotificacion(String idUser, long idAviso) throws SQLException;
	

}
