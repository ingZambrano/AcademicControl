package com.avior.academic.service;

import com.avior.academic.pojo.AppUser;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jairo on 27/07/16.
 */
public interface AppUserService {

    public AppUser getAppUserData(String email, Long idEscuela) throws Exception;
    public void updateToken(String email, String token, Long idEscuela) throws SQLException;
    public void updateAllTokens(String email, String token) throws SQLException;
    public List<AppUser> getAppUserData(String email) throws Exception;
    public List<AppUser> dameListaUsuariosXEscuela(long idEscuela) throws Exception;
}
