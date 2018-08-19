package com.avior.academic.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.Calificaciones;
import com.avior.academic.pojo.PreLogin;
import com.avior.academic.pojo.ReporteTutor;


@Repository("calificacionesDataImp")
public class CalificacionesDataImp implements CalificacionesData {
	
	private final static Logger logger = Logger.getLogger(CalificacionesDataImp.class);
	
//	private final static String context = "http://cem.academiccontrol.mx/";
	private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private PreLogin pre;
		
//	private DataSource dataSource;	
//
//	@Autowired
//	protected JdbcTemplate jdbcT;
//
//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//		this.jdbcT = new JdbcTemplate(this.dataSource);
//	}


	@Override
	public List<ReporteTutor> getReporteTutor(String idAlumno) throws SQLException {
		
		List<ReporteTutor> ret = new ArrayList<ReporteTutor>();
		String sqlQuery = "Exec "+pre.getBase()+".dbo.App_GetReporteXUserTutor ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setString(1, idAlumno);
			rs = call.executeQuery();
			ReporteTutor reporte= null;
			while(rs.next()){
				String wLurl = rs.getString("WLurl");
				reporte = new ReporteTutor();
				reporte.setIDWLink(rs.getString("IDWLink"));
				reporte.setWLurl(pre.getContextoWeb()+wLurl.substring(2,wLurl.length()));
				reporte.setWLFecha(df.format(rs.getDate("WLFecha")));
				reporte.setIDAlumno(rs.getString("IDAlumno"));
				reporte.setIDWReport(rs.getString("IDWReport"));
				reporte.setBoletaNombre(rs.getString("BoletaNombre"));
				ret.add(reporte);
			}
			
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		return ret;
		 
	}
	

	@Override
	public List<ReporteTutor> getReporteUserAlumno(String idAlumno)
			throws SQLException {
		List<ReporteTutor> ret = new ArrayList<ReporteTutor>();
		String sqlQuery = "Exec "+pre.getBase()+".dbo.App_GetReporteXUserAlumno ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setString(1, idAlumno);
			rs = call.executeQuery();
			ReporteTutor reporte;
			while(rs.next()){
				String wLurl = rs.getString("WLurl");				
				reporte = new ReporteTutor();
				reporte.setIDWLink(rs.getString("IDWLink"));
				reporte.setWLurl(pre.getContextoWeb()+wLurl.substring(2,wLurl.length()));
				reporte.setWLFecha(df.format(rs.getDate("WLFecha")));
				reporte.setIDAlumno(rs.getString("IDAlumno"));
				reporte.setIDWReport(rs.getString("IDWReport"));
				reporte.setBoletaNombre(rs.getString("BoletaNombre"));
				ret.add(reporte);
			}
			
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		return ret;
	}
	
	
	@Override
	public List<String> getPeriodos(String idAlumno) throws SQLException {
		
		List<String> ret = new ArrayList<String>();
		String sqlQuery = "Exec "+pre.getBase()+".dbo.App_Periodos ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setString(1, idAlumno);
			rs = call.executeQuery();
			
			while(rs.next()){
				
				ret.add(rs.getString("PNumber"));
			}
			
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		return ret;	
	}


	@Override
	public List<Calificaciones> getCalificacionesXMatriculaXPeriodo(
			String matricula, int periodo) throws SQLException {
		List<Calificaciones> ret = new ArrayList<Calificaciones>();
		String sqlQuery = "Exec "+pre.getBase()+".dbo.App_Calificaciones ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setInt(1, periodo);
			call.setString(2, matricula);
			rs = call.executeQuery();
			Calificaciones cal;
			while(rs.next()){
				cal = new Calificaciones();
				cal.setAlumno(rs.getString("Alumno"));
				cal.setCalif(rs.getString("Calif"));
				cal.setClave(rs.getString("Clave"));
				cal.setClaveMat(rs.getString("ClaveMat"));
				cal.setGradoX(rs.getString("GradoX"));
				cal.setmNombreC(rs.getString("MNombreC"));
				cal.setNivel(rs.getString("Nivel"));
				cal.setpNumber(rs.getString("PNumber"));
				ret.add(cal);
			}
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		return ret;
	}

}
