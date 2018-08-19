package com.avior.academic.dao;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.AppUser;
import com.avior.academic.pojo.ConexionBase;




//import com.avior.academic.pojo.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import javax.sql.DataSource;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jairo on 27/07/16.
 */
@Repository("appUserDataImp")
public class AppUserDataImp implements AppUserData {

    private final static Logger logger = Logger.getLogger(AppUserDataImp.class);

//    private DataSource dataSource;


//    @Autowired
//    protected JdbcTemplate jdbcT;
//
//    @Autowired
//    public void setDataSource(DataSource ds){
//        this.dataSource = ds;
//        this.jdbcT = new JdbcTemplate(this.dataSource);
//    }

    @Autowired
	private ConexionBase conBase;

    @Override
    public AppUser getUserData(String userName, Long idEscuela) throws Exception {
        String sql = "SELECT * FROM Concentradora_Users.dbo.App_Users WHERE Email = ? AND IDEscuela = ?";
        Connection conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AppUser ret = null;
        try {
            conn = this.conBase.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setLong(2, idEscuela);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = new AppUser();
                ret.setIdUsers(rs.getLong("IDUsers"));
                ret.setEmail(rs.getString("Email"));
                ret.setToken(rs.getString("Token"));
                ret.setTipo(rs.getString("Tipo"));
                ret.setIdEscuela(rs.getLong("IDEscuela"));
                ret.setFCMTokens(rs.getString("Campo"));
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ConexionesBD.closeConnection(conn, pstmt, rs, null);
        }
        if(ret == null){
        	logger.error("No se encontró usuario: SELECT * FROM Concentradora_Users.dbo.App_Users WHERE Email = "+userName+" AND IDEscuela = "+idEscuela);
        	throw new Exception("Usuario no encontrado");
        }

        return ret;
    }

    @Override
    public void saveToken(String username, String tokenString, Long idEscuela) throws SQLException{
        String sql = "UPDATE Concentradora_Users.dbo.App_Users SET Campo = ?  WHERE Email = ? AND IDEscuela = ?";
        logger.info("SQL Statement: " + sql);
        Integer affected = 0;
        Connection conn=null;
        PreparedStatement pstmt = null;
        try {
            conn = this.conBase.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tokenString);
            pstmt.setString(2, username);
            pstmt.setLong(3, idEscuela);
            affected = pstmt.executeUpdate();
            logger.info("FCM Token guardado. " + affected +" registros afectados");

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ConexionesBD.closeConnection(conn, pstmt, null, null);
        }
        if(affected == 0) logger.info("No se registraron en la base de datos!");
    }



    @Override
    public void saveTokenToAll(String email, String tokenString) throws SQLException {
        String sql = "UPDATE Concentradora_Users.dbo.App_Users SET Campo = ?  WHERE Email = ?";
        logger.info("SQL Statement: " + sql);
        Integer affected = 0;
        Connection conn=null;
        PreparedStatement pstmt = null;
        try {
            conn = this.conBase.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tokenString);
            pstmt.setString(2, email);

            affected = pstmt.executeUpdate();
            logger.info("FCM Token guardado. " + affected +" registros afectados");

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ConexionesBD.closeConnection(conn, pstmt, null, null);
        }
        if(affected == 0) logger.info("No se registraron en la base de datos!");
    }

    @Override
    public List<AppUser> readAllUserData(String email) throws Exception {
        String sql = "SELECT * FROM Concentradora_Users.dbo.App_Users WHERE Email = ?";
        Connection conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AppUser> ret = new ArrayList<AppUser>();
        AppUser record;

        try {
            conn = this.conBase.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                record = new AppUser();
                record.setIdUsers(rs.getLong("IDUsers"));
                record.setEmail(rs.getString("Email"));
                record.setToken(rs.getString("Token"));
                record.setTipo(rs.getString("Tipo"));
                record.setIdEscuela(rs.getLong("IDEscuela"));
                record.setFCMTokens(rs.getString("Campo"));

                ret.add(record);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ConexionesBD.closeConnection(conn, pstmt, rs, null);
        }
        if(ret.isEmpty()) throw new Exception("No se encontraron datos del usuario");

        return ret;
    }

    @Override
	public List<AppUser> getUserToken(long idEscuela) throws Exception{
    	String sql = "SELECT * FROM Concentradora_Users.dbo.App_Users WHERE IDEscuela = ? and Campo is not null ";
        Connection conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AppUser> ret = new ArrayList<AppUser>();
        AppUser appUser;

        try {
            conn = this.conBase.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, idEscuela);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                appUser = new AppUser();
                appUser.setIdUsers(rs.getLong("IDUsers"));
                appUser.setEmail(rs.getString("Email"));
                appUser.setToken(rs.getString("Token"));
                appUser.setTipo(rs.getString("Tipo"));
                appUser.setIdEscuela(rs.getLong("IDEscuela"));
                appUser.setFCMTokens(rs.getString("Campo"));

                ret.add(appUser);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ConexionesBD.closeConnection(conn, pstmt, rs, null);
        }
        if(ret.isEmpty()) throw new Exception("No se encontraron datos del usuario");

        return ret;
	}
    
	@Override
	public void removeToken(AppUser appUser) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
    
   
}
