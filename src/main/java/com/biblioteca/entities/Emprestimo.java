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
public class Emprestimo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long emprestimoId;
	
	@OneToOne
	@JoinColumn
	@NotNull             //"Um Usuario deve ser selecionado"
	private Usuario usuario;
	
	@OneToOne
	@NotNull             //"Uma obra deve ser selecionada"
	private Obra obra;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy") 
	private Date emprestimoData;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date emprestimoDataDevolucao;
	
	@NotNull
	private int emprestimoRenovacaoLimite = 0;
	
	
	
	
	
	

	public Emprestimo() {
		usuario = new Usuario();
		obra = new Obra();
	}
	
	
	
	
	
	
	
	
	public int getEmprestimoRenovacaoLimite() {
		return emprestimoRenovacaoLimite;
	}

	public void setEmprestimoRenovacaoLimite(int emprestimoRenovacaoLimite) {
		this.emprestimoRenovacaoLimite = emprestimoRenovacaoLimite;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getEmprestimoId() {
		return emprestimoId;
	}
	
	public void setEmprestimoId(Long emprestimoId) {
		this.emprestimoId = emprestimoId;
	}
	
	public Date getEmprestimoData() {
		return emprestimoData;
	}
	
	public void setEmprestimoData(Date emprestimoData) {
		this.emprestimoData = emprestimoData;
	}
	
	public Date getEmprestimoDataDevolucao() {
		return emprestimoDataDevolucao;
	}
	
	public void setEmprestimoDataDevolucao(Date emprestimoDataDevolucao) {
		this.emprestimoDataDevolucao = emprestimoDataDevolucao;
	}
	
	
	
	
	
	
	
	public String getInfoObra() {
		String print = "";
            print += (obra.getObraTitulo() + ", ");
        return print;
    }
}
	
	
	

