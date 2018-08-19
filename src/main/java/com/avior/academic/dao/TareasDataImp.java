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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.Materia;
import com.avior.academic.pojo.PreLogin;
import com.avior.academic.pojo.Tarea;
import com.avior.academic.pojo.TareaContesta;
import com.avior.academic.pojo.TareaMateriaGrupo;

@Repository("tareasDataImp")
public class TareasDataImp implements TareasData {
	
	private final static Logger logger = Logger.getLogger(TareasDataImp.class);
	
	private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
//	private DataSource dataSource;
	
	@Autowired
	private PreLogin pre;

//	@Autowired
//	protected JdbcTemplate jdbcT;
//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//		this.jdbcT = new JdbcTemplate(this.dataSource);
//	}

	@Override
	public long getIdGrupoXIdAlumno(long idAlumno) throws SQLException{
		
		long ret = 0L;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_GetIDGrupoXIDAlumno ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1,  idAlumno);
			rs = call.executeQuery();
			
			while(rs.next()){
				
				ret = rs.getLong("IDGrupo");
				
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally {
			ConexionesBD.closeConnection(conn, null, rs, call);		
		}
		
		return ret;
	}

	@Override
	public List<Materia> getMateriaXGrupo(long idGrupo) throws SQLException{
		List<Materia> ret = new ArrayList<Materia>();
		Materia materia = null;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_GetMateriaXGrupo ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idGrupo);
			rs = call.executeQuery();
			
			while(rs.next()){
				materia = new Materia();
				materia.setClaveMat(rs.getString("ClaveMat"));
				materia.setMateriaX(rs.getNString("MateriaX"));
				ret.add(materia);
			}
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn,null, rs, call);
		}
		
		
		return ret;
	}

	@Override
	public List<TareaMateriaGrupo> getTareasXMateriaT(String desde, String hasta,
			String claveMat, long idAlumno, String userId) throws SQLException{		
		List<TareaMateriaGrupo> ret = new ArrayList<TareaMateriaGrupo>();
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_GetTareasXMateriaT ?, ?, ?, ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setTimestamp(1,new Timestamp(df.parse(desde).getTime()));
			call.setTimestamp(2,new Timestamp(df.parse(hasta).getTime()));
			call.setString(3,claveMat);
			call.setString(4, userId);			
			call.setLong(5,idAlumno);			
			rs = call.executeQuery();
			TareaMateriaGrupo mat;
			while(rs.next()){
				mat = new TareaMateriaGrupo();
				mat.setiDTarea(rs.getLong("IDTarea"));
				mat.settFEntrega(df.format(rs.getDate("TFEntrega")));
				mat.settFGuarda(df.format(rs.getDate("TFGuarda")));
				mat.settEstatus(rs.getInt("TEstatus"));
				mat.settTituloT(rs.getString("TTituloT"));
				mat.setClaveMat(rs.getString("ClaveMat"));
				mat.setMateriaX(rs.getString("MateriaX"));
				mat.setClaveGrupo(rs.getString("ClaveGrupo"));
				mat.setProfesor(rs.getString("Profesor"));
				mat.setCalNombre(rs.getString("CalNombre"));
				ret.add(mat);
			}			
		}catch(SQLException|ParseException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn,null, rs, call);
		}
		return ret;
	}
	

	@Override
	public List<TareaMateriaGrupo> getTareasXMateriaA(String desde,
			String hasta, String claveMat, String userId) throws SQLException {
		List<TareaMateriaGrupo> ret = new ArrayList<TareaMateriaGrupo>();
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_GetTareasXMateriaA ?, ?, ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setTimestamp(1,new Timestamp(df.parse(desde).getTime()));
			call.setTimestamp(2,new Timestamp(df.parse(hasta).getTime()));
			call.setString(3,claveMat);
			call.setString(4, userId);			
			rs = call.executeQuery();
			TareaMateriaGrupo mat;
			while(rs.next()){
				mat = new TareaMateriaGrupo();
				mat.setiDTarea(rs.getLong("IDTarea"));
				mat.settFEntrega(df.format(rs.getDate("TFEntrega")));
				mat.settFGuarda(df.format(rs.getDate("TFGuarda")));
				mat.settEstatus(rs.getInt("TEstatus"));
				mat.settTituloT(rs.getString("TTituloT"));
				mat.setClaveMat(rs.getString("ClaveMat"));
				mat.setMateriaX(rs.getString("MateriaX"));
				mat.setClaveGrupo(rs.getString("ClaveGrupo"));
				mat.setProfesor(rs.getString("Profesor"));
				mat.setCalNombre(rs.getString("CalNombre"));
				ret.add(mat);
			}			
		}catch(SQLException|ParseException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn,null, rs, call);
		}
		return ret;
	}
	
	
	
	
	@Override
	public Tarea getTareaXIdDet(long idTarea) throws SQLException{
		Tarea ret = null;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_gettareaXId ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idTarea);
			rs = call.executeQuery();
			while(rs.next()){
				ret = new Tarea();
				ret.setiDTarea(rs.getLong("IDTarea"));
				ret.setiDGrupo(rs.getLong("IDGrupo"));
				ret.setClaveMat(rs.getString("ClaveMat"));
				ret.settFEntrega(df.format(rs.getDate("TFEntrega")));
				ret.settFGuarda(df.format(rs.getDate("TFGuarda")));
				ret.setTEstatus(rs.getInt("TEstatus"));
				ret.settTituloT(rs.getString("TTituloT"));
				ret.settDescripcionT(rs.getString("TDescripcionT"));
				ret.setUserId(rs.getString("UserId"));
				ret.setUsuario(rs.getString("Usuario"));
				ret.setCalNombre(rs.getString("CalNombre"));
				ret.setiDCalendario(rs.getLong("IDCalendario"));
				ret.setAltaPor(rs.getString("AltaPor"));
				ret.settFAlta(rs.getString("TFAlta"));
				ret.setMateriaX(rs.getString("MateriaX"));				
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn,null, rs, call);
		}
		return ret;
	}

	@Override
	public TareaContesta getTareaContesta(long idTarea, String userId,
			String tipoUsuario) throws SQLException {
		TareaContesta ret = null;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_gettareacontesta ?, ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idTarea);
			call.setString(2, userId);
			call.setString(3, tipoUsuario);
			rs = call.executeQuery();
			while(rs.next()){
				ret = new TareaContesta();
				ret.setIdTareaA(rs.getLong("IDTareaA"));
				ret.setIdTarea(rs.getLong("IDTarea"));
				ret.setTaComenMa(rs.getString("TAComenMa"));
				ret.setTaComenAlu(rs.getString("TAComenAlu"));
				ret.setTaComenTut(rs.getString("TAComenTut"));
				ret.setIdAlumnoGrupo(rs.getLong("IDAlumnoGrupo"));
				ret.setTaCalific(rs.getFloat("TACalific"));
				ret.setTaContenido(rs.getString("TAContenido"));
				ret.setUserId(rs.getString("UserId"));				
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn,null, rs, call);
		}
		return ret;
	}

//	@Override
//	public boolean acupTareaRevisada(long idTarea, String userId, long idAlumno)
//			throws SQLException {
//		boolean ret = false;
//		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_acup_TareaRevisada ?, ?, ?";
//		ResultSet rs = null;
//		CallableStatement call = null;
//		Connection conn= null;
//		try{
//			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
//			call = conn.prepareCall(sqlQuery);
//			call.setLong(1, idTarea);
//			call.setString(2, userId);
//			call.setLong(3, idAlumno);
//			ret = call.execute();
//			
//		}catch(SQLException e){
//			logger.error(e.getMessage(), e);
//		}finally{
//			ConexionesBD.closeConnection(conn,null, rs, call);
//		}
//		
//		return ret;
//	}
//
//	@Override
//	public boolean acinTareaRevisada(long idTarea, String userId, long idAlumno)
//			throws SQLException {
//		boolean ret = false;
//		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_acin_TareaRevisada ?, ?, ?";
//		ResultSet rs = null;
//		CallableStatement call = null;
//		Connection conn= null;
//		try{
//			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
//			call = conn.prepareCall(sqlQuery);
//			call.setLong(1, idTarea);
//			call.setString(2, userId);
//			call.setLong(3, idAlumno);
//			ret = call.execute();
//			
//		}catch(SQLException e){
//			logger.error(e.getMessage(), e);
//		}finally{
//			ConexionesBD.closeConnection(conn,null, rs, call);
//		}
//		
//		return ret;
//	}

	@Override
	public boolean apptUpTareaAlumno(String comentarioAlu, Date taFEntregaA,
			String taContenido, long idTarea)throws SQLException {
		boolean ret = false;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_tUp_TareaAlumno ?, ?, ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setString(1, comentarioAlu);
			call.setTimestamp(2,new Timestamp(df.parse(df.format(taFEntregaA)).getTime()));			
			call.setString(3, taContenido);
			call.setLong(4, idTarea);
			ret = call.execute();
			
		}catch(SQLException | ParseException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn,null, rs, call);
		}
		
		return ret;
	}

	@Override
	public boolean apptUpTareaAlumnoTutor(String comentarioTut, long idTarea)throws SQLException {
		boolean ret = false;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_tUp_TareaAlumnoTutor ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idTarea);
			call.setString(2, comentarioTut);			
			ret = call.execute();
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn,null, rs, call);
		}
		
		return ret;
	}

//	@Override
//	public ArchivoAdjunto getAdjuntos(String userId, long idTarea, String tipoUsuario)
//			throws SQLException {
//		ArchivoAdjunto ret = null;
//		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_getAAdjuntos ?, ?, ?";
//		ResultSet rs = null;
//		CallableStatement call = null;
//		Connection conn= null;
//		try{
//			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
//			call = conn.prepareCall(sqlQuery);
//			call.setString(1, userId);
//			call.setLong(2, idTarea);
//			call.setString(3, tipoUsuario);
//			rs = call.executeQuery();
//			while(rs.next()){
//				ret = new ArchivoAdjunto();
//				ret.setArchivoAdjunto(rs.getString("AAdjunto"));
//				ret.setIdArchivo(rs.getLong("IDAAdjunto"));
//				ret.setHtmlString(rs.getString("AAdjunto"));
//							
//			}
//		}catch(SQLException e){
//			logger.error(e.getMessage(), e);
//		}finally{
//			ConexionesBD.closeConnection(conn,null, rs, call);
//		}
//		return ret;
//	}

	@Override
	public boolean upInsertTarea(String userId, long idTarea, long idAlumno) throws SQLException {
		boolean ret = false;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_acin_TareaRevisada ?, ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idTarea);
			call.setString(2, userId);
			call.setLong(3, idAlumno);
			call.execute();				
			logger.info("Se invoca el stored app_acin_TareaRevisada");
//			Se actualiza
			this.insertTarea(userId, idTarea, idAlumno);
				
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn,null, rs, call);
		}
		
		return ret;
	}
	
	
	private boolean insertTarea(String userId, long idTarea, long idAlumno) throws SQLException {
		
		boolean ret = false;
		String sqlQuery = "Exec "+pre.getBase()+".dbo.app_acup_TareaRevisada ?, ?, ?";
		ResultSet rs = null;
		CallableStatement call = null;
		Connection conn= null;
		try{
			conn = ConexionesBD.getConnection(pre.getInstanciaBD(), pre.getPuertoBD(), pre.getUsrBD(), pre.getPassBD(), pre.getCatalogoBD());
			call = conn.prepareCall(sqlQuery);
			call.setLong(1, idTarea);
			call.setString(2, userId);
			call.setLong(3, idAlumno);
			logger.info("Se invoca el stored app_acup_TareaRevisada");
			ret = call.execute();
			
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			ConexionesBD.closeConnection(conn,null, rs, call);
		}
		
		return ret;
		
	}
	
	
	

	
}
