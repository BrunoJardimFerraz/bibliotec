package com.biblioteca.controller;

import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.biblioteca.entities.Info;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.UsuarioService;

@Controller
public class UsuarioController {


	@Autowired 
	private UsuarioRepository ur; 
	@Autowired 
	private UsuarioService us;
	

	

	public UsuarioController() {		
	}


	@GetMapping("/cadastrarUsuario") 
	public String formUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/formCadastroUsuario"; 
	}

	@PostMapping("/cadastrarUsuario") 
	public ModelAndView formUsuarioSave(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) throws  RuntimeException {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Preencha todos os campos corretamente!");
			return new ModelAndView("redirect:/cadastrarUsuario");
		} else {
			try {
				Info info = new Info();
				us.cadastrarUsuario(usuario, info);

				attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
				return new ModelAndView("redirect:/cadastrarUsuario"); 

			} catch (Exception ex) {
				attributes.addFlashAttribute("mensagem", "Número de R.A, e-email ou rg já cadastrados!");
				return new ModelAndView("redirect:/cadastrarUsuario"); 
			}
		}
	}

	
	@GetMapping("/consultarUsuario")
	public String listarUsuario(Model model, Principal principal) {
		model.addAttribute("principal", ur.findByUsuarioEmail(principal.getName()));
		return "usuario/consultaUsuario";
	}
	
	@GetMapping("/consultarUsuarios")
	public String listarUsuarios(Model model, Principal principal) {
		model.addAttribute("usuarios", new ArrayList<Usuario>());
		return "usuario/consultaUsuarios";
	}
	
	@PostMapping("/consultarUsuarios")
	public String pesquisaUsuario(@RequestParam(value="pesquisa",required=false) String pesquisa, Model model) {
		model.addAttribute("usuarios", ur.findQualquer(pesquisa));
	    return "usuario/consultaUsuarios";
	}
	
	

	@RequestMapping("/deletarUsuario")
	public String deletarUsuario(@Valid long usuarioId, RedirectAttributes attributes) throws  RuntimeException {
		try {
			Usuario usuario = ur.findByUsuarioId(usuarioId);
			us.deletarUsuario(usuario);

			attributes.addFlashAttribute("mensagem", "Usuário deletado com sucesso!");
			return "redirect:/consultarUsuarios"; 

		} catch (Exception ex) {
			attributes.addFlashAttribute("mensagem", "Erro! o Usuário deve estar vinculado a um empréstimo, revovação ou reserva!");
			return "redirect:/consultarUsuarios"; 
		}
	}
	
	@GetMapping("/editarUsuario/{usuarioId}")
	public String editarUsuario(@PathVariable Long usuarioId, RedirectAttributes attributes, Principal principal, Model model) {
		Usuario usuario = ur.findByUsuarioId(usuarioId);
		Usuario usuarioLogado = ur.findByUsuarioEmail(principal.getName());
		if(usuarioLogado != null && usuario.getUsuarioId() != usuarioLogado.getUsuarioId()) {
			attributes.addFlashAttribute("mensagem", "Não Permitido");
			return "redirect:/editarUsuario/" + usuarioLogado.getUsuarioId();
		}
		model.addAttribute("usuario", ur.findByUsuarioId(usuarioId));
		return "usuario/formEdicaoUsuario";
	}

	@PostMapping("/editarUsuario")
	public ModelAndView editarUsuarioSave(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes, Model model, Principal principal){
			try {
				us.editarUsuario(usuario);

				attributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
				return new ModelAndView("redirect:/editarUsuario/" + usuario.getUsuarioId()); 

			} catch (Exception ex) {
				attributes.addFlashAttribute("mensagem", "Número de R.A já cadastrado!");
				return new ModelAndView("redirect:/editarUsuario/" + usuario.getUsuarioId()); 
			}
	}


	@GetMapping("/alterarSenhaUsuario/{usuarioId}") 
	public String formSenhaUsuario(@PathVariable Long usuarioId, Model model, Principal principal, RedirectAttributes attributes) {
		Usuario usuario = ur.findByUsuarioId(usuarioId);
		Usuario usuarioLogado = ur.findByUsuarioEmail(principal.getName());
		if(usuarioLogado != null && usuario.getUsuarioId() != usuarioLogado.getUsuarioId()) {
			attributes.addFlashAttribute("mensagem", "Não Permitido");
			return "redirect:/alterarSenhaUsuario/" + usuarioLogado.getUsuarioId(); 
		}
		model.addAttribute("usuario", usuario);
		return "usuario/formSenhaUsuario"; 
	}

	@PostMapping("/alterarSenhaUsuario") 
	public ModelAndView formSenhaSave(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) throws  RuntimeException {
		if(result.hasErrors() || new BCryptPasswordEncoder().matches("", usuario.getPassword())) {
			attributes.addFlashAttribute("mensagem", "Não deixe a senha em branco!");
			return new ModelAndView("redirect:/alterarSenhaUsuario/" + usuario.getUsuarioId());
		} else {
			try {
				us.alterarSenhaUsuario(usuario);

				attributes.addFlashAttribute("mensagem", "Senha alterada com sucesso com sucesso!");
				return new ModelAndView("redirect:/alterarSenhaUsuario/" + usuario.getUsuarioId()); 

			} catch (Exception ex) {
				attributes.addFlashAttribute("mensagem", "Erro não identificado!");
				return new ModelAndView("redirect:/alterarSenhaUsuario/" + usuario.getUsuarioId()); 
			}
		}
	}

}
