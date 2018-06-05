package com.biblioteca.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.biblioteca.entities.Bibliotecario;
import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Obra;
import com.biblioteca.entities.Reserva;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.BibliotecarioRepository;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.InfoRepository;
import com.biblioteca.repository.ObraRepository;
import com.biblioteca.repository.ReservaRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.EmailsService;
import com.biblioteca.service.EmprestimoService;

@Controller
public class EmprestimoController {
	
	@Autowired
	private UsuarioRepository ur;
	@Autowired
	private ObraRepository or;
	@Autowired
	private EmprestimoRepository er;
	@Autowired
	private EmprestimoService es;
	@Autowired
	private EmailsService ems;
	@Autowired 
	private ReservaRepository rr;
	@Autowired 
	private ReservaController rc;
	@Autowired 
	private BibliotecarioRepository br; 
	

	
	public EmprestimoController() {
	}

	public EmprestimoController(UsuarioRepository usuarioRepository, ObraRepository obraRepository, EmprestimoRepository emprestimoRepository, ReservaRepository reservaRepository, ReservaController reservaController, InfoRepository infoRepository) {
		this.ur = usuarioRepository;
		this.or = obraRepository;
		this.er = emprestimoRepository;
		this.rr = reservaRepository;
		this.rc = reservaController;
	}
	
	
	
	  @GetMapping({"/emprestarObraNaoReservada"})
	  public String emprestarNaoReservadas(Model model) { 
		  //lista para realizar um empréstimo de uma obra para quem não reservou um livro. 
		  model.addAttribute("todasObras", or.findByObraEmprestadoFalseAndObraReservadoFalse());
		  model.addAttribute("usuarios", ur.findAll());
		  model.addAttribute("emprestimo", new Emprestimo());  
	      return "obra/emprestimoObraNaoReservada";
	  }
	  
	  @GetMapping({"/emprestarObraReservada"})
	  public String emprestarReservadas(Model model) {
		  //lista para realizar um empréstimo de uma obra para quem reservou um livro.
		  model.addAttribute("todasReservadas", rr.findAll());
		  model.addAttribute("reservaRequest", new ReservaRequest());
	      return "obra/emprestimoObraReservada";
	  }
	  
	       
	  @PostMapping("/emprestarObraNaoReservada")
	  public ModelAndView emprestarObra(@Valid Emprestimo emprestimo, RedirectAttributes attributes) throws RuntimeException {
		  Usuario usuario = emprestimo.getUsuario();
          
	        try { 
	                if (usuario.getUsuarioAtivo() == false) {
	                	attributes.addFlashAttribute("mensagem", "O usuario não está ativo!");
		            	return new ModelAndView("redirect:/emprestarObraNaoReservada");
	                }
	                if (usuario.getUsuarioNumeroEmprestimos() >= 3) {
	                	attributes.addFlashAttribute("mensagem", "O limite de 3 empréstimos por usuário foi atingido!");
		            	return new ModelAndView("redirect:/emprestarObraNaoReservada");
	                }
	                else 
	                	es.emprestarObra(emprestimo);
						ems.enviarEmailConfirmacaoEmprestimo(emprestimo);
	                	
	            
	                	attributes.addFlashAttribute("mensagem", "Emprestimo realizado com sucesso!");
	                	return new ModelAndView("redirect:/emprestarObraNaoReservada");

	        } catch (Exception ex) {
	            attributes.addFlashAttribute("mensagem", "Um erro inesperado ocorreu com o envio do e-mail.");
	            return new ModelAndView("redirect:/emprestarObraNaoReservada");
	      }
	  } 
	  
	  @PostMapping({"/emprestarObraReservada"}) 
		public ModelAndView emprestarObraReservada(@Valid ReservaRequest reservaRequest, RedirectAttributes attributes) throws RuntimeException {
			Usuario usuario = reservaRequest.getReserva().getUsuario();
			Obra obra = reservaRequest.getReserva().getObra();
			try { 
				  if (usuario.getUsuarioAtivo() == false) {
					  attributes.addFlashAttribute("mensagem", "O usuario não está ativo!");
					  return new ModelAndView("redirect:/emprestarObraReservada");
				  }
				  if (usuario.getUsuarioNumeroEmprestimos() >= 3) {
					  attributes.addFlashAttribute("mensagem", "O limite de 3 empréstimos por usuário foi atingido!");
					  return new ModelAndView("redirect:/emprestarObraReservada");
				  }
				  else {
					  obra.setObraEmprestado(true);
					  usuario.setUsuarioNumeroEmprestimos(usuario.getUsuarioNumeroEmprestimos() + 1);
					  usuario.setUsuarioNumeroReservas(usuario.getUsuarioNumeroReservas() - 1);
					  
					  Emprestimo emprestimo = new Emprestimo();
					  emprestimo.setEmprestimoData(new Date());
					  emprestimo.setEmprestimoDataDevolucao(reservaRequest.getEmprestimoDevolucao());
					  emprestimo.setObra(obra);
					  emprestimo.setUsuario(usuario);

					  obra.setObraReservado(false);
					  
					  if(emprestimo.getEmprestimoDataDevolucao() == null) {
				   	 		Date dataTemp = new Date();
				   	 		Calendar c = Calendar.getInstance();
				   	 		c.setTime(emprestimo.getEmprestimoData());
				   	 		c.add(Calendar.DATE, + 7);
				   	 		dataTemp = c.getTime();
				   	 		emprestimo.setEmprestimoDataDevolucao(dataTemp);
				   	 	}
					  
					  er.save(emprestimo);
					  ems.enviarEmailConfirmacaoEmprestimo(emprestimo);

					  rr.delete(reservaRequest.getReserva());

					  attributes.addFlashAttribute("mensagem", "Emprestimo realizado com sucesso!");
					  return new ModelAndView("redirect:/emprestarObraReservada");
				  }
			  } catch (Exception ex) {
				  attributes.addFlashAttribute("mensagem", "Um erro inesperado ocorreu com o envio do e-mail.");
				  return new ModelAndView("redirect:/emprestarObraReservada");
			  }
		}

	  
	  @GetMapping("/consultarEmprestimo")
		public String listarEmprestimo(Model model, Principal principal) {
		  Usuario usuarioLogado = ur.findByUsuarioEmail(principal.getName());
		  Bibliotecario bibliotecarioLogado = br.findByBibliotecarioEmail(principal.getName());  
		  
		  if(bibliotecarioLogado != null) {
			  model.addAttribute("emprestimos", er.findAll());
		  }
		  if(usuarioLogado != null ){
			  model.addAttribute("emprestimos", er.findByUsuario(usuarioLogado));
		  }
	
		    return "obra/consultaEmprestimo";
	  }
	  
	  
	@RequestMapping("/deletarEmprestimo")
		public String deletarEmprestimo(long emprestimoId, RedirectAttributes attributes){
			Emprestimo emprestimo = er.findByEmprestimoId(emprestimoId);
			Usuario usuario = emprestimo.getUsuario();
				
			es.deletarEmprestimo(emprestimo);
			ems.enviarEmailConfirmacaoDevolucao(emprestimo);
			
		  	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		  	String datas = null;
			
			
			if(usuario.getUsuarioInatividadeExpirar() == null) {
				attributes.addFlashAttribute("mensagem", "Empréstimo finalizado com sucesso!");
			}
			else {
				datas = formatter.format(usuario.getUsuarioInatividadeExpirar());
				attributes.addFlashAttribute("mensagem", "Empréstimo finalizado com sucesso!, o aluno poderá emprestar/reservar livros apartir do dia: " + datas);	
			}
			return "redirect:/consultarEmprestimo";
		}
	  
		@RequestMapping("/renovarEmprestimo")
		public ModelAndView renovarEmprestimo(@Valid long emprestimoId, RedirectAttributes attributes) throws RuntimeException{
			
			Emprestimo emprestimo = er.findByEmprestimoId(emprestimoId);
			int temp = 1;
        	
		
			try { 
                if (emprestimo.getEmprestimoRenovacaoLimite() >= temp){
                	attributes.addFlashAttribute("mensagem", "Só é permitida uma renovação por emprestimo!");
	            	return new ModelAndView("redirect:/consultarEmprestimo");
                }
                
                else
                	
                	es.renovarEmprestimo(emprestimo,temp);
                	ems.enviarEmailConfirmacaoRenovação(emprestimo);
              
                	attributes.addFlashAttribute("mensagem", "Revovação realizada com sucesso!");
                	return new ModelAndView("redirect:/consultarEmprestimo");
                	
		    } catch (Exception ex) {
	            attributes.addFlashAttribute("mensagem", "Houve um erro não identificado!");
	            return new ModelAndView("redirect:/consultarEmprestimo");
	      }
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

	public EmprestimoRepository getEr() {
		return er;
	}

	public void setEr(EmprestimoRepository er) {
		this.er = er;
	}

	public ReservaRepository getRr() {
		return rr;
	}

	public void setRr(ReservaRepository rr) {
		this.rr = rr;
	}

	public ReservaController getRc() {
		return rc;
	}

	public void setRc(ReservaController rc) {
		this.rc = rc;
	}

	
	
	
	
	@PostConstruct
	    public void population() {
	        //ur.save(new Usuario("y","1111","nome","email","celular","curso","turno","ADMIN", "semestre"));
	        //or.save(new Obra("seila", "sim", "sim", "sim", "sim", 3));
		}
	
	
	
}

class ReservaRequest{
	
	public ReservaRequest() {
	}
	
	public ReservaRequest(Reserva reserva, Date emprestimoDevolucao) {
		this.reserva = reserva;
		this.emprestimoDevolucao = emprestimoDevolucao;
	}
	
	private Reserva reserva;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date emprestimoDevolucao;
	
	public Reserva getReserva() {
		return reserva;
	}
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	public Date getEmprestimoDevolucao() {
		return emprestimoDevolucao;
	}
	public void setEmprestimoDevolucao(Date emprestimoDevolucao) {
		this.emprestimoDevolucao = emprestimoDevolucao;
	}	
}
