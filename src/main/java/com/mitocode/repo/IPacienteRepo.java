package com.mitocode.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitocode.model.Paciente;

public interface IPacienteRepo extends JpaRepository<Paciente, Integer>{

}
