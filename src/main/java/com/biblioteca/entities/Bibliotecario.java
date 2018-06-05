package com.biblioteca.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Bibliotecario implements Serializable, UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long bibliotecarioId;
	
	private String role ="ADMIN";

	@NotEmpty
	private String bibliotecarioNome;

	@NotEmpty
	private String bibliotecarioSenha;
	
	@Column(unique = true)
    @NotNull
	private String bibliotecarioEmail;
	
	@NotEmpty
	private String bibliotecarioCelular;
	
	@NotEmpty
	private String bibliotecarioTurno;
	
	
	
	
	
	
	
	
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Long getBibliotecarioId() {
		return bibliotecarioId;
	}

	public void setBibliotecarioId(Long bibliotecarioId) {
		this.bibliotecarioId = bibliotecarioId;
	}

	public String getBibliotecarioNome() {
		return bibliotecarioNome;
	}

	public void setBibliotecarioNome(String bibliotecarioNome) {
		this.bibliotecarioNome = bibliotecarioNome;
	}
	
	public String getBibliotecarioSenha() {
		return bibliotecarioSenha;
	}

	public void setBibliotecarioSenha(String bibliotecarioSenha) {
		this.bibliotecarioSenha = bibliotecarioSenha;
		//this.bibliotecarioSenha = new BCryptPasswordEncoder().encode(bibliotecarioSenha);
	}

	public String getBibliotecarioEmail() {
		return bibliotecarioEmail;
	}

	public void setBibliotecarioEmail(String bibliotecarioEmail) {
		this.bibliotecarioEmail = bibliotecarioEmail;
	}

	public String getBibliotecarioCelular() {
		return bibliotecarioCelular;
	}

	public void setBibliotecarioCelular(String bibliotecarioCelular) {
		this.bibliotecarioCelular = bibliotecarioCelular;
	}

	public String getBibliotecarioTurno() {
		return bibliotecarioTurno;
	}

	public void setBibliotecarioTurno(String bibliotecarioTurno) {
		this.bibliotecarioTurno = bibliotecarioTurno;
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
		return this.bibliotecarioSenha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.bibliotecarioEmail;
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
		result = prime * result + ((bibliotecarioEmail == null) ? 0 : bibliotecarioEmail.hashCode());
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
		Bibliotecario other = (Bibliotecario) obj;
		if (bibliotecarioEmail == null) {
			if (other.bibliotecarioEmail != null)
				return false;
		} else if (!bibliotecarioEmail.equals(other.bibliotecarioEmail))
			return false;
		return true;
	}
	
	
	
	
	

	
	/*
	private String ROLE_PREFIX = "ROLE_";
	
	public String getROLE_PREFIX() {
		return ROLE_PREFIX;
	}

	public void setROLE_PREFIX(String rOLE_PREFIX) {
		ROLE_PREFIX = rOLE_PREFIX;
	}
	*/
}
