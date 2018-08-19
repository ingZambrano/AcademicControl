package com.avior.academic.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.Avisos;
import com.avior.academic.pojo.AvisosDet;
import com.avior.academic.pojo.PreLogin;

@Repository("avisosDataImp")
public class AvisosDataImp implements AvisosData {
	
	private static final Logger logger = Logger.getLogger(AvisosDataImp.class);
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
	public List<Avisos> getAvisosXuser(String idUser, String desde, String hasta) throws SQLException{
		List<Avisos> ret = new ArrayList<Avisos>();
		String sqlQuery = "Exec "+pre.getBase()+".dbo.App_GetCircularesXUser ?, ?, ? ";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		
		
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setString(1, idUser);			
			call.setTimestamp(2,  new Timestamp(df.parse(desde).getTime()));
			call.setTimestamp(3,  new Timestamp(df.parse(hasta).getTime()));
			rs = call.executeQuery();
			Avisos avisos= null;
			while(rs.next()){
				avisos = new Avisos();
				avisos.setIdCircular(rs.getLong("IDCircular"));
				avisos.setfVencimiento(df.format(rs.getDate("CFVencimiento")));
				avisos.setfPublicacion(df.format(rs.getDate("CFPublicacion")));
				avisos.setTitulo(rs.getString("CTituloC"));
				avisos.setStatus(rs.getString("estatus"));
				avisos.setReviso(rs.getInt("Reviso"));
				ret.add(avisos);				
			}
		}catch(SQLException|ParseException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		
		return ret;		
	}
	@Override
	public AvisosDet getDetAviso(long idAviso) throws SQLException {
		AvisosDet ret = null;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.App_circularXId ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn = null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(),pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idAviso);
			rs = call.executeQuery();
			ret = new AvisosDet();
			while(rs.next()){
				ret.setIdCircular(rs.getLong("IDCircular"));
				ret.setIdUser(rs.getString("UserId"));
				ret.setfVencimiento(df.format(rs.getDate("CFVencimiento")));
				ret.setfPublicacion(df.format(rs.getDate("CFPublicacion")));
				ret.setTitulo(rs.getString("CTituloC"));
				ret.setDescripcion(rs.getString("CDescripcionC"));
				ret.setUsuarioPublico(rs.getString("Usuario"));
				
			}
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		return ret;
	}
	@Override
	public String getTxtPlanoDetalle(String userName, long idAviso) throws SQLException {
		String ret = null;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.App_GetCircularesDet ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn = null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(),pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setString(1, userName);
			rs = call.executeQuery();
			while(rs.next()){
				if(rs.getLong("IDCircular") == idAviso){				
					ret = rs.getString("CDescripcionC");
				}
			}
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		return ret;
	}

	@Override
	public boolean actualizarNotificacion(String idUser, long idAviso)
			throws SQLException {
		boolean ret = false;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.App_acup_CircularRevisada ?, ? ";
		CallableStatement call = null;
		Connection conn = null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idAviso);
			call.setString(2, idUser);
			ret = call.execute();
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, null, call);		
		}
		return ret;
	}
	@Override
	public boolean insertarNotificacion(String idUser, long idAviso)
			throws SQLException {
		boolean ret = false;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.App_acin_CircularRevisada ?, ? ";
		CallableStatement call = null;
		Connection conn = null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idAviso);
			call.setString(2, idUser);
			ret = call.execute();
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, null, call);		
		}
		return ret;
		
	}
}
