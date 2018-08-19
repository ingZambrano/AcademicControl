package com.avior.academic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.PreLogin;

@Repository("loginDataImp")
public class LoginDataImp implements LoginData {
	
	Logger logger = Logger.getLogger(LoginDataImp.class);
	
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
	public boolean validaMailUser(String mail, String usrId) throws Exception {
		boolean ret = false;
		String sqlQuery = "select COUNT(*) as count "
				+ "from "+pre.getBase()+".dbo.aspnet_Membership "
				+ "where UserId = ? "
				+ "and Email = ? ";
		
		logger.info(sqlQuery);
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());

			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.setString(1, usrId);
			pstmt.setString(2, mail);
			rs = pstmt.executeQuery();			
			if(rs.next()){
				
				long count = rs.getLong("count");
				if(count >0){
					ret = true;
				}
				
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConexionesBD.closeConnection(conn, pstmt, rs, null);		
		}
//		if(ret == false) throw new Exception("El eMail y nombre de usuario no concuerdan");
		
		return ret;

	}

}
