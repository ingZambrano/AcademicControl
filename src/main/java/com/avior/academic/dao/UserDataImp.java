package com.avior.academic.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.PreLogin;
import com.avior.academic.pojo.UserInfo;
import com.avior.academic.pojo.UserPadreHijo;

@Repository("userDataImp")
public class UserDataImp implements UserData {

	private final static Logger logger = Logger.getLogger(UserDataImp.class);
	
//	private DataSource dataSource;
	
	@Autowired
	private PreLogin pre;

//	@Autowired
//	protected JdbcTemplate jdbcT;
//
//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//		this.jdbcT = new JdbcTemplate(this.dataSource);
//	}

	@Override
	public String getUserIdByUserName(String userName) throws Exception {
		
		String sqlQuery = "SELECT * "
				+ "FROM "+pre.getBase()+".dbo.aspnet_Users " + "WHERE UserName = ? ";
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserInfo ret = null;
		logger.info("SQLGetUser: " + sqlQuery);
		try {
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());

			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();			
			while (rs.next()) {
				ret = new UserInfo();
				ret.setUserId(rs.getString("UserId"));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConexionesBD.closeConnection(conn, pstmt, rs, null);		
		}
		
		return ret.getUserId();
	}
	
	@Override
	public UserInfo getUserbyId(String idUsuario) throws SQLException {
		String sqlQuery = "select UserId, UserName, Mail, TIPO "
				+"from "+pre.getBase()+".dbo.app_Usuarios "
				+"where userId = ? ";
		UserInfo ret = null;
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());

			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.setString(1, idUsuario);
			rs = pstmt.executeQuery();
			ret = new UserInfo();
			while (rs.next()) {
				ret.setNombreUser(rs.getString("UserName"));
				ret.setUserId(idUsuario);
				ret.seteMail(rs.getString("Mail"));
				ret.setRole(rs.getString("TIPO"));
				
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConexionesBD.closeConnection(conn, pstmt, rs, null);		
		}
		return ret;
	}
	


	@Override
	public List<UserPadreHijo> getPadresHijos(String idUsuario) throws SQLException {
		List<UserPadreHijo>  ret = null;
		String sqlQuery ="Exec "+pre.getBase()+".dbo.App_PadresHijos ?";
		
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setString(1, idUsuario);
			rs = call.executeQuery();
			ret = new ArrayList<UserPadreHijo>();
			UserPadreHijo up;
			while(rs.next()){
				up = new UserPadreHijo();
				up.setMatricula(rs.getString("AMatricula"));
				up.setIdAlumno(rs.getLong("IDAlumno"));
				up.setNombreAlumno(rs.getString("Anombre"));
				up.setNombrePadre(rs.getString("ParNombre"));
				up.setUserId(rs.getString("UserId"));
				up.setUserName(rs.getString("UserName"));
				
				ret.add(up);
			}
			
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		return ret;
	}
	@Override
	public byte[] getPhotoByUserId(String idUsuario) throws SQLException {
		byte[] ret = null;
		String sqlQuery = "select BytesOriginal "
				+ "from "+pre.getBase()+".dbo.AFamParientesFoto "
				+ "where IDPariente = 1619 ";
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());

			pstmt = conn.prepareStatement(sqlQuery);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getBytes("BytesOriginal");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConexionesBD.closeConnection(conn, pstmt, rs, null);		
		}
		
		
		return ret;
	}



	//Aplica para alumnos loggeados
		@Override
		public List<UserPadreHijo> getInfoAlumno(String idUsuario)
				throws SQLException {
			
			List<UserPadreHijo> ret = null;
			
			String sqlQuery = "SELECT TOP (2147483647) "+ 
							"WITH TIES AAlumno.AMatricula, "+ 
							"AAlumnoGrupo.IDAlumno, "+ 
							"AAlumno.ANombre + ' ' + AAlumno.AApellidoPaterno + ' ' + AAlumno.AApellidoMaterno + ' ' AS ANombre, "+
							"'' AS ParNombre, "+
							"AAlumno.UserId, "+ 	      
							"aspnet_Users.UserName "+
							"FROM "+pre.getBase()+".dbo.AAlumno INNER JOIN "+pre.getBase()+".dbo.aspnet_Users "+ 
							"ON "+pre.getBase()+".dbo.AAlumno.UserId = "+pre.getBase()+".dbo.aspnet_Users.UserId "+ 
							"INNER JOIN "+pre.getBase()+".dbo.AAlumnoGrupo "+ 
							"ON "+pre.getBase()+".dbo.AAlumno.IDAlumno = "+pre.getBase()+".dbo.AAlumnoGrupo.IDAlumno "+		  
							"WHERE ("+pre.getBase()+".dbo.aspnet_Users.UserId = ?) "+ 
							"AND ("+pre.getBase()+".dbo.AAlumnoGrupo.Inscrito = 1) "+
							"ORDER BY "+pre.getBase()+".dbo.AAlumnoGrupo.IDAlumno ";
			
			
			Connection conn=null;
			CallableStatement call = null;
			ResultSet rs = null;		
			try {
				conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
				call = conn.prepareCall(sqlQuery);
				call.setString(1, idUsuario);
				rs = call.executeQuery();			
				UserPadreHijo up = null;
				ret = new ArrayList<UserPadreHijo>();
				while (rs.next()) {
					up = new UserPadreHijo();
					up.setMatricula(rs.getString("AMatricula"));
					up.setIdAlumno(rs.getLong("IDAlumno"));
					up.setNombreAlumno(rs.getString("Anombre"));
					up.setNombrePadre(rs.getString("ParNombre"));
					up.setUserId(rs.getString("UserId"));
					up.setUserName(rs.getString("UserName"));				
					ret.add(up);				
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				ConexionesBD.closeConnection(conn, null, rs, call);		
			}
			
			return ret;
			
		}

		@Override
		public String getPathFotoUser(String userName) throws SQLException {
			String  ret = null;
			String sqlQuery ="Exec "+pre.getBase()+".dbo.App_GetFotos ?";
			
			ResultSet rs = null;
			CallableStatement call = null;
			Connection conn= null;
			try{
				conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
				call = conn.prepareCall(sqlQuery);
				call.setString(1, userName);
				rs = call.executeQuery();
				
				while(rs.next()){
					ret = rs.getString("Foto");
				}
				
				
			}catch(SQLException e){
				logger.error(e.getMessage(), e);
			}finally {
				ConexionesBD.closeConnection(conn, null, rs, call);		
			}
			return ret;
		}
		
		

}
