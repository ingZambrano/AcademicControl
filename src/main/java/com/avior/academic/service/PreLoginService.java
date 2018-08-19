package com.avior.academic.service;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.PreLogin;

public interface PreLoginService {
	
	public List<PreLogin> getPreloginObject(String eMail) throws SQLException;
	public PreLogin getPreloginObjectSingle(String eMail, int idEscuela) throws SQLException;
}
