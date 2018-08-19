package com.avior.academic.service;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.Avisos;
import com.avior.academic.pojo.AvisosDet;

public interface AvisosService {
	
	public List<Avisos> getAvisosXuser(String idUser, String desde, String hasta) throws SQLException;
	public AvisosDet getDetAviso(String idUser, String nameUser, long idAviso) throws SQLException;
	
}
