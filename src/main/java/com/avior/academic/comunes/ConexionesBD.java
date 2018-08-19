package com.avior.academic.comunes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.avior.academic.pojo.ConexionBase;

public class ConexionesBD {
	
	private static final Logger logger = Logger.getLogger(ConexionesBD.class);
	
	public static Connection getConnection(String instancia, String puerto, String usr, String pass, String catalogo){
//		String[] parser = instancia.split(",");
//		String host = parser[0];
//		String port = parser[1];		
		
		Connection connection = null;
		try {
			//logger.info("Se va a crear una nueva conexiona BD a la instancia:"+instancia);
			connection = DriverManager.getConnection("jdbc:sqlserver://"+instancia+":"+puerto,usr, pass);
		} catch (SQLException e) {
			logger.error("Fallo la conexion",e);
			
		}
		return connection;
		
	}
	
	public static DriverManagerDataSource getDataSource(ConexionBase conBase) {
		
		  DriverManagerDataSource dataSource = new DriverManagerDataSource();		
		  dataSource.setDriverClassName(conBase.getDriverClassName());
		
		  dataSource.setUrl(conBase.getUrl());
		
		  dataSource.setUsername(conBase.getUserBD());
		
		  dataSource.setPassword(conBase.getPassBD());
		
		  return dataSource;
		
	}

	 public static boolean testConexionBD(String host, String port, String usr, String pass){
	    	
	    	ConexionBase cb = new ConexionBase();
	    	cb.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    	cb.setIpBase(host);
	    	cb.setPassBD(pass);
	    	cb.setPortBase(port);
	    	cb.setUserBD(usr);    	
	    	cb.setUrl("jdbc:sqlserver://"+host+":"+port);
	    	
	    	try {
				if(getDataSource(cb).getConnection() != null){
					closeConnection(ConexionesBD.getDataSource(cb).getConnection(),null, null, null);
					return true;
				}
			} catch (SQLException e) {
				
				logger.error("Error en los datos de conexión a la base:\n"+e);
			}
	    	
	    	return false;
	    }

	
	public static void closeConnection(Connection conn,
			PreparedStatement pstmt, ResultSet rs, CallableStatement call) throws SQLException{

		if (pstmt != null) {
			pstmt.close();
		}
		if (rs != null) {
			rs.close();
		}
		if (conn != null) {
			conn.close();
		}
		if (call != null) {
			call.close();
		}

	}

}
