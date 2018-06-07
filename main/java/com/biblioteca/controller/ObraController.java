package com.biblioteca.controller;

import java.util.ArrayList;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.biblioteca.entities.Obra;
import com.biblioteca.repository.ObraRepository;
import com.biblioteca.service.ObraService;

@Controller
public class ObraController {
	
	
	@Autowired 
	private ObraRepository or;
	@Autowired 
	private ObraService os;
	
	
	@GetMapping("/cadastrarObra") 
	public String formObra(Model model) {
		Obra obra = new Obra();
		model.addAttribute("obra", obra);
		return "obra/formCadastroObra"; 
	}
	
	@PostMapping("/cadastrarObra") 
	public ModelAndView formObraSave(@Valid Obra obra, BindingResult result, RedirectAttributes attributes)throws RuntimeException{
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Preencha todos os campos corretamente!");
			return new ModelAndView("redirect:/cadastrarObra");
		} else {
			try {
				Info info = new Info();
	        	os.cadastrarObra(obra, info);
				
				attributes.addFlashAttribute("mensagem", "Obra cadastrado com sucesso!");
				return new ModelAndView("redirect:/cadastrarObra"); 
				
			} catch (Exception ex) {
				attributes.addFlashAttribute("mensagem", "Obra já cadastrado!");
				return new ModelAndView("redirect:/cadastrarObra"); 
			}
		}
	}
	
	@GetMapping("/consultarAcervo")
	public String consultarAcervo(Model model) {
		model.addAttribute("obras", new ArrayList<Obra>());
	    return "obra/consultaAcervo";
	}
	
	@PostMapping("/consultarAcervo")
	public String pesquisarAcervo(@RequestParam(value="pesquisa",required=false) String pesquisa, Model model, RedirectAttributes attributes) {
		if(pesquisa == "") {
			attributes.addFlashAttribute("mensagem", "Digite algo para pesquisar!");
			return "redirect:/consultarAcervo";
		}
		else
			model.addAttribute("obras", or.findQualquer(pesquisa));
			return "obra/consultaAcervo";
	}
	
	@RequestMapping("/deletarObra")
	public String deletarObra(@Valid long obraId, RedirectAttributes attributes) throws  RuntimeException {
		try {			
			Obra obra = or.findByObraId(obraId);
			os.deletarObra(obra);
			
			attributes.addFlashAttribute("mensagem", "Obra deletada com sucesso!");
			return "redirect:/consultarAcervo";
			
		} catch (Exception ex) {
			attributes.addFlashAttribute("mensagem", "Erro! o Obra deve estar vinculada a um empréstimo, revovação ou reserva!");
			return "redirect:/consultarAcervo"; 
		}
	}
	
	@GetMapping("/editarObra/{obraId}")
	public String editarObra(@PathVariable Long obraId, Model model){
		model.addAttribute("obra", or.findByObraId(obraId));
		return "obra/formEdicaoObra";
	}
	
	@PostMapping("/editarObra")
	public ModelAndView editarUsuarioSave(@Valid Obra obra, BindingResult result, RedirectAttributes attributes, Model model){
				os.editarObra(obra);

				attributes.addFlashAttribute("mensagem", "Obra atualizada com sucesso!");
				return new ModelAndView("redirect:/editarObra/" + obra.getObraId()); 
	}
	
	
	
	
	
}	


















