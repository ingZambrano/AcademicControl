package com.avior.academic.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avior.academic.dao.TareasData;
import com.avior.academic.dao.UserData;
import com.avior.academic.pojo.ArchivoAdjunto;
import com.avior.academic.pojo.Materia;
import com.avior.academic.pojo.Tarea;
import com.avior.academic.pojo.TareaContesta;
import com.avior.academic.pojo.TareaMateriaGrupo;
import com.avior.academic.pojo.UserInfo;


@Service("tareasServiceImpl")
public class TareasServiceImpl implements TareasService {
	@Autowired
	@Qualifier("tareasDataImp")
	TareasData tareasDataImp;
	
	@Autowired
	@Qualifier("userDataImp")
	UserData usrDataImp;	
	
	
	
	@Override
	public List<TareaMateriaGrupo> getTareasXMateriaT(String desde,
			String hasta, long idAlumno, String userId)
			throws SQLException {
		List<TareaMateriaGrupo> ret = new ArrayList<TareaMateriaGrupo>();
		UserInfo userInfo = usrDataImp.getUserbyId(userId);
		//Obtener el grupo en el que se encuentra el alumno		
		long idGrupo = tareasDataImp.getIdGrupoXIdAlumno(idAlumno);
		//Obtener la lista de materias de acuerdo al grupo del alumno
		List<Materia> materias = tareasDataImp.getMateriaXGrupo(idGrupo);
		//Buscar las tareas que haya por materia
		List<TareaMateriaGrupo> tareaXMateria;
		for(Materia m: materias){
			
			if(userInfo.getRole().equals("Alumno")){
				tareaXMateria = tareasDataImp.getTareasXMateriaA(desde, hasta, m.getClaveMat(), userId);
			}else{
				tareaXMateria = tareasDataImp.getTareasXMateriaT(desde, hasta, m.getClaveMat(), idAlumno, userId);
			}
			
			if(tareaXMateria.isEmpty() || tareaXMateria == null){
				continue;
			}else{
				ret.addAll(tareaXMateria);
			}
		}
		
		return ret;
	}

	@Override
	public Tarea getTareaXIdDet(long idTarea, String userId,
			String tipoUsuario, long idAlumno) throws SQLException {
		Tarea ret = tareasDataImp.getTareaXIdDet(idTarea); 	
//		ret.setArchivoAdjunto(tareasDataImp.getAdjuntos(userId, idTarea, tipoUsuario));
		ArchivoAdjunto adjunto = new ArchivoAdjunto();
		adjunto.setHtmlString(ret.gettDescripcionT());
		adjunto.setArchivoAdjunto(ret.gettDescripcionT());
		adjunto.setIdArchivo(0);
		ret.setArchivoAdjunto(adjunto);
		
		ret.settDescripcionT("");
		//Se actualiza el estatus de la tarea
		tareasDataImp.upInsertTarea(userId, idTarea, idAlumno);
		
		return ret;
	}

	@Override
	public TareaContesta getComentarios(long idTarea, String userId,
			String tipoUsuario) throws SQLException {
		
		return tareasDataImp.getTareaContesta(idTarea, userId, tipoUsuario);
	}

	@Override
	public Boolean updateComentario(String comentario, String userId, long idTarea, String comentarioDetalle)
			throws SQLException {
		UserInfo userInfo = usrDataImp.getUserbyId(userId);
		if(userInfo.getRole().equals("Alumno")){
			return tareasDataImp.apptUpTareaAlumno(comentario, new Date(),comentarioDetalle, idTarea);
		}else{
			return tareasDataImp.apptUpTareaAlumnoTutor(comentario, idTarea);
		}
	}
	
	
	
	

}
