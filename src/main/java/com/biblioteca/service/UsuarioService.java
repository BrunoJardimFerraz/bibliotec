package com.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.biblioteca.entities.Info;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.InfoRepository;
import com.biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository ur;
	@Autowired
	private InfoRepository ir;
	
	
	public void cadastrarUsuario(Usuario usuario, Info info){
		info.setInfoUsuarioCount(info.getInfoUsuarioCount() +1);
		usuario.setUsuarioSenha(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		ur.save(usuario);
		ir.save(info);		
	}
	
	
	public void deletarUsuario(Usuario usuario) {
		ur.delete(usuario);
	}
	
	
	public void editarUsuario(Usuario usuario) {
		ur.save(usuario);	
	}
	
	public void alterarSenhaUsuario(Usuario usuario) {
		usuario.setUsuarioSenha(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		ur.save(usuario);	
	}
}
