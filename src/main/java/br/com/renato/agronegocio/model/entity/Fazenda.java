package br.com.renato.agronegocio.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Fazenda {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String nome;

	@OneToMany(mappedBy = "fazenda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Animal> animais;

	public Fazenda() {
	}

	public Fazenda(String nome) {
		super();
		this.nome = nome;
		this.animais = new ArrayList<Animal>();
	}

	public Fazenda(long id, String nome, List<Animal> animais) {
		super();
		this.id = id;
		this.nome = nome;
		this.animais = animais;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Animal> getAnimais() {
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}

	public void addAnimal(Animal animal) {
		animal.setFazenda(this);
		animais.add(animal);
	}

	public boolean isContemAnimais() {
		return animais != null && !animais.isEmpty();
	}
}
