package com.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.Info;
import com.biblioteca.entities.Obra;
import com.biblioteca.repository.InfoRepository;
import com.biblioteca.repository.ObraRepository;

@Service
public class ObraService {
	
	@Autowired
	private ObraRepository or;
	@Autowired
	private InfoRepository ir;
	
	
	public void cadastrarObra(Obra obra, Info info) {
		info.setInfoObraCount(info.getInfoObraCount() +1);
    	obra.setObraTombo(or.findFirstByOrderByObraTomboDesc().getObraTombo() + 1);
		or.save(obra);
		ir.save(info);
		
	}

	public void deletarObra(Obra obra) {
		or.delete(obra);
		
	}
	
	public void editarObra(Obra obra) {
		or.save(obra);
		
	}
	
	
}
