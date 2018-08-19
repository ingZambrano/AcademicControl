package com.avior.academic.comunes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GeneradorFechas {
	
	private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");		
	
	public static String generarFechaDesde(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		//Restar una semana, siete dias
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)-7);
		String desde = df.format(cal.getTime());	
		
		return desde;
	}
	
	public static String generarFechaHasta(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());		
		//Sumar dos semanas, catorce dias
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)+35);
		String hasta = df.format(cal.getTime());
		
		return hasta;
	}

}
