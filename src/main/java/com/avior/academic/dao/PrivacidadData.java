package com.avior.academic.dao;

import java.sql.SQLException;

/**
 * Created by jairo on 18/08/16.
 */
public interface PrivacidadData {
    public String getCurrentAvisoPrivacidad() throws SQLException;
    public String getTerminosServicio() throws SQLException;
}
