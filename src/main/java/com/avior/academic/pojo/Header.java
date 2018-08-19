package com.avior.academic.pojo;

import java.io.Serializable;
import java.util.Map;

import com.avior.academic.comunes.ConstantesResponse;

public class Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String responseCode;
	private String responseMessage;
	
	
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public static Header getHeader(String logCode){
		Header h = new Header();
		for(Map.Entry<String, String> e: ConstantesResponse.codeMap.entrySet()){
			if(e.getKey().equals(logCode)){
				h.setResponseCode(e.getKey());
				h.setResponseMessage(e.getValue());
				return h;
			}
		}
				
		h.setResponseCode("0000");
		h.setResponseMessage("Respuesta invalida");
		return h;
	}
	
	
	

}
