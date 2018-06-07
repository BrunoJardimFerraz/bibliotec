package com.biblioteca.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;


@SuppressWarnings("serial")
@Entity
@Validated
public class Usuario implements Serializable, UserDetails{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long usuarioId;
	
	private String role ="USER";
	
	@Column(unique = true)
    @NotNull
	private String usuarioNumeroMatricula; 
	
	@NotEmpty
	private String usuarioSenha;
	
	@NotEmpty
	private String usuarioNome;
	
	@Column(unique = true)
    @NotNull
	private String usuarioEmail;
	
	@NotEmpty
	private String usuarioCelular;
	
	@NotEmpty
	private String usuarioCurso;
	
	@NotEmpty
	private String usuarioSemestre;
	
	@NotEmpty
	private String usuarioTurno;
	 
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy") 
	private Date usuarioInatividadeExpirar;
	
	private int usuarioNumeroEmprestimos = 0;
	
	private int usuarioNumeroReservas = 0;
	
	private boolean usuarioAtivo = true;
	
	@Column(unique = true)
    @NotNull
	private String usuarioNumeroRg;
	
	
	
	
	
	public Usuario() {
	}
	
	public Usuario(String matricula, String senha, String nome, String email, String celular, String curso, String turno, String role, String semestre, boolean ativo, String rg, int emprestimo, int reserva) {
		this.usuarioNumeroMatricula = matricula;
		this.usuarioSenha = senha;
	    this.usuarioNome = nome;
	    this.usuarioEmail = email;
	    this.usuarioCelular = celular;
	    this.usuarioCurso = curso;
	    this.usuarioTurno = turno;
	    this.usuarioSemestre = semestre;
	    this.role = role;
	    this.usuarioAtivo = ativo;
	    this.usuarioNumeroRg = rg;
	    this.usuarioNumeroEmprestimos = emprestimo;
	    this.usuarioNumeroReservas = reserva;
	    
	}
	
	

	
	
	public Date getUsuarioInatividadeExpirar() {
		return usuarioInatividadeExpirar;
	}

	public void setUsuarioInatividadeExpirar(Date usuarioInatividadeExpirar) {
		this.usuarioInatividadeExpirar = usuarioInatividadeExpirar;
	}

	public int getUsuarioNumeroEmprestimos() {
		return usuarioNumeroEmprestimos;
	}

	public void setUsuarioNumeroEmprestimos(int usuarioNumeroEmprestimos) {
		this.usuarioNumeroEmprestimos = usuarioNumeroEmprestimos;
	}

	public int getUsuarioNumeroReservas() {
		return usuarioNumeroReservas;
	}

	public void setUsuarioNumeroReservas(int usuarioNumeroReservas) {
		this.usuarioNumeroReservas = usuarioNumeroReservas;
	}

	public boolean getUsuarioAtivo() {
		return usuarioAtivo;
	}

	public void setUsuarioAtivo(boolean usuarioAtivo) {
		this.usuarioAtivo = usuarioAtivo;
	}

	public String getUsuarioNumeroRg() {
		return usuarioNumeroRg;
	}

	public void setUsuarioNumeroRg(String usuarioNumeroRg) {
		this.usuarioNumeroRg = usuarioNumeroRg;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	 
	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuarioSenha() {
		return usuarioSenha;
	}

	public void setUsuarioSenha(String usuarioSenha) {
		this.usuarioSenha = usuarioSenha;
		//this.usuarioSenha = new BCryptPasswordEncoder().encode(usuarioSenha);
	}

	public String getUsuarioNumeroMatricula() {
		return usuarioNumeroMatricula;
	}

	public void setUsuarioNumeroMatricula(String usuarioNumeroMatricula) {
		this.usuarioNumeroMatricula = usuarioNumeroMatricula;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public String getUsuarioEmail() {
		return usuarioEmail;
	}

	public void setUsuarioEmail(String usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
	}

	public String getUsuarioCelular() {
		return usuarioCelular;
	}

	public void setUsuarioCelular(String usuarioCelular) {
		this.usuarioCelular = usuarioCelular;
	}

	public String getUsuarioCurso() {
		return usuarioCurso;
	}

	public void setUsuarioCurso(String usuarioCurso) {
		this.usuarioCurso = usuarioCurso;
	}

	public String getUsuarioSemestre() {
		return usuarioSemestre;
	}

	public void setUsuarioSemestre(String usuarioSemestre) {
		this.usuarioSemestre = usuarioSemestre;
	}

	public String getUsuarioTurno() {
		return usuarioTurno;
	}

	public void setUsuarioTurno(String usuarioTurno) {
		this.usuarioTurno = usuarioTurno;
	}
	
	
	
	
	
	
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("ROLE_" + role));
        return list;
    }

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.usuarioSenha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.usuarioEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuarioEmail == null) ? 0 : usuarioEmail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (usuarioEmail == null) {
			if (other.usuarioEmail != null)
				return false;
		} else if (!usuarioEmail.equals(other.usuarioEmail))
			return false;
		return true;
	}
	
	
	 @Override
	    public String toString() {
	        return "(NOME: " + usuarioNome + " / RG: " + usuarioNumeroRg + " / ATIVO: " + usuarioAtivo +" / Nº RESERVAS: " + usuarioNumeroReservas + " / Nº EMPRÉSTIMOS: " + usuarioNumeroEmprestimos + ")";
	    }

	
}
