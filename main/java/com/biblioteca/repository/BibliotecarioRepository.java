package com.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.biblioteca.entities.Bibliotecario;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {

	Bibliotecario findByBibliotecarioEmail(String bibliotecarioLogin);
	
	Bibliotecario findByBibliotecarioId(Long bibliotecarioId);
	
	@Query("select b from Bibliotecario b where b.bibliotecarioNome like %?1% or b.bibliotecarioEmail like %?1% or b.bibliotecarioCelular like %?1% or b.bibliotecarioTurno like %?1%")
	List<Bibliotecario> findQualquer(String busca);
}
