package com.avior.academic.pojo;

import java.io.Serializable;

public class ResponseAvior<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Header header;
	private T responsData;
	
	
	
	
	public ResponseAvior(Header header) {
		super();
		this.header = header;
	}
	
	
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}


	public T getResponsData() {
		return responsData;
	}


	public void setResponsData(T responsData) {
		this.responsData = responsData;
	}

	
	
	
	
	

}
