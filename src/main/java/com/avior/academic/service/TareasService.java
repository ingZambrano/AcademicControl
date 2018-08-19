package com.avior.academic.service;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.Tarea;
import com.avior.academic.pojo.TareaContesta;
import com.avior.academic.pojo.TareaMateriaGrupo;

public interface TareasService {

	public List<TareaMateriaGrupo> getTareasXMateriaT(String desde,String hasta, long idAlumno, String userId)
			throws SQLException;
	public Tarea getTareaXIdDet(long idTarea, String userId,String tipoUsuario, long idAlumno) throws SQLException;
	public TareaContesta getComentarios(long idTarea, String userId,String tipoUsuario)throws SQLException;
	public Boolean updateComentario(String comentario, String userId, long idTarea, String comentarioDetalle)throws SQLException;
	
}
