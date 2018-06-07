package com.biblioteca.service;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.entities.Info;
import com.biblioteca.entities.Obra;
import com.biblioteca.entities.Reserva;
import com.biblioteca.entities.Usuario;
import com.biblioteca.repository.InfoRepository;
import com.biblioteca.repository.ObraRepository;
import com.biblioteca.repository.ReservaRepository;
import com.biblioteca.repository.UsuarioRepository;

@Service
public class ReservaService {
	
	@Autowired 
	ReservaRepository rr;
	
	@Autowired 
	InfoRepository ir;
	
	@Autowired
	ObraRepository or;
	
	@Autowired
	UsuarioRepository ur;
	
	
	
	
	public void reservarObra(Reserva reserva, Info info, Obra obra, Usuario usuario) {
		 
         reserva.setObra(obra);
		 reserva.setUsuario(usuario);
					 
     	 obra.setObraReservado(true);
    	 usuario.setUsuarioNumeroReservas(usuario.getUsuarioNumeroReservas() + 1);

    	 Date dataTemp = new Date();
    	 Calendar c = Calendar.getInstance();
    	 c.add(Calendar.DATE, + 1);
    	 dataTemp = c.getTime();

    	 reserva.setReservaData(new Date());
    	 reserva.setReservaDataExpira(dataTemp);
    	
    	 info.setInfoReservaCount(info.getInfoReservaCount() +1);
    	
    	 rr.save(reserva);
    	 ir.save(info);
		
	}
	
	
	public void deletarReserva(Reserva reserva) {
		Usuario usuario = reserva.getUsuario();
		Obra obra = reserva.getObra();
		usuario.setUsuarioNumeroReservas(usuario.getUsuarioNumeroReservas() - 1);
		obra.setObraReservado(false);
		rr.delete(reserva);
		
	}
}
