package com.biblioteca.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.biblioteca.entities.Bibliotecario;
import com.biblioteca.repository.BibliotecarioRepository;


@Service
@Transactional
//@Repository
public class ImplementsBibliotecarioDetailsService implements UserDetailsService{
	
	@Autowired
	private BibliotecarioRepository br;
	
	@Override
	public UserDetails loadUserByUsername(String bibliotecarioLogin) throws UsernameNotFoundException {
		Bibliotecario bibliotecario = br.findByBibliotecarioEmail(bibliotecarioLogin);
		
		if(bibliotecario == null) {
			throw new UsernameNotFoundException("Bibliotecário não encontrado!");
		}
		return new User(bibliotecario.getUsername(),bibliotecario.getPassword(), true, true, true, true, bibliotecario.getAuthorities());
		//return bibliotecario;
	}
}
