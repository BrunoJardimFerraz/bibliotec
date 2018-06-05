package com.biblioteca.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;



@SuppressWarnings("serial")
@Entity
@Validated
public class Obra  implements Serializable{
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long obraId;
	
	@NotNull
	private String obraTitulo;
	
	@NotNull 
	private String obraSubtitulo;
	
	@NotNull 
	private String obraTipoDocumento;
	
	@NotNull 
	private int obraTotalPagina;
	
	@NotNull 
	private String obraCidadePublicacao;

    @NotNull
    private String obraNumeroEdicao;
    
    @Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy") 
	private Date obraDataPublicacao;
    
    @NotNull 
	private String obraPalavraChave;
	
    @NotNull
    private String obraEditora;
	
    @NotNull
    private String obraAutor;
	
    @NotNull
    private String obraIsbn;
	
    @NotNull
    private String obraCdu;
	
    @NotNull
    private String obraPha;
	
	@Column(unique = true)
    @NotNull
	private int obraTombo;
	
	private boolean obraEmprestado = false;
	
	private boolean obraReservado = false;
	
	
	
	
    public Obra() {
	}
	
	public Obra(String obraTitulo, String obraEmprestado, String obraNumeroEdicao, String obraEditora, String obraAutor, String obraIsbn, String obraPalavraChave, String obraCidadePublicacao, Date obraDataPublicacao, String obraTipoDocumento, String obraSubtitulo, int obraTotalPagina, int obraTombo) {
		this.obraTitulo = obraTitulo.trim();
		this.obraNumeroEdicao = obraNumeroEdicao;
		this.obraEditora = obraEditora;
		this.obraAutor = obraAutor;
		this.obraIsbn = obraIsbn;
		this.obraPalavraChave = obraPalavraChave;
		this.obraCidadePublicacao = obraCidadePublicacao;
		this.obraDataPublicacao = obraDataPublicacao;
		this.obraTipoDocumento = obraTipoDocumento;
		this.obraSubtitulo = obraSubtitulo;
		this.obraTotalPagina = obraTotalPagina;
		this.obraTombo = obraTombo;
		
	}
	
	
	
	

	
	
	
	
	public String getObraCdu() {
		return obraCdu;
	}

	public void setObraCdu(String obraCdu) {
		this.obraCdu = obraCdu;
	}

	public String getObraPha() {
		return obraPha;
	}

	public void setObraPha(String obraPha) {
		this.obraPha = obraPha;
	}

	public int getObraTombo() {
		return obraTombo;
	}

	public void setObraTombo(int obraTombo) {
		this.obraTombo = obraTombo;
	}

	public boolean getObraEmprestado() {
		return obraEmprestado;
	}

	public void setObraEmprestado(boolean obraEmprestado) {
		this.obraEmprestado = obraEmprestado;
	}

	public boolean getObraReservado() {
		return obraReservado;
	}

	public void setObraReservado(boolean obraReservado) {
		this.obraReservado = obraReservado;
	}

	public Long getObraId() {
		return obraId;
	}

	public void setObraId(Long obraId) {
		this.obraId = obraId;
	}

	public String getObraTitulo() {
		return obraTitulo;
	}

	public void setObraTitulo(String obraTitulo) {
		this.obraTitulo = obraTitulo;
	}

	public String getObraNumeroEdicao() {
		return obraNumeroEdicao;
	}

	public void setObraNumeroEdicao(String obraNumeroEdicao) {
		this.obraNumeroEdicao = obraNumeroEdicao;
	}

	public String getObraEditora() {
		return obraEditora;
	}

	public void setObraEditora(String obraEditora) {
		this.obraEditora = obraEditora;
	}

	public String getObraAutor() {
		return obraAutor;
	}

	public void setObraAutor(String obraAutor) {
		this.obraAutor = obraAutor;
	}
	
	public String getObraIsbn() {
		return obraIsbn;
	}

	public void setObraIsbn(String obraIsbn) {
		this.obraIsbn = obraIsbn;
	}

	
	
	
	
	
	
	
	
	
	public String getObraSubtitulo() {
		return obraSubtitulo;
	}

	public void setObraSubtitulo(String obraSubtitulo) {
		this.obraSubtitulo = obraSubtitulo;
	}

	public String getObraTipoDocumento() {
		return obraTipoDocumento;
	}

	public void setObraTipoDocumento(String obraTipoDocumento) {
		this.obraTipoDocumento = obraTipoDocumento;
	}

	public int getObraTotalPagina() {
		return obraTotalPagina;
	}

	public void setObraTotalPagina(int obraTotalPagina) {
		this.obraTotalPagina = obraTotalPagina;
	}

	public String getObraCidadePublicacao() {
		return obraCidadePublicacao;
	}

	public void setObraCidadePublicacao(String obraCidadePublicacao) {
		this.obraCidadePublicacao = obraCidadePublicacao;
	}

	public Date getObraDataPublicacao() {
		return obraDataPublicacao;
	}

	public void setObraDataPublicacao(Date obraDataPublicacao) {
		this.obraDataPublicacao = obraDataPublicacao;
	}

	public String getObraPalavraChave() {
		return obraPalavraChave;
	}

	public void setObraPalavraChave(String obraPalavraChave) {
		this.obraPalavraChave = obraPalavraChave;
	}

	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 79 * hash + Objects.hashCode(this.obraId);
	    return hash;
	    }


	 @Override
	 public boolean equals(Object obj) {
	      if (this == obj) {
	          return true;
	      }
	      if (obj == null) {
	          return false;
	      }
	      if (getClass() != obj.getClass()) {
	          return false;
	      }
	      final Obra other = (Obra) obj;
	      if (!Objects.equals(this.obraId, other.obraId)) {
	          return false;
	      }
	      return true;
	  }
	
	
	
	 
	 @Override
	    public String toString() {
	        return "(TÍTULO: " + obraTitulo + " / CDU: " + obraCdu + " / PHA: " + obraPha + " / TOMBO: " + obraTombo + ")";
	    }


	
	
}
