package com.biblioteca.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Usuario;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>{

	Emprestimo findByEmprestimoId(Long emprestimoId);
	
	List<Emprestimo> findByUsuario(Usuario usuario);
	
	List<Emprestimo> findByEmprestimoDataDevolucaoBefore(Date date); 
	
}
