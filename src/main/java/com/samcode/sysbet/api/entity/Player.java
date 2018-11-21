package com.samcode.sysbet.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Player implements Serializable {

	private static final long serialVersionUID = -9119791651901633087L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@Column(unique = true)
	@NotBlank(message = "Name required!")
    private String name;
	
	private Boolean suspended;
	
	@Column(name = "qtd_cartao_amarelo")
	private Integer qtdCartaoAmarelo;
	
	@Column(name = "qtd_cartao_vermelho")
	private Integer qtdCartaoVermelho;
	
	@ManyToOne
	private Team team;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSuspended() {
		return suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	public Integer getQtdCartaoAmarelo() {
		return qtdCartaoAmarelo;
	}

	public void setQtdCartaoAmarelo(Integer qtdCartaoAmarelo) {
		this.qtdCartaoAmarelo = qtdCartaoAmarelo;
	}

	public Integer getQtdCartaoVermelho() {
		return qtdCartaoVermelho;
	}

	public void setQtdCartaoVermelho(Integer qtdCartaoVermelho) {
		this.qtdCartaoVermelho = qtdCartaoVermelho;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void suspend() {
		if(getQtdCartaoAmarelo() >= 3) {
			setSuspended(true);
			setQtdCartaoAmarelo(0);
		}
	}
	
}
