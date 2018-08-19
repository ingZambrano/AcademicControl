package com.avior.academic.dao;

import com.avior.academic.pojo.AppUser;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jairo on 27/07/16.
 */
public interface AppUserData {

    public AppUser getUserData(String email, Long idEscuela) throws Exception;
    public void saveToken(String username, String token, Long idEscuela) throws SQLException;
    public void saveTokenToAll(String email, String token) throws SQLException;
    public List<AppUser> readAllUserData(String email) throws Exception;
    public List<AppUser> getUserToken(long idEscuela)throws Exception;
    public void removeToken(AppUser appUser) throws Exception;    
    
}
