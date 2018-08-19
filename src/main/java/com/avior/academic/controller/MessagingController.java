package com.avior.academic.controller;


import com.avior.academic.pojo.*;
import com.avior.academic.service.AppUserService;
import com.avior.academic.service.FirebaseMessaging;
import com.avior.academic.service.NotificacionService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.collections.list.SetUniqueList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jairo on 21/06/16.
 */
@RequestMapping("/fcm")
@RestController
public class MessagingController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("firebaseCloudMessaging")
    FirebaseMessaging messaging;

    @Autowired
    @Qualifier("AppUserDataService")
    AppUserService appUserService;

    @Autowired
    @Qualifier("notificacionServiceImp")
    NotificacionService notiService;

    @Autowired
    private PreLogin pre;


    /**
     * Se encarga de recibir un "usuario" y su "token", para almacenarlos en la base de datos. Debe de validar que NO
     * exista el usuario en la base, e insertar dichos valores
     * @param email
     * @param token
     * @return
     */
    @ApiOperation(value="/register", notes="Cada usuario se tiene que registrar para poder enviarle notificaciones")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", required = true, value="Email del usuario", dataType="String", paramType = "query"),
            @ApiImplicitParam(name = "token", required = true, value = "Token generado por Firebase", dataType = "String", paramType = "query")})
    @RequestMapping(value="/register", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAvior<String>> register(@RequestParam(value = "user", required = true)String email,
                                                          @RequestParam(value = "token", required = true) String token,
                                                          Authentication auth){
        try{
            if(token.equals("null")){
                log.warn("El usuario [" + pre.getEmail() + "] intento registrar un token DE FCM nulo");
                ResponseAvior<String> ret = new ResponseAvior<>(Header.getHeader("3334"));
                ret.getHeader().setResponseMessage("Token invalido");
                return ResponseEntity.badRequest().body(ret);
            }
            
            if(email.contains("@")){
            	log.info("Usuario Android");            	
            }else{
            	log.info("Usuario Iphone");
            }
            log.info("Usuario: "+email);
            log.info("Token: "+token);
            
            
            AppUser userData = appUserService.getAppUserData(pre.getEmail(), pre.getSchool().getIdEscuela());
            userData.addTokenToList(token);

            appUserService.updateAllTokens(pre.getEmail(), userData.getSerializedFCMTokens());
            ResponseAvior<String> ret = new ResponseAvior<String>(Header.getHeader("1111"));
            ret.getHeader().setResponseMessage("Usuario registrado");
            log.info("Registrando al usuario con mail: " + pre.getEmail() + " para recibir notificaciones [" + token +"]");
            return ResponseEntity.ok(ret);
        }catch(SQLException ex){
            ResponseAvior<String> reterr = new ResponseAvior<String>(Header.getHeader("3333"));
            reterr.getHeader().setResponseMessage("Usuario no encontrado");
            return ResponseEntity.ok(reterr);
        }catch(Exception e){
            log.error("Error al procesar alta de usuario.", e);
            ResponseAvior<String> ret = new ResponseAvior<String>(Header.getHeader("3335"));
            ret.getHeader().setResponseMessage("Usuario no encontrado");
            return ResponseEntity.badRequest().body(ret);
        }
    }


    /**
     * En caso de que un usuario no quiera recibir notificaciones, se borra de la base de datos si y solo si coinciden
     * usuario y token.
     * @param email del usuario que se da de baja del servicio de notificaciones
     * @return
     */
    @ApiOperation(value="/remove", notes="Elimina al usuario de la lista de notificaciones. Si quiere volver a recibir notificaciones, habrá que registrarse nuevamente")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", required = true, value="Email del usuario", dataType="String", paramType = "query"),
                        @ApiImplicitParam(name = "fcmtoken", required = false, value = "Token de FCM", dataType = "String", paramType = "query")})
    @RequestMapping(value="/remove", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAvior<String>> delete(@RequestParam(value = "user")String email, @RequestParam(value="fcmtoken", required=false) String fcmtoken){

        try{
        	
        	if(email.contains("@")){
            	log.info("Usuario Android");            	
            }else{
            	log.info("Usuario Iphone");
            }
            log.info("Usuario: "+email);
            log.info("Token: "+fcmtoken);
//            AppUser userData = appUserService.getAppUserData(pre.getEmail(), pre.getSchool().getIdEscuela());
//            userData.removeTokenFromList(fcmtoken);
//
//            appUserService.updateAllTokens(pre.getEmail(), userData.getSerializedFCMTokens());
            
            //Aqui remover el token de la base de datos
//            appUserService.removeToken(fcmtoken, pre.getSchool().getIdEscuela());
            AppUser userData = null; 
            log.info("IDEscuela del usuario que quiere remover su token fcm: "+pre.getSchool().getIdEscuela());
            List<AppUser> listAppUser = appUserService.dameListaUsuariosXEscuela(pre.getSchool().getIdEscuela());
            for(AppUser a: listAppUser){
            	List<String> listTokens = a.getFCMTokens();
            	log.info("Usuario:"+ a.getEmail());
            	
            	try{
	            	if(!listTokens.isEmpty()){
	            		log.info("Tamaño de lista de tokens: "+ listTokens.size());
		            	for(String s: a.getFCMTokens()){
		            		if(s.equals(fcmtoken)){
		            			a.removeTokenFromList(fcmtoken);
		            			userData = a;
		            			break;
		            		}
		            	}
	            	}
            	}catch(NullPointerException e){
            		log.error("Error: ",e);
            	}
            }
            
            if(userData != null){
            	appUserService.updateAllTokens(pre.getEmail(), userData.getSerializedFCMTokens());
            }
            ResponseAvior<String> ret = new ResponseAvior<String>(Header.getHeader("1111"));
            ret.getHeader().setResponseMessage("Usuario eliminado de notificaciones");
            log.info("[FCM] Removiendo al usuario con mail: " + pre.getEmail() + " para recibir notificaciones, con token FCM:" + fcmtoken);
            return ResponseEntity.ok(ret);
        }catch(SQLException ex){
            ResponseAvior<String> reterr = new ResponseAvior<String>(Header.getHeader("3333"));
            reterr.getHeader().setResponseMessage("Usuario no encontrado");
            log.error("[FCM] Ocurrio un error al remover usuario de notificaciones: " + ex.getMessage());
            return ResponseEntity.ok(reterr);
        }catch(Exception e){
            log.error("Error al procesar baja de usuario.", e);
            ResponseAvior<String> ret = new ResponseAvior<String>(Header.getHeader("3335"));
            ret.getHeader().setResponseMessage("Error al procesar la peticion");
            return ResponseEntity.badRequest().body(ret);
        }
    }


    @SuppressWarnings("unchecked")
	@ApiOperation(value="notify", notes = "Notifica al sistema web de un nuevo evento para el cual debiera mandar una notificación a los usuarios pertinentes")
    @ApiImplicitParams({@ApiImplicitParam(name = "idEscuela", required = true, value="ID de la escuela originadora", dataType="Long", paramType = "query"),
            @ApiImplicitParam(name = "idEvento", required = true, value="ID del evento que se notificará", dataType="Long", paramType = "query"),
            @ApiImplicitParam(name = "tipo", required = true, value="[Tarea] ó [Aviso], que tipo de evento corresponde", dataType="String", paramType = "query")
    })
    @RequestMapping(value="/notify", method = RequestMethod.GET)
    public ResponseEntity<String> notifyNewHomework(@RequestParam(value = "idEscuela")Long idEscuela,
                                  @RequestParam(value = "idEvento") Long idEvento,
                                  @RequestParam(value = "tipo")     String tipo){

        log.debug("Notificacion [" + tipo + "] para la escuela [" + idEscuela + "] con el id [" + idEvento + "]");

        try{
            PreLogin pl = notiService.createPrelogin(idEscuela);
            log.info("Obteniendo evento...");
            Notificacion notificacion = notiService.getNotificacion(tipo, idEvento, pl);
            log.info("Obteniendo destinatarios del evento...");
            List<String> tokens = notiService.getTokensUsuariosNoti(tipo, idEvento, pl);
            log.info("Componiendo mensaje de Firebase");


            FirebaseMessage msg = new FirebaseMessage();
            FCMNotification content = new FCMNotification();

            //Armar el contenido de la notificacion que verá el usuario
            content.setTitle(tipo+": "+notificacion.getTitulo());
            content.setBody("Vence: " + notificacion.getfVencimiento());
            content.setIcon("ic_stat_notif");
            content.setSound("default");

            //Armar el resto del mensaje
            msg.setNotification(content);

            //Limpiamos duplicados
            log.info("Hay " + tokens.size() + " destinatarios");
            List<String> filteredTokens = SetUniqueList.decorate(new ArrayList<>(tokens));
            log.info("Ahora hay " + filteredTokens.size() + " despues de la filtracion y " + tokens.size() + " en la original");

            //Establecemos los destinatarios, (pueden ser hasta 1000 en el campo RegistrationIDs)
            List<String> temporaryBuffer = new ArrayList<>();

            for(String dest : filteredTokens){
                if(dest != null && !dest.equals("")){//No nulas o vacías
                    temporaryBuffer.add(dest);
                    if(temporaryBuffer.size() == 999){
                        msg.setRegistration_ids( temporaryBuffer.toArray(new String[0]));
                        messaging.send(msg);
                        temporaryBuffer.clear();
                    }
                }
            }

            if( ! temporaryBuffer.isEmpty() ){
                msg.setRegistration_ids( temporaryBuffer.toArray(new String[0]));
                messaging.send(msg);
                filteredTokens.clear();
            }else{
                log.info("No se encontraron desintatarios validos para notificacion [remamentes]");
            }

        }catch(SQLException e1){
            log.error("Error al invocar el servidor SQLServer", e1);
            return ResponseEntity.ok("ERROR");
        }catch(Exception e2){
            log.error("Error al procesar notificacion", e2);
            return ResponseEntity.ok("ERROR");
        }

        return ResponseEntity.ok("OK");
    }



    @ApiOperation(value="testMessaging", notes = "Envia un mensaje de prueba para Firebase Cloud Messaging (sólo visible en el log)")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", required = true, value="Email del usuario", dataType="String", paramType = "query"),
            @ApiImplicitParam(name = "idEscuela", required = true, value = "ID de Escuela del usuario", dataType = "Long", paramType = "query")
            })
    @RequestMapping(value = "/testMessaging", method = RequestMethod.GET)
    public void testMessaging(@RequestParam(value = "email")String email,
                              @RequestParam(value = "idEscuela") Long idEscuela){

        messaging.sendHomeworkTo(email, idEscuela, "12/08/2016");

    }

    @ApiOperation(value="token", notes="Lee el token del usuario en la escuela especificada")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", required = true, value = "Email del usuario", dataType = "String", paramType = "query"),
                        @ApiImplicitParam(name = "idescuela", required = true, value = "Id de la escuela", dataType = "Long", paramType = "query")})
    @RequestMapping(value="/token", method = RequestMethod.GET)
    public ResponseEntity<ResponseAvior<String>> getTokenAt(@RequestParam(value = "email")String email,
                                                            @RequestParam(value="idescuela") Long idescuela){
        String token = "placeholder";

        try {
            token = appUserService.getAppUserData(email, idescuela).getToken();


        }catch(SQLException sq){
            ResponseAvior<String> ret = new ResponseAvior<>(Header.getHeader("3333"));
            ret.getHeader().setResponseMessage(sq.getMessage());
            return ResponseEntity.badRequest().body(ret);
        }catch(Exception e){
            ResponseAvior<String> ret = new ResponseAvior<>(Header.getHeader("3334"));
            ret.getHeader().setResponseMessage(e.getMessage());
            return ResponseEntity.badRequest().body(ret);
        }

        ResponseAvior<String> ret = new ResponseAvior<>(Header.getHeader("1111"));
        ret.setResponsData(token);
        ret.getHeader().setResponseMessage("Completado exitosamente");
        return ResponseEntity.ok().body(ret);
    }


    @SuppressWarnings("rawtypes")
	@ApiOperation(value="tokens", notes="Devuelve todos los tokens del usuario")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", required = true, value = "Email del usuario", dataType = "String", paramType = "query") })
    @RequestMapping(value="/tokens", method = RequestMethod.GET)
    public ResponseEntity<ResponseAvior> getTokenAt(@RequestParam(value = "email")String email){

        List<AppUser> data;

        try {
            data = appUserService.getAppUserData(email);


        }catch(SQLException sq){
            ResponseAvior<String> ret = new ResponseAvior<>(Header.getHeader("3333"));
            ret.getHeader().setResponseMessage(sq.getMessage());
            return ResponseEntity.badRequest().body(ret);
        }catch(Exception e){
            ResponseAvior<String> ret = new ResponseAvior<>(Header.getHeader("3334"));
            ret.getHeader().setResponseMessage(e.getMessage());
            return ResponseEntity.badRequest().body(ret);
        }

        ResponseAvior<List<AppUser>> ret = new ResponseAvior<>(Header.getHeader("1111"));
        ret.setResponsData(data);
        return ResponseEntity.ok().body(ret);
    }
}
