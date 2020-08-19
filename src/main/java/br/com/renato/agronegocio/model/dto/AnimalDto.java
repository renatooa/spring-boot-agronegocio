package br.com.renato.agronegocio.model.dto;

import br.com.renato.agronegocio.model.entity.Animal;

public class AnimalDto extends AnimalForm {

	private Long id;

	public AnimalDto() {
	}

	public AnimalDto(Animal animal) {
		super(animal.getIdentificacaoUnica());
		this.id = animal.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}