package com.avior.academic.controller;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avior.academic.pojo.VersionServicios;

import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Properties;

/**
 * Created by jairo on 31/08/16.
 */
@Controller
public class LogController {

    private String logFile="";

    private static final Logger logger = Logger.getLogger(LogController.class);
    
    @Autowired
    VersionServicios versionServicios;
    
    @RequestMapping(value="/datosServicio", method = RequestMethod.GET)
    public  @ResponseBody VersionServicios getDatosServicio(){
    	return this.versionServicios;
    }

    @ApiIgnore
    @RequestMapping(value="/dllogs", method = RequestMethod.GET)
    public void getLogsFromServer(@RequestParam(required = false)String secret,
                                  HttpServletResponse response){
        if(logFile.equals("")){
            Properties props = new Properties();
            InputStream is = getClass().getClassLoader().getResourceAsStream("log4j.properties");
            if(is != null){
                try {
                    props.load(is);
                    logFile = props.getProperty("log4j.appender.file.File");
                }catch(IOException e){
                    logger.error("ERROR AL LEER ARCHIVO DE CONFIGURACION DE LOG4J");
                }

                logger.info("El archivo de log esta en " + logFile);
            }else{
                logger.error("CONFIGURACION DE LOG NO ENCONTRADA");
            }

        }

        if(secret == null){
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            return;
        }

        if(secret.equals("AviorRifaYControla")){
            if(!logFile.equals("")){
                try {
                    InputStream log = new FileInputStream(logFile);
                    response.addHeader("Content-Disposition", "attachment;filename=logs.txt");
                    response.setContentType("txt/plain");
                    IOUtils.copy(log, response.getOutputStream());
                    response.flushBuffer();
                }catch(FileNotFoundException ex){
                    logger.error("El archivo de logs no se encontró");
                }catch (IOException e){
                    logger.error("Error al leer el archivo de log", e);
                }
            }else{
                try {
                    PrintWriter pw = response.getWriter();
                    pw.print("Error: No se ha definido el archivo de log");
                    pw.flush();
                }catch (IOException e){
                    logger.error("Error al enviar respuesta.", e);
                }
            }
        }else{
            response.setStatus(HttpStatus.SC_NOT_FOUND);
        }

        return;
    }
}
