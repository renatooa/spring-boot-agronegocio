package br.com.renato.agronegocio.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renato.agronegocio.model.dto.AnimalForm;
import br.com.renato.agronegocio.model.dto.FazendaDto;
import br.com.renato.agronegocio.model.dto.FazendaForm;
import br.com.renato.agronegocio.model.entity.Animal;
import br.com.renato.agronegocio.model.entity.Fazenda;
import br.com.renato.agronegocio.model.exception.NaoEncontradoException;
import br.com.renato.agronegocio.repository.FazendaRepository;

@Service
public class FazendaService {

	@Autowired
	private FazendaRepository fazendaRepository;

	public List<FazendaDto> listarFazendas() throws NaoEncontradoException {

		List<Fazenda> fazendas = fazendaRepository.findAll();

		return fazendas.stream().map(FazendaDto::new).collect(Collectors.toList());
	}

	public FazendaDto obterFazenda(Long idFazenda) throws NaoEncontradoException {

		return new FazendaDto(recuperarVerficarExistenciaFazenda(idFazenda));
	}

	public FazendaDto inserirFazenda(FazendaForm<AnimalForm> fazendaForm) {

		Fazenda fazenda = fazendaForm.toFazenda();

		fazendaRepository.save(fazenda);

		return new FazendaDto(fazenda);
	}

	public FazendaDto inserirAnimaisFazenda(Long idFazenda, List<AnimalForm> animais) throws NaoEncontradoException {

		Fazenda fazenda = recuperarVerficarExistenciaFazenda(idFazenda);

		inserirAtualizarAnimais(fazenda, animais);

		return obterFazenda(idFazenda);
	}

	public void apagarFazenda(Long idFazenda) throws NaoEncontradoException {

		Fazenda fazenda = recuperarVerficarExistenciaFazenda(idFazenda);

		fazendaRepository.delete(fazenda);
	}

	public FazendaDto editarFazenda(Long idFazenda, FazendaForm<AnimalForm> fazendaForm) throws NaoEncontradoException {

		Fazenda fazenda = recuperarVerficarExistenciaFazenda(idFazenda);

		atualizar(fazenda, fazendaForm);

		return obterFazenda(idFazenda);
	}

	private void atualizar(Fazenda fazenda, FazendaForm<AnimalForm> fazendaForm) {
		fazenda.setNome(fazendaForm.getNome());

		autualizarListaAnimais(fazenda, fazendaForm.getAnimais());
	}

	private void autualizarListaAnimais(Fazenda fazenda, List<AnimalForm> animais) {
		if (animais != null && animais.size() > 0) {

			inserirAtualizarAnimais(fazenda, animais);
			removerAnimais(fazenda, animais);
		}
	}

	private void inserirAtualizarAnimais(Fazenda fazenda, List<AnimalForm> animais) {

		Map<String, Animal> mapStringAnimal = fazenda.getAnimais().stream()
				.collect(Collectors.toMap(Animal::getIdentificacaoUnica, animal -> animal));

		animais.forEach(animal -> {

			if (mapStringAnimal.containsKey(animal.getIdentificacaoUnica())) {

				animal.atualizar(mapStringAnimal.get(animal.getIdentificacaoUnica()));

			} else {

				Animal animalFazenda = animal.toAnimal();
				fazenda.addAnimal(animalFazenda);
				mapStringAnimal.put(animal.getIdentificacaoUnica(), animalFazenda);
			}
		});
	}

	private void removerAnimais(Fazenda fazenda, List<AnimalForm> animais) {
		Map<String, Boolean> mapStringAnimal = animais.stream()
				.collect(Collectors.toMap(AnimalForm::getIdentificacaoUnica, a -> Boolean.TRUE));

		fazenda.getAnimais().removeIf(animal -> !mapStringAnimal.containsKey(animal.getIdentificacaoUnica()));
	}

	/**
	 * Destina-se a recuperar uma Fazenda pelo ID caso não exista será lançado
	 * {@link NaoEncontradoException a Exception}, caso contrario retorna a Fazenda
	 * 
	 * @param idFazenda O id alvo
	 * @return {@link Fazenda} Caso encontrada
	 * @throws NaoEncontradoException Caso não encontre a fazenda pelo idFazenda
	 */
	private Fazenda recuperarVerficarExistenciaFazenda(Long idFazenda) throws NaoEncontradoException {

		Optional<Fazenda> fazenda = fazendaRepository.findById(idFazenda);

		if (!fazenda.isPresent()) {
			throw new NaoEncontradoException();
		}

		return fazenda.get();
	}
}