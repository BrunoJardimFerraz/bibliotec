package com.biblioteca.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.UsuarioRepository;


@Service
@Transactional
//@Repository
public class ImplementsUsuarioDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository ur;
		
	@Override
	public UserDetails loadUserByUsername(String usuarioEmail) throws UsernameNotFoundException {
		Usuario usuario = ur.findByUsuarioEmail(usuarioEmail);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		return new User(usuario.getUsername(),usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
		//return usuario;
	}
}