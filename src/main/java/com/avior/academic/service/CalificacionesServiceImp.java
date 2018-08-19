package com.avior.academic.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avior.academic.dao.CalificacionesData;
import com.avior.academic.pojo.Calificaciones;
import com.avior.academic.pojo.ReporteTutor;

@Service("calificacionesServiceImp")
public class CalificacionesServiceImp implements CalificacionesService {
	
	@Autowired
	@Qualifier("calificacionesDataImp")
	CalificacionesData calificacionesDataImp;

	@Override
	public List<ReporteTutor> getReporteTutor(String idAlumno)
			throws SQLException {
		List<ReporteTutor> ret = new ArrayList<ReporteTutor>();
		for(ReporteTutor r: calificacionesDataImp.getReporteTutor(idAlumno)){			
			ret.add((ReporteTutor) ServicioGenerico.changeNullValues(r));			
		}
		
		return ret;
	}

	@Override
	public List<ReporteTutor> getReporteUserAlumno(String idAlumno)
			throws SQLException {
		List<ReporteTutor> ret = new ArrayList<ReporteTutor>();
		for(ReporteTutor r: calificacionesDataImp.getReporteUserAlumno(idAlumno)){			
			ret.add((ReporteTutor) ServicioGenerico.changeNullValues(r));			
		}
		
		return ret;
	}

	@Override
	public List<String> getPeriodos(String idAlumno) throws SQLException {
		
		return calificacionesDataImp.getPeriodos(idAlumno);
	}

	@Override
	public List<Calificaciones> getCalificacionesXMatriculaXPeriodo(
			String matricula, int periodo) throws SQLException {
		List<Calificaciones> ret = new ArrayList<Calificaciones>();
		for(Calificaciones c: calificacionesDataImp.getCalificacionesXMatriculaXPeriodo(matricula, periodo)){
			ret.add((Calificaciones)ServicioGenerico.changeNullValues(c));
		}
		
		
		return ret;
	}

}
