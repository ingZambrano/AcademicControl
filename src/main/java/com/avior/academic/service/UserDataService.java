package com.avior.academic.service;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.UserInfo;
import com.avior.academic.pojo.UserPadreHijo;



/**
 * 
 * @author jozambrano
 *
 */

public interface UserDataService {
	
	public String getUserIdByUserName(String userName) throws Exception;	
	public UserInfo getUserbyId(String idUsuario) throws SQLException;
	public List<UserPadreHijo> getPadresHijos(String idUsuario) throws SQLException;
	public List<UserPadreHijo> getInfoAlumno(String idUsuario) throws SQLException;
	public String getToken(String user, String eMail, int idEscuela);
	
	
}
