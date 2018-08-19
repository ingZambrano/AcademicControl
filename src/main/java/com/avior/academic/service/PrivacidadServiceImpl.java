package com.avior.academic.service;

import com.avior.academic.dao.PrivacidadData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Obtiene el aviso de prvacidad desde el DAO correspondiente.
 * Created by jairo on 18/08/16.
 */
@Service("PrivacidadService")
public class PrivacidadServiceImpl implements PrivacidadService {

    private final Logger logger = Logger.getLogger(this.getClass());


    @Autowired
    @Qualifier("AvisoDePrivacidad")
    PrivacidadData privacidadDAO;

    @Override
    public String getAvisoPrivacidad(){
        String aviso = "";
        try{
            aviso = privacidadDAO.getCurrentAvisoPrivacidad();
        }catch(SQLException e){
            logger.trace("Error al obtener el aviso de privacidad", e);
            aviso = "Ha ocurrido un error inesperado. Contacte a AcademicControl para notificar de este error.";
        }
        return aviso;
    }

    @Override
    public String getTerminosServicio(){
        String tos = "";
        try{
            tos = privacidadDAO.getTerminosServicio();
        }catch(SQLException e){
            logger.trace("Error al obtener los terminos de servicio", e);
            tos = "Ha ocurrido un error al obtener los terminos de servicio. Favor de contactar a AcademicControl para notificar este problema";
        }
        return tos;
    }
}
