package com.biblioteca.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Reserva;
import com.biblioteca.entities.Usuario;

@Service
public class EmailsService {
	
	
	@Autowired
	private JavaMailSender jms;
	
	
	public String enviarEmailConfirmacaoEmprestimo(Emprestimo emprestimo){
		final Usuario usuario = emprestimo.getUsuario();
		
		Date data1 = emprestimo.getEmprestimoData();
		Date data2 = emprestimo.getEmprestimoDataDevolucao();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada1 = formato.format(data1);
		String dataFormatada2 = formato.format(data2);
		
		final String corpoEmail = "Olá, " + usuario.getUsuarioNome() + "\n\n"
				+ "Você emprestou o livro: " + emprestimo.getInfoObra() + " no dia: " + dataFormatada1 + ".\n\n"
				+ "Devolva o livro até dia: " + dataFormatada2 + ".\n\n"
				+ "Obs: Em caso de atraso o aluno ficara impossibilitado de realizar um novo emprestimo pelo dobro dos dias em atraso"
				+ ".\n\n"
				+ "Email enviado automaticamente. Não responda a este E-mail.\n\n" + "Equipe Bibliotec";
		
		MimeMessage message = jms.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    try {
	    	helper.setTo(usuario.getUsuarioEmail());
	        helper.setText(corpoEmail);
	        helper.setSubject("BIBLIOTEC - CONFIRMAÇÃO EMPRESTIMO");
	    } catch (MessagingException e) {
	    	e.printStackTrace();
	    	return "Error while sending mail ..";
	      }
	        jms.send(message);
	        return "Mail Sent Success!";
	    }
	
	
	public String enviarEmailConfirmacaoDevolucao(Emprestimo emprestimo) {
		final Usuario usuario = emprestimo.getUsuario();
		
		Date data1 = emprestimo.getEmprestimoData();
		Date data2 = emprestimo.getEmprestimoDataDevolucao();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada1 = formato.format(data1);
		String dataFormatada2 = formato.format(data2);
		
		final String corpoEmail = "Olá, " + usuario.getUsuarioNome() + "\n\n"
				+ "Obrigado por devolver o livro: " + emprestimo.getInfoObra() + ", emprestado no dia: " + dataFormatada1 + "\n\n"
				+ "e data de devolução dia: " + dataFormatada2 
				+ ".\n\n"
				+ "Email enviado automaticamente. Não responda a este E-mail.\n\n" + "Equipe Bibliotec";
		
		MimeMessage message = jms.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    try {
	    	helper.setTo(usuario.getUsuarioEmail());
	        helper.setText(corpoEmail);
	        helper.setSubject("BIBLIOTEC - CONFIRMAÇÃO DEVOLUÇÃO");
	    } catch (MessagingException e) {
	    	e.printStackTrace();
	    	return "Error while sending mail ..";
	      }
	        jms.send(message);
	        return "Mail Sent Success!";
	    }
	
	
	 public String enviarEmailConfirmacaoReserva(Reserva reserva) {
			final Usuario usuario = reserva.getUsuario();
			
			Date data1 = reserva.getReservaData();
			Date data2 = reserva.getReservaDataExpira();
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String dataFormatada1 = formato.format(data1);
			String dataFormatada2 = formato.format(data2);
			
			final String corpoEmail = "Olá, " + usuario.getUsuarioNome() + "\n\n"
					+ "Você reservou o livro: " + reserva.getInfoObra() + " no dia: " + dataFormatada1 + ".\n\n"
					+ "A reserva vai ser cancelada no dia: " + dataFormatada2 + ".\n\n"
					+ "Obs: Compareça a biblioteca para emprestar o livro até a data de cancelamento da reserva."
					+ ".\n\n"
					+ "Email enviado automaticamente. Não responda a este E-mail.\n\n" + "Equipe Bibliotec";
			
			MimeMessage message = jms.createMimeMessage();
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		    try {
		    	helper.setTo(usuario.getUsuarioEmail());
		        helper.setText(corpoEmail);
		        helper.setSubject("BIBLIOTEC - CONFIRMAÇÃO RESERVA");
		    } catch (MessagingException e) {
		    	e.printStackTrace();
		    	return "Error while sending mail ..";
		      }
		        jms.send(message);
		        return "Mail Sent Success!";
		    }
	
	
	 public String enviarEmailConfirmacaoRenovação(Emprestimo emprestimo) {
			final Usuario usuario = emprestimo.getUsuario();
			
			Date data1 = emprestimo.getEmprestimoData();
			Date data2 = emprestimo.getEmprestimoDataDevolucao();
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String dataFormatada1 = formato.format(data1);
			String dataFormatada2 = formato.format(data2);
			
			final String corpoEmail = "Olá, " + usuario.getUsuarioNome() + "\n\n"
					+ "O empréstimo do livro: " + emprestimo.getInfoObra() + " no dia: " + dataFormatada1 + ".\n\n"
					+ "foi prorrogada para o dia: " + dataFormatada2 + ".\n\n"
					+ ".\n\n"
					+ "Email enviado automaticamente. Não responda a este E-mail.\n\n" + "Equipe Bibliotec";
			
			MimeMessage message = jms.createMimeMessage();
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		    try {
		    	helper.setTo(usuario.getUsuarioEmail());
		        helper.setText(corpoEmail);
		        helper.setSubject("BIBLIOTEC - CONFIRMAÇÃO RENOVAÇÂO");
		    } catch (MessagingException e) {
		    	e.printStackTrace();
		    	return "Error while sending mail ..";
		      }
		        jms.send(message);
		        return "Mail Sent Success!";
		    }
	
	
	
	
	public JavaMailSender getJms() {
		return jms;
	}

	public void setJms(JavaMailSender jms) {
		this.jms = jms;
	}
}
