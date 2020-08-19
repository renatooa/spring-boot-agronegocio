package br.com.renato.agronegocio.model.dto;

import java.util.stream.Collectors;

import br.com.renato.agronegocio.model.entity.Fazenda;

public class FazendaDto extends FazendaForm<AnimalDto> {

	private Long id;

	public FazendaDto() {
	}

	public FazendaDto(Fazenda fazenda) {
		super(fazenda.getNome(), fazenda.getAnimais().stream().map(AnimalDto::new).collect(Collectors.toList()));

		this.id = fazenda.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}