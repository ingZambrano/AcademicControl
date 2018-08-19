package com.avior.academic.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.avior.academic.pojo.AppUser;
import com.avior.academic.pojo.FCMNotification;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.avior.academic.pojo.FirebaseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by jairo on 14/06/16.
 */
@Repository("firebaseCloudMessaging")
public class FirebaseMessaging {

    @Autowired
    @Qualifier("AppUserDataService")
    AppUserService userData;

    // [https://firebase.google.com/docs/cloud-messaging/http-server-ref#error-codes]

    private final String FCM_API_KEY = "AIzaSyC2h_a7A96AeFi9l2bMT042QL78HkSEFuw";
    private final String FIREBASE_URL = "https://fcm.googleapis.com/fcm/send";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void send(FirebaseMessage msg){
        try {
            ObjectMapper mapper = new ObjectMapper();
            //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost request = new HttpPost(FIREBASE_URL);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "key=" + FCM_API_KEY);

            StringEntity body = new StringEntity(mapper.writeValueAsString(msg));
            logger.info("Peticion: " + mapper.writeValueAsString(msg));

            request.setEntity(body);

            CloseableHttpResponse response = client.execute(request);
            logger.info("Respuesta recibida:" + response.getStatusLine());
            Header[] hdrs = response.getAllHeaders();
            for(Header h : hdrs){
                logger.info(h.toString());
            }

        }catch (JsonProcessingException e){
            logger.error("Error al serializar petición a Firebase", e);
        }catch (UnsupportedEncodingException e){
            logger.error("Error al serializar petición a Firebase", e);
        }catch (IOException e){
            logger.error("Error al enviar petición a Firebase", e);
        }
    }


    /**
     * Compone un mensaje del tipo Tarea y lo envia al usuario especificado.
     * @param username El nombre del usuario al que se le va a enviar la notificación
     * @throws Exception Cuando el usuario no se haya registrado para recibir notificaciones
     */
    public void sendHomeworkTo(String username, Long idEscuela, String vencimiento){
        FirebaseMessage msg = new FirebaseMessage();
        try{
            AppUser user = userData.getAppUserData(username, idEscuela);

            if(user.getToken() == null || user.getToken().equals("")) throw new Exception("Usuario no registrado en FCM");

            msg.setTo(user.getToken());
            //Cuando se tenga un POJO con los datos como vencimiento de la tarea se envia en la estructura
            //msg.setData(OBJETO);

            FCMNotification notif = new FCMNotification("¡Hay nueva tarea!", "Vence: " + vencimiento);
            msg.setNotification(notif);
            this.send(msg);

        }catch (Exception e){
            logger.error("No se pudo enviar la notificacion");
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }
    
    public void sendMessage(String mensaje, String token){
    	FirebaseMessage msg = new FirebaseMessage();
    	msg.setTo(token);
    	FCMNotification notif = new FCMNotification("¡Notificacion!",mensaje);
        msg.setNotification(notif);
        this.send(msg);
    	
    	
    }
}
