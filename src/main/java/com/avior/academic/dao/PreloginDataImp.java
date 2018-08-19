package com.avior.academic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.ConexionBase;
import com.avior.academic.pojo.PreLogin;
import com.avior.academic.pojo.School;

@Repository("preloginDataImp")
public class PreloginDataImp implements PreloginData {

	private Logger logger = Logger.getLogger(PreloginDataImp.class);

	private static final String base = "Concentradora_Users";

//	private DataSource dataSource;

	@Autowired
	private ConexionBase conBase;
	
//	@Autowired
//	protected JdbcTemplate jdbcT;
//
//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		if(! (this.conBase.getDriverClassName() == null )){
//			this.dataSource = ConexionesBD.getDataSource(conBase);
//			this.jdbcT = new JdbcTemplate(this.dataSource);
//		}
//		
//
//	}

	@Override
	public List<PreLogin> getPreloginObject(String eMail) throws SQLException {
		List<PreLogin> ret = null;

		School school = null;
		PreLogin preLogin = null;

		String sqlQuery = "select esc.IDEscuela, " + "esc.NomEscuela, "
				+ "esc.Logo, " + "esc.WebService, " + "esc.Instancia, "
				+ "esc.Usuario, " + "esc.Contraseña, " + "esc.Catalogo, "
				+ "esc.Puerto, " + "esc.Url " + "from " + base
				+ ".dbo.App_Escuelas esc, " + base + ".dbo.App_Users usr "
				+ "where esc.IDEscuela = usr.IDEscuela " + "and usr.Email = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = this.conBase.getDataSource().getConnection();
			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.setString(1, eMail);
			rs = pstmt.executeQuery();
			ret = new ArrayList<PreLogin>();
			while (rs.next()) {
				preLogin = new PreLogin();
				school = new School();
				preLogin.setBase(rs.getString("Catalogo"));
				preLogin.setMsj("Usuario autorizado");
				preLogin.setAcepted(true);
				preLogin.setEmail(eMail);
				preLogin.setContextoWeb(rs.getString("Url"));
				preLogin.setEndPoint(rs.getString("WebService"));
				preLogin.setInstanciaBD(rs.getString("Instancia"));
				preLogin.setUsrBD(rs.getString("Usuario"));
				preLogin.setPassBD(rs.getString("Contraseña"));
				preLogin.setCatalogoBD(rs.getString("Catalogo"));
				preLogin.setPuertoBD(rs.getString("Puerto"));
				school.setIdEscuela(rs.getLong("IDEscuela"));
				school.setNombreEscuela(rs.getString("NomEscuela"));
				school.setPathLogo(rs.getString("Logo"));
				preLogin.setSchool(school);
				ret.add(preLogin);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConexionesBD.closeConnection(conn, pstmt, rs, null);
		}

		return this.filtrarUsuarios(ret);
	}

	@Override
	public PreLogin getPreloginObjectSingle(String eMail, int idEscuela)
			throws SQLException {
		// PreLogin ret = null;

		School school = null;
		PreLogin preLogin = null;

		String sqlQuery = "select esc.IDEscuela, " + "esc.NomEscuela, "
				+ "esc.Logo, " + "esc.WebService, " + "esc.Instancia, "
				+ "esc.Usuario, " + "esc.Contraseña, " + "esc.Catalogo, "
				+ "esc.Puerto, " + "esc.Url " + "from " + base
				+ ".dbo.App_Escuelas esc, " + base + ".dbo.App_Users usr "
				+ "where esc.IDEscuela = usr.IDEscuela "
				+ "and esc.IDEscuela = ? " + "and usr.Email = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = this.conBase.getDataSource().getConnection();
			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.setInt(1, idEscuela);
			pstmt.setString(2, eMail);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				preLogin = new PreLogin();
				school = new School();
				preLogin.setBase(rs.getString("Catalogo"));
				preLogin.setMsj("Usuario autorizado");
				preLogin.setAcepted(true);
				preLogin.setEmail(eMail);
				preLogin.setContextoWeb(rs.getString("Url"));
				preLogin.setEndPoint(rs.getString("WebService"));
				preLogin.setInstanciaBD(rs.getString("Instancia"));
				preLogin.setUsrBD(rs.getString("Usuario"));
				preLogin.setPassBD(rs.getString("Contraseña"));
				preLogin.setCatalogoBD(rs.getString("Catalogo"));
				preLogin.setPuertoBD(rs.getString("Puerto"));
				school.setIdEscuela(rs.getLong("IDEscuela"));
				school.setNombreEscuela(rs.getString("NomEscuela"));
				school.setPathLogo(rs.getString("Logo"));
				preLogin.setSchool(school);

			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConexionesBD.closeConnection(conn, pstmt, rs, null);
		}

		return preLogin;
	}

	@Override
	public List<String> getTokensUsuariosNoti(List<String> listaEmails,
			long idEscuela) throws SQLException {
		List<String> ret = new ArrayList<String>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select usr.Campo ");
		sqlQuery.append("from " + base + ".dbo.App_Users usr ");
		sqlQuery.append("where usr.idEscuela = ? ");
		sqlQuery.append("and usr.Email in ( ");
		for (int j = 0; j < listaEmails.size(); j++) {
			if (j == 0) {
				sqlQuery.append("?");
			} else {
				sqlQuery.append(",?");
			}
		}
		sqlQuery.append(" )");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = this.conBase.getDataSource().getConnection();
			pstmt = conn.prepareStatement(sqlQuery.toString());
			pstmt.setLong(1, idEscuela);
			for (int j = 0; j < listaEmails.size(); j++) {
				pstmt.setString(j + 2, listaEmails.get(j));
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String fcmTokens = rs.getString("Campo");
				if(fcmTokens != null && !fcmTokens.equals("")){
					List<String> tokens = new ArrayList<String>(Arrays.asList(fcmTokens.split("·")));
					if(tokens != null){
						ret.addAll(tokens);
					}
				}


			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConexionesBD.closeConnection(conn, pstmt, rs, null);
		}
		return ret;
	}

	@Override
	public PreLogin createPrelogin(long idEscuela) throws SQLException {
		School school = null;
		PreLogin preLogin = null;

		String sqlQuery = "select esc.IDEscuela, " + "esc.NomEscuela, "
				+ "esc.Logo, " + "esc.WebService, " + "esc.Instancia, "
				+ "esc.Usuario, " + "esc.Contraseña, " + "esc.Catalogo, "
				+ "esc.Puerto, " + "esc.Url " + "from " + base
				+ ".dbo.App_Escuelas esc " + "where esc.IDEscuela = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = this.conBase.getDataSource().getConnection();
			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.setLong(1, idEscuela);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				preLogin = new PreLogin();
				school = new School();
				preLogin.setBase(rs.getString("Catalogo"));
				preLogin.setMsj("Usuario autorizado");
				preLogin.setAcepted(true);
				preLogin.setContextoWeb(rs.getString("Url"));
				preLogin.setEndPoint(rs.getString("WebService"));
				preLogin.setInstanciaBD(rs.getString("Instancia"));
				preLogin.setUsrBD(rs.getString("Usuario"));
				preLogin.setPassBD(rs.getString("Contraseña"));
				preLogin.setCatalogoBD(rs.getString("Catalogo"));
				preLogin.setPuertoBD(rs.getString("Puerto"));
				school.setIdEscuela(rs.getLong("IDEscuela"));
				school.setNombreEscuela(rs.getString("NomEscuela"));
				school.setPathLogo(rs.getString("Logo"));
				preLogin.setSchool(school);

			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConexionesBD.closeConnection(conn, pstmt, rs, null);
		}

		return preLogin;
	}

	private List<PreLogin> filtrarUsuarios(List<PreLogin> listaPreLogin) {

		PreLogin current;
		int i;		
		for (i = 0; i < listaPreLogin.size(); i++) {
			current = listaPreLogin.get(i);			
			for(int j = i+1; j < listaPreLogin.size(); ){
				if (current.getEmail().equals(listaPreLogin.get(j).getEmail())
						&& (current.getSchool().getIdEscuela() == listaPreLogin.get(j).getSchool().getIdEscuela())) {
					listaPreLogin.remove(j);					
				}else{
					j++;
				}
			}			
		}
		return listaPreLogin;
	}

}
