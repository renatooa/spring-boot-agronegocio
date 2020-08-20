package br.com.renato.agronegocio.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renato.agronegocio.model.dto.AnimalDto;
import br.com.renato.agronegocio.model.dto.AnimalForm;
import br.com.renato.agronegocio.model.dto.FazendaDto;
import br.com.renato.agronegocio.model.dto.FazendaForm;
import br.com.renato.agronegocio.model.dto.MensagemDto;
import br.com.renato.agronegocio.model.exception.IdentificarAnimalDuplicadaException;
import br.com.renato.agronegocio.model.exception.ListaAnimaisException;
import br.com.renato.agronegocio.model.exception.NaoEncontradoException;
import br.com.renato.agronegocio.service.FazendaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/fazenda")
public class FazendaController {

	@Autowired
	private FazendaService fazendaService;

	@ApiOperation(value = "Recupera todas as Fazendas")
	@ApiResponses({ @ApiResponse(code = 200, message = "A lista de fazendas"),
			@ApiResponse(code = 204, message = "Nenhuma fazenda encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida", response = MensagemDto.class),
			@ApiResponse(code = 500, message = "Erro Interno Servidor", response = MensagemDto.class) })
	@GetMapping
	public List<FazendaDto> listarFazendas() throws NaoEncontradoException {
		return fazendaService.listarFazendas();
	}

	@ApiOperation(value = "Recupera uma fazenda especifica")
	@ApiResponses({ @ApiResponse(code = 200, message = "A fazenda de id correspondente"),
			@ApiResponse(code = 204, message = "Nenhuma fazenda encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida", response = MensagemDto.class),
			@ApiResponse(code = 500, message = "Erro Interno Servidor", response = MensagemDto.class) })
	@GetMapping(path = "/{idFazenda}")
	public FazendaDto obterFazenda(@PathVariable(required = true) Long idFazenda) throws NaoEncontradoException {
		return fazendaService.obterFazendaDto(idFazenda);
	}

	@ApiOperation(value = "Insere um fazenda")
	@ApiResponses({ @ApiResponse(code = 200, message = "A fazenda inserida"),
			@ApiResponse(code = 400, message = "Requisição inválida", response = MensagemDto.class),
			@ApiResponse(code = 500, message = "Erro Interno Servidor", response = MensagemDto.class) })
	@PostMapping
	@Transactional
	public FazendaDto inserirFazenda(@RequestBody @Valid FazendaForm<AnimalForm> fazendaForm) throws IdentificarAnimalDuplicadaException {

		return fazendaService.inserirFazenda(fazendaForm);
	}

	@ApiOperation(value = "Editar a fazensa pelo ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "A fazenda editada"),
			@ApiResponse(code = 204, message = "Nenhuma fazenda encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida", response = MensagemDto.class),
			@ApiResponse(code = 500, message = "Erro Interno Servidor", response = MensagemDto.class) })
	@PutMapping(path = "/{idFazenda}")
	@Transactional
	public FazendaDto editarFazenda(@RequestBody @Valid FazendaForm<AnimalForm> fazendaForm,
			@PathVariable(required = true) Long idFazenda) throws NaoEncontradoException, IdentificarAnimalDuplicadaException {

		return fazendaService.editarFazenda(idFazenda, fazendaForm);
	}

	@ApiOperation(value = "Apaga uma fazensa pelo ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 204, message = "Nenhuma fazenda encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida", response = MensagemDto.class),
			@ApiResponse(code = 500, message = "Erro Interno Servidor", response = MensagemDto.class) })
	@DeleteMapping(path = "/{idFazenda}")
	@Transactional
	public ResponseEntity<MensagemDto> apagarFazenda(@PathVariable(required = true) Long idFazenda)
			throws NaoEncontradoException {

		fazendaService.apagarFazenda(idFazenda);

		return ResponseEntity.ok(new MensagemDto());
	}

	@ApiOperation(value = "Insere uma coleção de animais na fazenda representada pelo ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "A fazenda modificada"),
			@ApiResponse(code = 204, message = "Nenhuma fazenda encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida", response = MensagemDto.class),
			@ApiResponse(code = 500, message = "Erro Interno Servidor", response = MensagemDto.class) })
	@Transactional
	@PostMapping(path = "/{idFazenda}/animais")
	public FazendaDto inserirAnimaisFazenda(@PathVariable(required = true) Long idFazenda,
			@RequestBody @Valid @NotNull List<AnimalForm> animais) throws NaoEncontradoException, ListaAnimaisException, IdentificarAnimalDuplicadaException {
		
		return fazendaService.inserirEditarAnimaisFazenda(idFazenda, animais);
	}

	@ApiOperation(value = "Edita uma coleção de animais na fazenda representada pelo ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "A fazenda modificada"),
			@ApiResponse(code = 204, message = "Nenhuma fazenda encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida", response = MensagemDto.class),
			@ApiResponse(code = 500, message = "Erro Interno Servidor", response = MensagemDto.class) })
	@Transactional
	@PutMapping(path = "/{idFazenda}/animais")
	public FazendaDto editarAnimaisFazenda(@PathVariable(required = true) Long idFazenda,
			@RequestBody @Valid @NotNull List<AnimalForm> animais) throws NaoEncontradoException, ListaAnimaisException, IdentificarAnimalDuplicadaException {

		return fazendaService.inserirEditarAnimaisFazenda(idFazenda, animais);
	}

	@ApiOperation(value = "Apaga uma coleção de animais na fazenda representada pelo ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "A fazenda modificada"),
			@ApiResponse(code = 204, message = "Nenhuma fazenda encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida", response = MensagemDto.class),
			@ApiResponse(code = 500, message = "Erro Interno Servidor", response = MensagemDto.class) })
	@Transactional
	@DeleteMapping(path = "/{idFazenda}/animais")
	public FazendaDto apagarAnimaisFazenda(@PathVariable(required = true) Long idFazenda,
			@RequestBody @Valid @NotNull List<AnimalForm> animais) throws NaoEncontradoException {

		return fazendaService.apagarAnimaisFazenda(idFazenda, animais);
	}

	@ApiOperation(value = "Lista os animais encontrados na fazenda representada pelo ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "A fazenda modificada"),
			@ApiResponse(code = 204, message = "Nenhuma fazenda encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida", response = MensagemDto.class),
			@ApiResponse(code = 500, message = "Erro Interno Servidor", response = MensagemDto.class) })
	@GetMapping(path = "/{idFazenda}/animais")
	public List<AnimalDto> listarAnimaisFazenda(@PathVariable(required = true) Long idFazenda)
			throws NaoEncontradoException {

		return fazendaService.listarAnimaisFazenda(idFazenda);
	}
}