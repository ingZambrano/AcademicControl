package com.avior.academic.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avior.academic.dao.PreloginData;
import com.avior.academic.pojo.PreLogin;


/**
 * 
 * @author jozambrano
 *
 */
@Service("preLoginServiceImp")
public class PreLoginServiceImp implements PreLoginService {
	
	@Autowired
	@Qualifier("preloginDataImp")
	PreloginData preloginDataImp;

	@SuppressWarnings("unchecked")
	@Override
	public List<PreLogin> getPreloginObject(String eMail) throws SQLException {
		return (List<PreLogin>)ServicioGenerico.changeNullValues(preloginDataImp.getPreloginObject(eMail));
	}

	@Override
	public PreLogin getPreloginObjectSingle(String eMail, int idEscuela)
			throws SQLException {
		// TODO Auto-generated method stub
		return preloginDataImp.getPreloginObjectSingle(eMail, idEscuela);
	}

}
