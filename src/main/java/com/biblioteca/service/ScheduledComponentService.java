package com.biblioteca.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Reserva;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.ObraRepository;
import com.biblioteca.repository.ReservaRepository;
import com.biblioteca.repository.UsuarioRepository;


@Component 
public class ScheduledComponentService {
	
	@Autowired
	private UsuarioRepository ur;	
	@Autowired 
	private ReservaRepository rr;
	@Autowired 
	private ObraRepository or;
	@Autowired 
	private EmprestimoRepository er;
	
	/*
	 * second, minute, hour, day of month, month, day(s) of week
	 * 
	 * "0 0 * * * *" = the top of every hour of every day.
	 * "* /10 * * * * *" = every ten seconds.
	 * "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
	 * "0 0 8,10 * * *" = 8 and 10 o'clock of every day.
	 * "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
	 * "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
	 * "0 0 0 25 12 ?" = every Christmas Day at midnight
	 */
		
	@Scheduled(cron = "*/10 * * * * *") //checar a cada hora por reservas expiradas
	public void checarReservasExpiradas() {
		List<Reserva> lista;
		
		Date dataTemp;
	    Calendar c = Calendar.getInstance();
    	
    	c.clear(Calendar.AM_PM);
    	c.clear(Calendar.HOUR);
    	c.clear(Calendar.HOUR_OF_DAY);
    	c.clear(Calendar.MINUTE);
    	c.clear(Calendar.SECOND);
    	c.clear(Calendar.MILLISECOND);
    	
    	dataTemp = c.getTime();
		lista = rr.findByReservaDataExpiraBefore(dataTemp);
		
		//System.out.println(dataTemp);
		
		for (Reserva reserva : lista) {
			reserva.getObra().setObraReservado(false);
			
			Usuario usuario = reserva.getUsuario();
			usuario.setUsuarioNumeroReservas(usuario.getUsuarioNumeroReservas() - 1);
			
			ur.save(usuario);
			or.save(reserva.getObra());
			rr.delete(reserva);
		}
		//System.out.println(lista.size());	
	}
	
	@Scheduled(cron = "*/10 * * * * *") //checar a cada hora por emprestimos atrasados
	public void checarEmprestimosAtrasados() {
		List<Emprestimo> lista;
		
		Date dataTemp;
	    Calendar c = Calendar.getInstance();
    	
    	c.clear(Calendar.AM_PM);
    	c.clear(Calendar.HOUR);
    	c.clear(Calendar.HOUR_OF_DAY);
    	c.clear(Calendar.MINUTE);
    	c.clear(Calendar.SECOND);
    	c.clear(Calendar.MILLISECOND);
    	
    	dataTemp = c.getTime();
		lista =  er.findByEmprestimoDataDevolucaoBefore(dataTemp);
		
		//System.out.println(dataTemp);
		
		for (Emprestimo emprestimo : lista) {
			emprestimo.getUsuario().setUsuarioAtivo(false);
			ur.save(emprestimo.getUsuario());			
		}
		//System.out.println(lista.size());	
	}
	
	@Scheduled(cron = "0 0 * * * *") //checar se o usuario pode voltar a usar o sistema
	public void checarUsuariosInativos() {
		List<Usuario> lista;
		
		Date dataTemp;
	    Calendar c = Calendar.getInstance();
    	
    	c.clear(Calendar.AM_PM);
    	c.clear(Calendar.HOUR);
    	c.clear(Calendar.HOUR_OF_DAY);
    	c.clear(Calendar.MINUTE);
    	c.clear(Calendar.SECOND);
    	c.clear(Calendar.MILLISECOND);
    	
    	dataTemp = c.getTime();
		lista =  ur.findByUsuarioInatividadeExpirarBefore(dataTemp);
		
		//System.out.println(dataTemp);
		
		for (Usuario usuario : lista) {
			usuario.setUsuarioAtivo(true);
			usuario.setUsuarioInatividadeExpirar(null);
			ur.save(usuario);			
		}
		//System.out.println(lista.size());	
	}
	
}
