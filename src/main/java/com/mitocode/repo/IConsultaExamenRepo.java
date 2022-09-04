package com.mitocode.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.ConsultaExamen;

public interface IConsultaExamenRepo extends IGenericRepo<ConsultaExamen, Integer> {

	// es para insert , update y delete

	//@Transactional
	@Modifying
	@Query(value = "INSERT INTO consulta_examen(id_consulta,id_examen) VALUES (:idConsulta, :idExamen)", nativeQuery = true)
	Integer registrar(@Param("idConsulta") Integer idConsulta, @Param("idExamen") Integer idExamen);
}
