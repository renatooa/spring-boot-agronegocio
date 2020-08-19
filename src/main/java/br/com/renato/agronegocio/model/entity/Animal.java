package br.com.renato.agronegocio.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String identificacaoUnica;

	@ManyToOne
	@JoinColumn(name = "fazenda_id", referencedColumnName = "id")
	private Fazenda fazenda;

	public Animal() {
	}

	public Animal(Long id, String identificacaoUnica, Fazenda fazenda) {
		super();
		this.id = id;
		this.identificacaoUnica = identificacaoUnica;
		this.fazenda = fazenda;
	}

	public Animal(String identificacaoUnica) {
		super();
		this.identificacaoUnica = identificacaoUnica;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificacaoUnica() {
		return identificacaoUnica;
	}

	public void setIdentificacaoUnica(String identificacaoUnica) {
		this.identificacaoUnica = identificacaoUnica;
	}

	public Fazenda getFazenda() {
		return fazenda;
	}

	public void setFazenda(Fazenda fazenda) {
		this.fazenda = fazenda;
	}
}