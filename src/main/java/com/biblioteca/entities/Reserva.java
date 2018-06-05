package com.biblioteca.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

@SuppressWarnings("serial")
@Entity
@Validated
public class Reserva  implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reservaId;
	
	@OneToOne
	@JoinColumn
	@NotNull               //"Um Usuario deve ser selecionado"
	private Usuario usuario;
	
	@OneToOne
	@NotNull              //"Uma obra deve ser selecionada"
	private Obra obra;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date reservaData;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date reservaDataExpira;
	
	
	
	
	
	public Reserva () {
		usuario = new Usuario();
		obra = new Obra();
	}





	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public Long getReservaId() {
		return reservaId;
	}
	
	public void setReservaId(Long reservaId) {
		this.reservaId = reservaId;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getReservaData() {
		return reservaData;
	}

	public void setReservaData(Date reservaData) {
		this.reservaData = reservaData;
	}

	public Date getReservaDataExpira() {
		return reservaDataExpira;
	}

	public void setReservaDataExpira(Date reservaDataExpira) {
		this.reservaDataExpira = reservaDataExpira;
	}
	
	
	
	
	 @Override
	    public String toString() {
	        return "(NOME: " + usuario.getUsuarioNome() + " / RG: " + usuario.getUsuarioNumeroRg() + " / OBRA: " + obra.getObraTitulo() + " / TOMBO: " + obra.getObraTombo() +  " / CDU: " + obra.getObraCdu() + " / PHA: " + obra.getObraPha() + ")";
	    }
	
	
	
	public String getInfoObra() {
		String print = "";
            print += (obra.getObraTitulo() + ", ");
        return print;
    }

}
