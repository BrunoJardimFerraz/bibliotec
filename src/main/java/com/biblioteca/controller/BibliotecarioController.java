package com.biblioteca.controller;

import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.biblioteca.entities.Bibliotecario;
import com.biblioteca.entities.Info;
import com.biblioteca.repository.BibliotecarioRepository;
import com.biblioteca.service.BibliotecarioService;


@Controller
public class BibliotecarioController {
	
	@Autowired 
	private BibliotecarioRepository br; 
	@Autowired
	private BibliotecarioService bs; 
	
	
	@GetMapping("/cadastrarBibliotecario") 
	public String formBibliotecario(Model model) {
		model.addAttribute("bibliotecario", new Bibliotecario());
		return "bibliotecario/formCadastroBibliotecario"; 
	}
	
	@PostMapping("/cadastrarBibliotecario") 
	public ModelAndView formBibliotecarioSave(@Valid Bibliotecario bibliotecario, BindingResult result, RedirectAttributes attributes) throws  RuntimeException {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Preencha todos os campos corretamente!");
			return new ModelAndView("redirect:/cadastrarBibliotecario");
		} else {
			try {
				Info info = new Info();
	        	bs.cadastrarBibliotecario(bibliotecario, info);
				
				attributes.addFlashAttribute("mensagem", "Bibliotecário cadastrado com sucesso!");
				return new ModelAndView("redirect:/cadastrarBibliotecario");
				
			} catch (Exception ex) {
				attributes.addFlashAttribute("mensagem", "Login já cadastrado!");
				return new ModelAndView("redirect:/cadastrarBibliotecario"); 
				
			}
		}
	}
	
	
	@GetMapping("/consultarBibliotecario")
	public String listarBibliotecario(Model model, Principal principal) {
	    model.addAttribute("principal", br.findByBibliotecarioEmail(principal.getName()));
	    return "bibliotecario/consultaBibliotecario";
	    }
	
	@GetMapping("/consultarBibliotecarios")
	public String listarBibliotecarios(Model model, Principal principal) {
	    model.addAttribute("bibliotecarios", br.findAll());
	    return "bibliotecario/consultaBibliotecarios";
	    }
	
	@RequestMapping("/deletarBibliotecario")
	public String deletarBibliotecario(long bibliotecarioId, RedirectAttributes attributes){
		Bibliotecario bibliotecario = br.findByBibliotecarioId(bibliotecarioId);
		bs.deletarBibliotecario(bibliotecario);
		
		attributes.addFlashAttribute("mensagem", "Bibliotecário deletado com sucesso!");
		return "redirect:/consultarBibliotecarios";
	}
	
	@GetMapping("/editarBibliotecario/{bibliotecarioId}")
	public String editarBibliotecario(@PathVariable Long bibliotecarioId, RedirectAttributes attributes, Principal principal, Model model) {
		Bibliotecario bibliotecario = br.findByBibliotecarioId(bibliotecarioId);
		Bibliotecario bibliotecarioLogado = br.findByBibliotecarioEmail(principal.getName());
		if(bibliotecarioLogado != null && bibliotecario.getBibliotecarioId() != bibliotecarioLogado.getBibliotecarioId()) {
			attributes.addFlashAttribute("mensagem", "Não Permitido");
			return "redirect:/editarBibliotecario/" + bibliotecarioLogado.getBibliotecarioId();
		}
		model.addAttribute("bibliotecario", br.findByBibliotecarioId(bibliotecarioId));
		return "bibliotecario/formEdicaoBibliotecario";
	}

	@PostMapping("/editarBibliotecario")
	public ModelAndView editarUsuarioSave(@Valid Bibliotecario bibliotecario, BindingResult result, RedirectAttributes attributes, Model model, Principal principal){
			try {
				bs.editarBibliotecario(bibliotecario);

				attributes.addFlashAttribute("mensagem", "Bibliotecario atualizado com sucesso!");
				return new ModelAndView("redirect:/editarBibliotecario/" + bibliotecario.getBibliotecarioId()); 

			} catch (Exception ex) {
				attributes.addFlashAttribute("mensagem", "Número de R.A já cadastrado!");
				return new ModelAndView("redirect:/editarBibliotecario/" + bibliotecario.getBibliotecarioId()); 
			}
	}
	
	@GetMapping("/alterarSenhaBibliotecario/{bibliotecarioId}") 
	public String formSenhaBibliotecario(@PathVariable Long bibliotecarioId, Model model, Principal principal, RedirectAttributes attributes) {
		Bibliotecario bibliotecario = br.findByBibliotecarioId(bibliotecarioId);
		Bibliotecario bibliotecarioLogado = br.findByBibliotecarioEmail(principal.getName());
			if(bibliotecarioLogado != null && bibliotecario.getBibliotecarioId() != bibliotecarioLogado.getBibliotecarioId()) {
				attributes.addFlashAttribute("mensagem", "Não Permitido");
				return "redirect:/alterarSenhaBibliotecario/" + bibliotecarioLogado.getBibliotecarioId(); 	
		}
		model.addAttribute("bibliotecario", bibliotecario);
		return "bibliotecario/formSenhaBibliotecario"; 
	}

	@PostMapping("/alterarSenhaBibliotecario") 
	public ModelAndView formSenhaSave(@Valid Bibliotecario bibliotecario, BindingResult result, RedirectAttributes attributes) throws  RuntimeException {
		if(result.hasErrors() || new BCryptPasswordEncoder().matches("", bibliotecario.getPassword())) {
			attributes.addFlashAttribute("mensagem", "Não deixe a senha em branco!");
			return new ModelAndView("redirect:/alterarSenhaBibliotecario/" + bibliotecario.getBibliotecarioId());
		} else {
			try {
				bs.alterarSenhaBibliotecario(bibliotecario);
				
				attributes.addFlashAttribute("mensagem", "Senha alterada com sucesso com sucesso!");
				return new ModelAndView("redirect:/alterarSenhaBibliotecario/" + bibliotecario.getBibliotecarioId()); 
				
			} catch (Exception ex) {
				attributes.addFlashAttribute("mensagem", "Erro não identificado!");
				return new ModelAndView("redirect:/alterarSenhaBibliotecario/" + bibliotecario.getBibliotecarioId()); 
			}
		}
	}
	
}
