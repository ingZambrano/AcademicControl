package com.avior.academic.pojo;

import java.io.Serializable;

/**
 * Created by jairo on 20/06/16.
 */
public class FCMNotification implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private String body;
    private String title;
    private String icon;
    private String sound;

    public FCMNotification(String title, String body){
        super();
        this.body = body;
        this.title = title;
    }

    public FCMNotification(){}

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setSound(String sound){this.sound = sound; }

    public String getSound(){ return this.sound; }
}
