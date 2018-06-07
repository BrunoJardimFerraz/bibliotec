package com.biblioteca.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.BibliotecarioRepository;
import com.biblioteca.repository.UsuarioRepository;



@Controller
public class DefaultController {
	
	@Autowired
	private UsuarioRepository ur;	
	@Autowired
	private BibliotecarioRepository br;
	
	

	
		
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(){
	    return "default/login";
	}
	

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String index(Model model, Principal principal){
		Usuario  usuario = ur.findByUsuarioEmail(principal.getName());
		String x = " ";
		if(usuario != null && usuario.getUsuarioAtivo() == false && usuario.getUsuarioInatividadeExpirar() != null ) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			x = " ->  Você ficará impossibilitado de usar os recursos até dia: " +formatter.format(usuario.getUsuarioInatividadeExpirar());	
		}
		if(usuario != null && usuario.getUsuarioAtivo() == false) {
			x = " ->  Você possui empréstimo(s) em atraso, compareça a Biblioteca!";
		}
		
		model.addAttribute("usuarios", usuario);
		model.addAttribute("bibliotecarios", br.findByBibliotecarioEmail(principal.getName()));
		model.addAttribute("men", x);

				
	    return "default/menu";
	}
	
}
