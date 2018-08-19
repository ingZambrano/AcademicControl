package com.avior.academic.dao;

import java.sql.SQLException;
import java.util.List;

import com.avior.academic.pojo.PreLogin;

public interface PreloginData {
	

	public List<PreLogin> getPreloginObject(String eMail) throws SQLException;
	public PreLogin getPreloginObjectSingle(String eMail, int idEscuela) throws SQLException;
	public List<String> getTokensUsuariosNoti(List<String> listaEmails, long idEscuela) throws SQLException;
	public PreLogin createPrelogin(long idEscuela) throws SQLException;

}
