package br.com.renato.agronegocio.model.dto;

import br.com.renato.agronegocio.model.entity.Animal;

public class AnimalForm {

	private String identificacaoUnica;

	public AnimalForm() {
	}

	public AnimalForm(String identificacaoUnica) {
		super();
		this.identificacaoUnica = identificacaoUnica;
	}

	public String getIdentificacaoUnica() {
		return identificacaoUnica;
	}

	public void setIdentificacaoUnica(String identificacaoUnica) {
		this.identificacaoUnica = identificacaoUnica;
	}

	public Animal toAnimal() {
		return new Animal(getIdentificacaoUnica());
	}

	public void atualizar(Animal animal) {
		animal.setIdentificacaoUnica(getIdentificacaoUnica());

	}
}