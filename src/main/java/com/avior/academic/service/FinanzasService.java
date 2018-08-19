package com.avior.academic.service;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.CnsCobAbonos;
import com.avior.academic.pojo.CobroCargosXAlumno;

public interface FinanzasService {
	public List<CobroCargosXAlumno> getFinanzasXAlumno(long idAlumno) throws SQLException;
	public CnsCobAbonos getDetalleMonto(long idCargo) throws SQLException;
}
