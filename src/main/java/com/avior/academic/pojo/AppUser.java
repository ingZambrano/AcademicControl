package com.avior.academic.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jairo on 27/07/16.
 */
public class AppUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Id unico dentro de esta tabla
     */
    private Long idUsers;


    /**
     * Correo electronico del usuario. Es inconsistente en datos sin registrar
     */
    private String email;

    /**
     * El token de Firebase a manejar
     */
    private String token;

    /**
     * Boolean que indica si tiene sesión abierta. No se sabe si se debe modificar o no
     */
    private boolean sesionAbierta;

    /**
     * Fecha del ultimo acceso, está en NULL en todas las columnas
     */
    //private Timestamp ultimoLogeo;

    /**
     * Se debería guardar aquí que dispositivo usó para el acceso????
     */
    private String dispositivo;

    /**
     * Escuela a la que pertenece
     */
    private Long idEscuela;

    /**
     * Alumno, Familiar o Staff
     */
    private String tipo;

    private List<String> FCMTokens;

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Long idUsers) {
        this.idUsers = idUsers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSesionAbierta() {
        return sesionAbierta;
    }

    public void setSesionAbierta(boolean sesionAbierta) {
        this.sesionAbierta = sesionAbierta;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Long getIdEscuela() {
        return idEscuela;
    }

    public void setIdEscuela(Long idEscuela) {
        this.idEscuela = idEscuela;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<String> getFCMTokens(){
    	return FCMTokens; 
    }
    
    public void setFCMTokens(List<String> tokens){
        this.FCMTokens = tokens;
    }
    public void setFCMTokens(String sqlValue){
        if(sqlValue != null && !sqlValue.equals(""))
            this.FCMTokens = new ArrayList<String>(Arrays.asList(sqlValue.split("·")));
    }

    public String getSerializedFCMTokens(){
        StringBuilder sb = new StringBuilder();
        try{
	        for(String s : this.FCMTokens){
	            sb.append(s);
	            sb.append("·");
	        }
        }catch(NullPointerException e){
        	e.printStackTrace();
        }
        return sb.toString();
    }

    public void addTokenToList(String token){
        if(this.FCMTokens == null){
            this.FCMTokens = new ArrayList<String>();
        }

        if(token == null || token.isEmpty())
            return;

        this.FCMTokens.add(token);
    }

    /**
     * Elimina el token de la lista interna de este objeto, en caso de eliminarlo efectivamente regresa "true"
     * @param token El token a encontrar y eliminar
     * @return true si el token fue encontrado y eliminado, false de otro modo
     */
    public boolean removeTokenFromList(String token){
        if(this.FCMTokens == null)
            return false;

        if(token == null || token.isEmpty())
            return false;

        return this.FCMTokens.removeAll(Collections.singleton(token));

    }
}
