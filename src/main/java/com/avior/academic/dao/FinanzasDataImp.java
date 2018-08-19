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
import com.avior.academic.pojo.CnsCobAbonos;
import com.avior.academic.pojo.CobroCargosXAlumno;
import com.avior.academic.pojo.PreLogin;


/**
 * 
 * @author jozambrano
 *
 */
@Repository("finanzasDataImp")
public class FinanzasDataImp implements FinanzasData {
	
	private final static Logger logger = Logger.getLogger(FinanzasDataImp.class);

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
	public List<CobroCargosXAlumno> getFinanzasXAlumno(long idAlumno) throws SQLException {
		List<CobroCargosXAlumno> ret = new ArrayList<CobroCargosXAlumno>();
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_GetCobCargosXAlumno ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idAlumno);
			rs = call.executeQuery();
			CobroCargosXAlumno cobro;			
			while(rs.next()){
				
				
				cobro = new CobroCargosXAlumno();
				cobro.setConcepto(rs.getString("Concepto"));
				cobro.setFechaVencimineto(rs.getString("FechaVencimineto"));
				cobro.setIdCargo(rs.getLong("IDCargo"));
				cobro.setSaldoTotal(rs.getDouble("SaldoTotal"));
//				cobro.setAbonosTotal(rs.getDouble("AbonosTotal"));
				cobro.setColor(rs.getString("color"));
				CnsCobAbonos detalleCobro = getDetalleMonto(cobro.getIdCargo());
				if(detalleCobro.getAbonosTotal() == 0){
					cobro.setVerDetalle(false);
				}else{
					cobro.setVerDetalle(true);
				}
//				if(rs.getDouble("SaldoTotal") == 0){
//					cobro.setVerDetalle(true);
//				}else{
//					cobro.setVerDetalle(true);
//				}
				
				ret.add(cobro);
				
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		return ret; 
	}

	@Override
	public CnsCobAbonos getDetalleMonto(long idCargo) throws SQLException{
		
		CnsCobAbonos ret = null;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_CnsCobAbonos ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idCargo);
			rs = call.executeQuery();
			ret = new CnsCobAbonos();
			while(rs.next()){
				ret.setAbonosTotal(rs.getDouble("AbonosTotal"));
				ret.setaPagarTotal(rs.getDouble("APagarTotal"));
				ret.setBecaMonto(rs.getDouble("BecaMonto"));
				ret.setCFDI(pre.getContextoWeb()+rs.getString("CFDI"));
				ret.setConcepto(rs.getString("Concepto"));
				ret.setDescuentoMonto(rs.getDouble("DescuentoMonto"));
				ret.setFechaVencimiento(df.format(rs.getDate("CargoVencimiento")));
				ret.setFolio(rs.getLong("FOLIO"));
				ret.setiDCargo(rs.getLong("IDCargo"));
				ret.setRecargosTotal(rs.getDouble("RecargosTotal"));
				ret.setReciboNo(rs.getLong("ReciboNo"));
				ret.setSaldoTotal(rs.getDouble("SaldoTotal"));
				ret.setTarifaImporte(rs.getDouble("TarifaImporte"));
				ret.setXmlURL(pre.getContextoWeb()+rs.getString("XmlURL"));
				
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn, null, rs, call);
		}
			
			
			
		return ret;
	}

}
