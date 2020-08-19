package br.com.renato.agronegocio.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.renato.agronegocio.model.entity.Fazenda;

public class FazendaForm<T extends AnimalForm> {

	private String nome;

	private List<T> animais;

	public FazendaForm() {
	}

	public FazendaForm(String nome, List<T> animais) {
		super();
		this.nome = nome;
		this.animais = animais;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<T> getAnimais() {
		return animais;
	}

	public void setAnimais(List<T> animais) {
		this.animais = animais;
	}

	@JsonIgnore
	public Fazenda toFazenda() {

		Fazenda fazenda = new Fazenda(nome);

		if (animais != null && animais.size() > 0) {
			animais.forEach(animal -> {
				fazenda.addAnimal(animal.toAnimal());
			});
		}
		return fazenda;
	}
}