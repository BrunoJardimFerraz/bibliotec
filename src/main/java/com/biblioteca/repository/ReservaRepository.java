package com.biblioteca.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.biblioteca.entities.Reserva;
import com.biblioteca.entities.Usuario;

@Repository
public interface ReservaRepository  extends JpaRepository<Reserva, Long>{

	Reserva findByReservaId(Long reservaId);
	
	List<Reserva> findByUsuario(Usuario usuario);
	
	List<Reserva> findByReservaDataExpiraBefore(Date expira);
}
