package com.avior.academic.comunes;

import java.util.HashMap;
import java.util.Map;



public class ConstantesResponse {
	
	private static final String codeSuccess = "1111";	
	private static final String codeInvalidMail = "2222";
	private static final String codeDataNotFound = "3333";
	private static final String codeSQLError = "4444";
	private static final String codeInvalIdUser = "5555";
	
	private static final String msjSuccess = "Objeto encontrado";
	private static final String msjInvalidMail = "Mail no encontrado en base concentradora";
	private static final String msjDataNotFound = "No se encontraron registros en base de datos";
	private static final String msjCodeSQLError = "Excepcion ocurrida al ejecutar el proceso de base de datos";
	private static final String msjInvalIdUser = "El id de alumno no corresponde al usuario firmado";
	
	public static Map<String, String> codeMap = new HashMap<String, String>();
	
	static{
		ConstantesResponse.codeMap.put(ConstantesResponse.codeSuccess, ConstantesResponse.msjSuccess);
		ConstantesResponse.codeMap.put(ConstantesResponse.codeInvalidMail, ConstantesResponse.msjInvalidMail);
		ConstantesResponse.codeMap.put(ConstantesResponse.codeDataNotFound, ConstantesResponse.msjDataNotFound);
		ConstantesResponse.codeMap.put(ConstantesResponse.codeSQLError, ConstantesResponse.msjCodeSQLError);
		ConstantesResponse.codeMap.put(ConstantesResponse.codeInvalIdUser, ConstantesResponse.msjInvalIdUser);
	}
}
