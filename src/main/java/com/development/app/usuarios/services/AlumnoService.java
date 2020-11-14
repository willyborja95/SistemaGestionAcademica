package com.development.app.usuarios.services;

import java.util.List;

import com.development.app.commons.alumnos.models.entity.Alumno;
import com.development.commons.services.CommonService;

public interface AlumnoService extends CommonService<Alumno>{
	
	public List<Alumno> findByNombreOApellido(String term);

}
