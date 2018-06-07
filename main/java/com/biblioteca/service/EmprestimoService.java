package com.biblioteca.service;


import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Obra;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.UsuarioRepository;

@Service
public class EmprestimoService {
	
	@Autowired
	private UsuarioRepository ur;
	@Autowired
	private EmprestimoRepository er;
	
	
	public void emprestarObra(Emprestimo emprestimo) throws RuntimeException {
		Usuario usuario = emprestimo.getUsuario();
        Obra obra = emprestimo.getObra(); 
		obra.setObraEmprestado(true);
		usuario.setUsuarioNumeroEmprestimos(usuario.getUsuarioNumeroEmprestimos() + 1);
   	 	emprestimo.setEmprestimoData(new Date());
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
	}
	
	
	public void deletarEmprestimo(Emprestimo emprestimo) {
		Usuario usuario = emprestimo.getUsuario();
		Obra obra = emprestimo.getObra();
		usuario.setUsuarioNumeroEmprestimos(usuario.getUsuarioNumeroEmprestimos() - 1);
		
		Date dataTemp;
		Calendar c = Calendar.getInstance();
    	
    	c.clear(Calendar.AM_PM);
    	c.clear(Calendar.HOUR);
    	c.clear(Calendar.HOUR_OF_DAY);
    	c.clear(Calendar.MINUTE);
    	c.clear(Calendar.SECOND);
    	c.clear(Calendar.MILLISECOND);
    	
    	dataTemp = c.getTime();
		
		//System.out.println(dataTemp);
		//System.out.println(emprestimo.getEmprestimoDataDevolucao());
		
		if(emprestimo.getEmprestimoDataDevolucao().before(dataTemp)) {
			long hojeMili = new Date().getTime();
			Long diferenca = hojeMili - emprestimo.getEmprestimoDataDevolucao().getTime();
			usuario.setUsuarioInatividadeExpirar(new Date( hojeMili + (diferenca*2) ) );
			usuario.setUsuarioAtivo(false);
			ur.save(usuario);
		}
		obra.setObraEmprestado(false);
        er.delete(emprestimo);
		
	}
	
	public void renovarEmprestimo(Emprestimo emprestimo, int temp) {
		temp=1;
		Date dataTemp = new Date();
    	Calendar c = Calendar.getInstance();
    	c.setTime(emprestimo.getEmprestimoDataDevolucao());
    	c.add(Calendar.DATE, + 7);
    	dataTemp = c.getTime();
    	emprestimo.setEmprestimoDataDevolucao(dataTemp);
    	emprestimo.setEmprestimoRenovacaoLimite(emprestimo.getEmprestimoRenovacaoLimite() + temp);
    	er.save(emprestimo);
	}
}
