package br.com.renato.agronegocio.model.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Fazenda Form")
public class FazendaForm<T extends AnimalForm> {

	@NotNull
	@NotBlank
	@ApiModelProperty(name = "Nome da Fazenda", example = "Fazenda Rio Grande")
	private String nome;

	@NotNull
	@Size(min = 1)
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
}