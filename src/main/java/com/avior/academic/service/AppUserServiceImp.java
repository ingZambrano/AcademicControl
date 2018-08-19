package com.avior.academic.service;

import com.avior.academic.dao.AppUserDataImp;
import com.avior.academic.pojo.AppUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jairo on 27/07/16.
 */
@Service("AppUserDataService")
public class AppUserServiceImp implements AppUserService{

    @Autowired
    @Qualifier("appUserDataImp")
    AppUserDataImp userData;


    @Override
    public AppUser getAppUserData(String username, Long idEscuela) throws Exception {
        return userData.getUserData(username, idEscuela);
    }

    @Override
    public void updateToken(String username, String token, Long idEscuela) throws SQLException {
        userData.saveToken(username, token, idEscuela);
    }

    @Override
    public void updateAllTokens(String email, String token) throws SQLException {
        userData.saveTokenToAll(email, token);
    }

    @Override
    public List<AppUser> getAppUserData(String email) throws Exception {
        return userData.readAllUserData(email);
    }

	@Override
	public List<AppUser> dameListaUsuariosXEscuela(long idEscuela) throws Exception {
		return userData.getUserToken(idEscuela);	
		
		
	}
}
