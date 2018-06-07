package com.biblioteca.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.validation.annotation.Validated;

@SuppressWarnings("serial")
@Entity
@Validated
public class Info implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long infoId;
	
	private int infoReservaCount = 0;
	
	private int infoEmprestimoCount = 0;
	
	private int infoUsuarioCount = 0;
	
	private int infoObraCount = 0;
	
	private int infoBibliotecarioCount = 0;
	
	
	
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	public int getInfoReservaCount() {
		return infoReservaCount;
	}
	public void setInfoReservaCount(int infoReservaCount) {
		this.infoReservaCount = infoReservaCount;
	}
	public int getInfoEmprestimoCount() {
		return infoEmprestimoCount;
	}
	public void setInfoEmprestimoCount(int infoEmprestimoCount) {
		this.infoEmprestimoCount = infoEmprestimoCount;
	}
	public int getInfoUsuarioCount() {
		return infoUsuarioCount;
	}
	public void setInfoUsuarioCount(int infoUsuarioCount) {
		this.infoUsuarioCount = infoUsuarioCount;
	}
	public int getInfoObraCount() {
		return infoObraCount;
	}
	public void setInfoObraCount(int infoObraCount) {
		this.infoObraCount = infoObraCount;
	}
	public int getInfoBibliotecarioCount() {
		return infoBibliotecarioCount;
	}
	public void setInfoBibliotecarioCount(int infoBibliotecarioCount) {
		this.infoBibliotecarioCount = infoBibliotecarioCount;
	}
	
	

}
