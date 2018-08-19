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
import org.springframework.stereotype.Repository;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.Notificacion;
import com.avior.academic.pojo.PreLogin;

@Repository("notificacionesDataImp")
public class NotificacionesDataImp implements NotificacionesData {
	
	private static final Logger logger = Logger.getLogger(NotificacionesDataImp.class);
	private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Notificacion getNotificacion(String tipo, long id, PreLogin pre) throws SQLException{
		Notificacion ret=null;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_Notificacion ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;		
		
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setString(1, tipo);
			call.setLong(2, id);
			
			rs = call.executeQuery();
			
			while(rs.next()){
				ret = new Notificacion();
				ret.setTitulo(rs.getString("TITULO"));
				ret.setfVencimiento(df.format(rs.getDate("VENCIMIENTO")));
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		return ret;
	}

	@Override
	public List<String> getEmailUsuariosNoti(String tipo, long id,PreLogin pre) throws SQLException{
		List<String> ret = new ArrayList<String>();
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_UsersNotificacion ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		
		
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setString(1, tipo);
			call.setLong(2, id);
			
			rs = call.executeQuery();
			while(rs.next()){
				
				ret.add(rs.getString("correo"));				
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		
		return ret;		
	}

	

}
