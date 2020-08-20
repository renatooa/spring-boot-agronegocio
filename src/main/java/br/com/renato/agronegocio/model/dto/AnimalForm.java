package br.com.renato.agronegocio.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.renato.agronegocio.model.entity.Animal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Animal Form")
public class AnimalForm {

	@NotNull
	@NotBlank
	@ApiModelProperty(name = "Identificação do Animal", example = "554ffFFgh")
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