package com.avior.academic.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avior.academic.dao.FinanzasData;
import com.avior.academic.dao.UserData;
import com.avior.academic.pojo.CnsCobAbonos;
import com.avior.academic.pojo.CobroCargosXAlumno;

@Service("finanzasServiceImp")
public class FinanzasServiceImp implements FinanzasService {
	
		
	@Autowired
	@Qualifier("finanzasDataImp")
	FinanzasData finanzasDataImp;
	
	@Autowired
	@Qualifier("userDataImp")
	UserData usrDataImp;

	@Override
	public List<CobroCargosXAlumno> getFinanzasXAlumno(long idAlumno)
			throws SQLException {
		List<CobroCargosXAlumno> ret = new ArrayList<CobroCargosXAlumno>();
		
		for(CobroCargosXAlumno c : finanzasDataImp.getFinanzasXAlumno(idAlumno)){
			ret.add((CobroCargosXAlumno)ServicioGenerico.changeNullValues(c));
		}		
		return ret;
	}

	@Override
	public CnsCobAbonos getDetalleMonto(long idCargo) throws SQLException {
		
		return (CnsCobAbonos)ServicioGenerico.changeNullValues(finanzasDataImp.getDetalleMonto(idCargo));
	}
	
	
}
