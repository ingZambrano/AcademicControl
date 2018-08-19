package com.avior.academic.dao;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.ConexionBase;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jairo on 18/08/16.
 */

@Repository("AvisoDePrivacidad")
public class PrivacidadDataImpl implements PrivacidadData {

    private final Logger logger = Logger.getLogger(this.getClass());

//    private DataSource dataSource;

//    @Autowired
//    protected JdbcTemplate jdbcT;
    
    @Autowired
	private ConexionBase conBase;

//    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
////        this.jdbcT = new JdbcTemplate(this.dataSource);
//    }


    @Override
    public String getCurrentAvisoPrivacidad() throws SQLException {
        String sql = "SELECT TOP 1 AvisoDePrivacidad FROM Concentradora_Users.dbo.App_Avisos";
        Connection conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String ret = "";
        try {
            conn = this.conBase.getDataSource().getConnection();//jdbcT.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = rs.getString("AvisoDePrivacidad");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ConexionesBD.closeConnection(conn, pstmt, rs, null);
        }
        if(ret.equals("")) throw new SQLException("Aviso de privacidad no encontrado");

        return ret;
    }

    @Override
    public String getTerminosServicio() throws SQLException {
        String sql = "SELECT TOP 1 [Terminos y Condiciones] FROM [Concentradora_Users].[dbo].[App_Avisos]";
        Connection conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String ret = "";
        try {
            conn = this.conBase.getDataSource().getConnection();//jdbcT.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = rs.getString(1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ConexionesBD.closeConnection(conn, pstmt, rs, null);
        }
        if(ret.equals("")) throw new SQLException("Terminos de servicio no encontrados");

        return ret;
    }
}
