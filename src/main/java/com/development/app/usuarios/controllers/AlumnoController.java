package com.development.app.usuarios.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.development.app.commons.alumnos.models.entity.Alumno;
import com.development.app.usuarios.services.AlumnoService;
import com.development.commons.controllers.CommonController;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id){
		Optional<Alumno> optional = service.findById(id);
		
		if(optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Resource imagen = new ByteArrayResource(optional.get().getFoto());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
		
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, @PathVariable Long id, BindingResult result){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Alumno> optional = service.findById(id);
		
		if(optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Alumno alumnoModificado = optional.get();
		alumnoModificado.setNombre(alumno.getNombre());
		alumnoModificado.setApellido(alumno.getApellido());
		alumnoModificado.setEmail(alumno.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoModificado));
		
	}
	

	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombreOApellido(term));
	}
	
	@PostMapping("/crear-con-foto")	
	public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException{
		if(!archivo.isEmpty()) {
			alumno.setFoto(archivo.getBytes());
		}
		return super.crear(alumno, result);
	}
	
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> editarConFoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id, 
			@RequestParam MultipartFile archivo) throws IOException{
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Alumno> optional = service.findById(id);
		
		if(optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Alumno alumnoModificado = optional.get();
		alumnoModificado.setNombre(alumno.getNombre());
		alumnoModificado.setApellido(alumno.getApellido());
		alumnoModificado.setEmail(alumno.getEmail());
		
		if(!archivo.isEmpty()) {
			alumnoModificado.setFoto(archivo.getBytes());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoModificado));
		
	}
	
}
