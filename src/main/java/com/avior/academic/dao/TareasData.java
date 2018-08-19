package com.avior.academic.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.avior.academic.pojo.Materia;
import com.avior.academic.pojo.TareaContesta;
import com.avior.academic.pojo.TareaMateriaGrupo;
import com.avior.academic.pojo.Tarea;

public interface TareasData {
	
	 
	public long getIdGrupoXIdAlumno(long idAlumno) throws SQLException;	
	public List<Materia> getMateriaXGrupo(long idGrupo) throws SQLException;
	public List<TareaMateriaGrupo> getTareasXMateriaT(String desde, String hasta, String claveMat, long iDAlumno, String userId) throws SQLException;
	public List<TareaMateriaGrupo> getTareasXMateriaA(String desde, String hasta, String claveMat, String userId) throws SQLException;
	public Tarea getTareaXIdDet(long idTarea) throws SQLException;
	public TareaContesta getTareaContesta(long idTarea, String userId, String tipoUsuario) throws SQLException;
//	public boolean acupTareaRevisada(long idTarea, String userId, long idAlumno ) throws SQLException;
//	public boolean acinTareaRevisada(long idTarea, String userId, long idAlumno ) throws SQLException;
	public boolean apptUpTareaAlumno(String comentarioAlu, Date taFEntregaA, String taContenido, long idTarea)throws SQLException;
	public boolean apptUpTareaAlumnoTutor(String comentarioTut, long idTarea)throws SQLException;
//	public ArchivoAdjunto getAdjuntos(String userId, long idTarea, String tipoUsuario)throws SQLException;
	public boolean upInsertTarea(String userId, long idTarea, long idAlumno)throws SQLException;
	
	
	
}
