package com.avior.academic.dao;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.UserInfo;
import com.avior.academic.pojo.UserPadreHijo;


public interface UserData {
	
	
	public String getUserIdByUserName(String userName) throws Exception;	
	public UserInfo getUserbyId(String idUsuario) throws SQLException;
	public List<UserPadreHijo> getPadresHijos(String idUsuario) throws SQLException;
	public List<UserPadreHijo> getInfoAlumno(String idUsuario)throws SQLException;
	public byte[] getPhotoByUserId(String idUsuario)throws SQLException;
	public String getPathFotoUser(String userName) throws SQLException;
	
	
}
