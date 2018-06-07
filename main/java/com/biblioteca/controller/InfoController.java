package com.biblioteca.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.biblioteca.entities.Info;
import com.biblioteca.repository.BibliotecarioRepository;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.ObraRepository;
import com.biblioteca.repository.ReservaRepository;
import com.biblioteca.repository.UsuarioRepository;


@Controller
public class InfoController {
	
	
	@Autowired
	private UsuarioRepository ur;
	@Autowired
	private ObraRepository or;
	@Autowired 
	private BibliotecarioRepository br;
	@Autowired
	private EmprestimoRepository er;
	@Autowired
	private ReservaRepository rr;
	
	@RequestMapping(value = "/consultarInfo")
	public String consultarInfo(Model model){	
		ArrayList<Info> listInfo = new ArrayList<Info>();
		Info info = new Info();
		info.setInfoObraCount((int) or.count());
		info.setInfoEmprestimoCount((int) er.count());
		info.setInfoBibliotecarioCount((int) br.count());
		info.setInfoUsuarioCount((int) ur.count());
		info.setInfoReservaCount((int) rr.count());
		
		listInfo.add(info);
		model.addAttribute("infos", listInfo);
	    return "info/consultaInfo";
	}

}
