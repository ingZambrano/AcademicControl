package com.avior.academic.service;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.Calificaciones;
import com.avior.academic.pojo.ReporteTutor;

public interface CalificacionesService {
	
	public List<ReporteTutor> getReporteTutor(String idAlumno) throws SQLException;
	public List<ReporteTutor> getReporteUserAlumno(String idAlumno) throws SQLException;
	public List<String> getPeriodos(String idAlumno) throws SQLException;
	public List<Calificaciones> getCalificacionesXMatriculaXPeriodo(String matricula, int periodo) throws SQLException;
}
