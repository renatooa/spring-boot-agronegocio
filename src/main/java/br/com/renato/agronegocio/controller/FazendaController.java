package br.com.renato.agronegocio.controller;

import java.util.List;

import javax.transaction.Transactional;

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

import br.com.renato.agronegocio.model.dto.AnimalForm;
import br.com.renato.agronegocio.model.dto.FazendaDto;
import br.com.renato.agronegocio.model.dto.FazendaForm;
import br.com.renato.agronegocio.model.exception.NaoEncontradoException;
import br.com.renato.agronegocio.service.FazendaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/fazenda")
public class FazendaController {

	@Autowired
	private FazendaService fazendaService;

	@GetMapping
	public List<FazendaDto> listarFazendas() throws NaoEncontradoException {
		return fazendaService.listarFazendas();
	}

	@GetMapping(path = "/{idFazenda}")
	public FazendaDto obterFazenda(@PathVariable(required = true) Long idFazenda) throws NaoEncontradoException {
		return fazendaService.obterFazenda(idFazenda);
	}

	@PostMapping
	@Transactional
	public FazendaDto inserirFazenda(@RequestBody FazendaForm<AnimalForm> fazendaForm) {

		return fazendaService.inserirFazenda(fazendaForm);
	}

	@PutMapping(path = "/{idFazenda}")
	@Transactional
	public FazendaDto editarFazenda(@RequestBody FazendaForm<AnimalForm> fazendaForm,
			@PathVariable(required = true) Long idFazenda) throws NaoEncontradoException {

		return fazendaService.editarFazenda(idFazenda, fazendaForm);
	}

	@DeleteMapping(path = "/{idFazenda}")
	@Transactional
	public ResponseEntity<?> apagarFazenda(@PathVariable(required = true) Long idFazenda)
			throws NaoEncontradoException {

		fazendaService.apagarFazenda(idFazenda);

		return ResponseEntity.ok().build();
	}

	@Transactional
	@PostMapping(path = "/{idFazenda}/animais")
	public FazendaDto inserirAnimaisFazenda(@PathVariable(required = true) Long idFazenda,
			@RequestBody List<AnimalForm> animais) throws NaoEncontradoException {

		return fazendaService.inserirAnimaisFazenda(idFazenda, animais);
	}
}