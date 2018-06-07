package com.biblioteca.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.biblioteca.entities.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByUsuarioEmail(String usuarioEmail);

	public Usuario findByUsuarioNome(String UsuarioNome);
	
	public Usuario findByUsuarioId(Long usuarioId);

	public List<Usuario> findByUsuarioInatividadeExpirarBefore(Date date);
	
	@Query("select u from Usuario u where u.usuarioNome like %?1% or u.usuarioNumeroMatricula like %?1% or u.usuarioTurno like %?1% or u.usuarioEmail like %?1% or u.usuarioCelular like %?1% or u.usuarioCurso like %?1% or u.usuarioSemestre like %?1% or u.usuarioNumeroReservas like %?1% or u.usuarioNumeroEmprestimos like %?1% or u.usuarioAtivo like %?1% or u.usuarioNumeroRg like %?1%")
	List<Usuario> findQualquer(String busca);
}
