package com.biblioteca.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.biblioteca.entities.Bibliotecario;
import com.biblioteca.entities.Info;
import com.biblioteca.entities.Obra;
import com.biblioteca.entities.Reserva;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.BibliotecarioRepository;
import com.biblioteca.repository.ObraRepository;
import com.biblioteca.repository.ReservaRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.EmailsService;
import com.biblioteca.service.ReservaService;

@Controller
public class ReservaController {
	
	@Autowired
	private UsuarioRepository ur;
	@Autowired
	private ObraRepository or;
	@Autowired
	private ReservaRepository rr;
	@Autowired
	private EmailsService ems;
	@Autowired 
	private BibliotecarioRepository br; 
	@Autowired 
	private ReservaService rs;

	
	
	public ReservaController() {
	}

		  
	@RequestMapping("/reservarObra")
	public ModelAndView reservarObra(long obraId, Principal principal, RedirectAttributes attributes){

		Reserva reserva = new Reserva();
		Info info = new Info();
		Obra obra = new Obra();
		Usuario usuario = new Usuario();
		
		obra = or.findByObraId(obraId);
		usuario = ur.findByUsuarioEmail(principal.getName());
		
		
				
		try { 
			if (obra.getObraReservado() == true){
				attributes.addFlashAttribute("mensagem", "A obra já está reservada!");
				return new ModelAndView("redirect:/consultarAcervo");
			 }
			 if (obra.getObraEmprestado() == true) {
			    attributes.addFlashAttribute("mensagem", "A obra já está emprestada!");
			    return new ModelAndView("redirect:/consultarAcervo");
			 }
			 if (usuario.getUsuarioAtivo() == false) {
				 attributes.addFlashAttribute("mensagem", "O usuario não está ativo!");
				 return new ModelAndView("redirect:/consultarAcervo");
			 }
			 if (usuario.getUsuarioNumeroReservas() >= 3) {
				 attributes.addFlashAttribute("mensagem", "O limite de 3 reservas por usuário foi atingido!");
				 return new ModelAndView("redirect:/consultarAcervo");
			 }
			 else {
				 
				rs.reservarObra(reserva, info, obra, usuario);
			 	ems.enviarEmailConfirmacaoReserva(reserva);
			                	
			    attributes.addFlashAttribute("mensagem", "Reserva realizada com sucesso!");
			    return new ModelAndView("redirect:/consultarAcervo");
			 }
		} catch (Exception ex) {
			attributes.addFlashAttribute("mensagem", ex.getMessage());
			return new ModelAndView("redirect:/consultarAcervo");
			}
		}
	 
	
	  
	  @GetMapping("/consultarReserva")
		public String listarReserva(Model model, Principal principal) {
		  Usuario usuarioLogado = ur.findByUsuarioEmail(principal.getName());
		  Bibliotecario bibliotecarioLogado = br.findByBibliotecarioEmail(principal.getName());
		  
		  if(bibliotecarioLogado != null) {
			  model.addAttribute("reservas", rr.findAll());
		  }
		  if(usuarioLogado != null ){
			  model.addAttribute("reservas", rr.findByUsuario(usuarioLogado));
		  }
		
		    return "obra/consultaReserva";
		    }
	  
	  @RequestMapping("/deletarReserva")
		public String deletarReserva(long reservaId, RedirectAttributes attributes){
		  	Reserva reserva = rr.findByReservaId(reservaId);
		  	rs.deletarReserva(reserva);
		  	
			attributes.addFlashAttribute("mensagem", "Reserva cancelada com sucesso!");
			return "redirect:/consultarReserva";
		}
 
	  
	  


	public ReservaRepository getRr() {
		return rr;
	}

	public void setRr(ReservaRepository rr) {
		this.rr = rr;
	}

	public EmailsService getEms() {
		return ems;
	}

	public void setEms(EmailsService ems) {
		this.ems = ems;
	}

	public UsuarioRepository getUr() {
		return ur;
	}

	public void setUr(UsuarioRepository ur) {
		this.ur = ur;
	}

	public ObraRepository getOr() {
		return or;
	}

	public void setOr(ObraRepository or) {
		this.or = or;
	}

}
