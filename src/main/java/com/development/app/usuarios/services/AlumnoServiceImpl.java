package com.development.app.usuarios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.development.app.commons.alumnos.models.entity.Alumno;
import com.development.app.usuarios.model.repository.AlumnoRepository;
import com.development.commons.services.CommonServiceImpl;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

	@Override
	@Transactional(readOnly=true)
	public List<Alumno> findByNombreOApellido(String term) {
		return repository.findByNombreOApellido(term);
	}


}
