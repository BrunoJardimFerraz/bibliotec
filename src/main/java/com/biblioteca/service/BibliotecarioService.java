package com.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.biblioteca.entities.Bibliotecario;
import com.biblioteca.entities.Info;
import com.biblioteca.repository.BibliotecarioRepository;
import com.biblioteca.repository.InfoRepository;

@Service
public class BibliotecarioService {
	
	@Autowired
	private BibliotecarioRepository br;
	@Autowired
	private InfoRepository ir;
	
	
	public void cadastrarBibliotecario(Bibliotecario bibliotecario, Info info){
		info.setInfoBibliotecarioCount(info.getInfoBibliotecarioCount() +1);
		bibliotecario.setBibliotecarioSenha(new BCryptPasswordEncoder().encode(bibliotecario.getPassword()));
		br.save(bibliotecario);
		ir.save(info);		
	}
	
	
	public void deletarBibliotecario(Bibliotecario bibliotecario) {
		br.delete(bibliotecario);
	}
	
	
	public void editarBibliotecario(Bibliotecario bibliotecario) {
		br.save(bibliotecario);	
	}
	
	public void alterarSenhaBibliotecario(Bibliotecario bibliotecario) {
		bibliotecario.setBibliotecarioSenha(new BCryptPasswordEncoder().encode(bibliotecario.getPassword()));
		br.save(bibliotecario);	
	}
	
	
	
	
	
	
	
	
	
	
	

}
