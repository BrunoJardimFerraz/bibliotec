package com.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.Obra;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long>{
	
	Obra findByObraId(Long obraId);
	
	List<Obra> findByObraEmprestadoFalseAndObraReservadoFalse();
	
	Obra findFirstByOrderByObraTomboDesc();
	
	@Query("select o from Obra o where o.obraTitulo like %?1% or o.obraSubtitulo like %?1%  or o.obraTipoDocumento like %?1%  or o.obraTotalPagina like %?1%  or o.obraCidadePublicacao like %?1%  or o.obraNumeroEdicao like %?1%  or o.obraDataPublicacao like %?1%  or o.obraPalavraChave like %?1%  or o.obraEditora like %?1%  or o.obraAutor like %?1%  or o.obraIsbn like %?1%  or o.obraCdu like %?1%  or o.obraPha like %?1%  or o.obraTombo like %?1%  or o.obraEmprestado like %?1% or o.obraReservado like %?1%")
	List<Obra> findQualquer(String busca);
} 
