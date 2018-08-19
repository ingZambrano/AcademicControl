package com.avior.academic.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avior.academic.comunes.Cifrador;
import com.avior.academic.dao.UserData;
import com.avior.academic.pojo.UserInfo;
import com.avior.academic.pojo.UserPadreHijo;


@Service("userDataServiceImp")
public class UserDataServiceImp implements UserDataService {
	
	
	@Autowired
	@Qualifier("userDataImp")
	UserData usrDataImp;	
	
//	@Autowired
//	@Qualifier("preloginDataImp")
//	PreloginData preloginDataImp;

	@Override
	public String getUserIdByUserName(String userName) throws Exception {
		
		String ret = usrDataImp.getUserIdByUserName(userName); 
		
		return ret;
	}

	@Override
	public UserInfo getUserbyId(String idUsuario) throws SQLException {
		
		UserInfo ret;		
		ret = (UserInfo) ServicioGenerico.changeNullValues(usrDataImp.getUserbyId(idUsuario));
		
		String userName = ret.getNombreUser();
		ret.setPathPhotoUser(usrDataImp.getPathFotoUser(userName));
		
		
		return ret;
	}

	@Override
	public List<UserPadreHijo> getPadresHijos(String idUsuario)
			throws SQLException {
		
		List<UserPadreHijo> ret = new ArrayList<UserPadreHijo>();
		for(UserPadreHijo u : usrDataImp.getPadresHijos(idUsuario)){
			ret.add((UserPadreHijo)ServicioGenerico.changeNullValues(u));
		}
		
		return ret;
	}
	@Override
	public List<UserPadreHijo> getInfoAlumno(String idUsuario)
			throws SQLException {
		List<UserPadreHijo> ret = new ArrayList<UserPadreHijo>();
		for(UserPadreHijo u : usrDataImp.getInfoAlumno(idUsuario)){
			ret.add((UserPadreHijo)ServicioGenerico.changeNullValues(u));
		}
		
		return ret;
	}
	

	@Override
	public String getToken(String user, String eMail, int idEscuela) {
		
		return Cifrador.encriptar(user+"|||"+eMail+"|||"+idEscuela);
	}

	

}
