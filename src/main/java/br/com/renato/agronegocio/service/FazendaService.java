package br.com.renato.agronegocio.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renato.agronegocio.model.dto.AnimalDto;
import br.com.renato.agronegocio.model.dto.AnimalForm;
import br.com.renato.agronegocio.model.dto.FazendaDto;
import br.com.renato.agronegocio.model.dto.FazendaForm;
import br.com.renato.agronegocio.model.entity.Animal;
import br.com.renato.agronegocio.model.entity.Fazenda;
import br.com.renato.agronegocio.model.exception.IdentificarAnimalDuplicadaException;
import br.com.renato.agronegocio.model.exception.ListaAnimaisException;
import br.com.renato.agronegocio.model.exception.NaoEncontradoException;
import br.com.renato.agronegocio.repository.AnimalRepository;
import br.com.renato.agronegocio.repository.FazendaRepository;

@Service
public class FazendaService {

	@Autowired
	private FazendaRepository fazendaRepository;

	@Autowired
	private AnimalRepository animalRepository;

	public List<FazendaDto> listarFazendas() throws NaoEncontradoException {

		List<Fazenda> fazendas = fazendaRepository.findAll();

		return fazendas.stream().map(FazendaDto::new).collect(Collectors.toList());
	}

	public FazendaDto obterFazendaDto(Long idFazenda) throws NaoEncontradoException {

		return new FazendaDto(recuperarVerficarExistenciaFazenda(idFazenda));
	}

	public FazendaDto inserirFazenda(FazendaForm<AnimalForm> fazendaForm) throws IdentificarAnimalDuplicadaException {

		Fazenda fazenda = criarFazenda(fazendaForm);

		fazendaRepository.save(fazenda);

		return new FazendaDto(fazenda);
	}

	public FazendaDto inserirEditarAnimaisFazenda(Long idFazenda, List<AnimalForm> animais)
			throws NaoEncontradoException, ListaAnimaisException, IdentificarAnimalDuplicadaException {

		validarListaAnimais(animais);

		Fazenda fazenda = recuperarVerficarExistenciaFazenda(idFazenda);

		inserirAtualizarAnimais(fazenda, animais);

		return obterFazendaDto(idFazenda);
	}

	public void apagarFazenda(Long idFazenda) throws NaoEncontradoException {

		Fazenda fazenda = recuperarVerficarExistenciaFazenda(idFazenda);

		fazendaRepository.delete(fazenda);
	}

	public FazendaDto editarFazenda(Long idFazenda, FazendaForm<AnimalForm> fazendaForm)
			throws NaoEncontradoException, IdentificarAnimalDuplicadaException {

		Fazenda fazenda = recuperarVerficarExistenciaFazenda(idFazenda);

		atualizar(fazenda, fazendaForm);

		return obterFazendaDto(idFazenda);
	}

	public FazendaDto apagarAnimaisFazenda(Long idFazenda, List<AnimalForm> animais) throws NaoEncontradoException {

		Fazenda fazenda = recuperarVerficarExistenciaFazenda(idFazenda);

		removerAnimais(fazenda, animais, false);

		return obterFazendaDto(idFazenda);
	}

	public List<AnimalDto> listarAnimaisFazenda(Long idFazenda) throws NaoEncontradoException {

		Fazenda fazenda = recuperarVerficarExistenciaFazenda(idFazenda);

		if (fazenda.isContemAnimais()) {
			return fazenda.getAnimais().stream().map(AnimalDto::new).collect(Collectors.toList());
		}

		throw new NaoEncontradoException();

	}

	private void atualizar(Fazenda fazenda, FazendaForm<AnimalForm> fazendaForm)
			throws IdentificarAnimalDuplicadaException {

		fazenda.setNome(fazendaForm.getNome());
		autualizarListaAnimais(fazenda, fazendaForm.getAnimais());
	}

	private void autualizarListaAnimais(Fazenda fazenda, List<AnimalForm> animais)
			throws IdentificarAnimalDuplicadaException {
		if (animais != null && animais.size() > 0) {
			inserirAtualizarAnimais(fazenda, animais);
			removerAnimais(fazenda, animais, true);
		}
	}

	private void inserirAtualizarAnimais(Fazenda fazenda, List<AnimalForm> animais)
			throws IdentificarAnimalDuplicadaException {

		Map<String, Animal> mapStringAnimal = fazenda.getAnimais().stream()
				.collect(Collectors.toMap(Animal::getIdentificacaoUnica, animal -> animal));

		for (AnimalForm animal : animais) {

			if (mapStringAnimal.containsKey(animal.getIdentificacaoUnica())) {

				animal.atualizar(mapStringAnimal.get(animal.getIdentificacaoUnica()));

			} else {

				Animal animalFazenda = addAnimalFazenda(fazenda, animal);
				mapStringAnimal.put(animal.getIdentificacaoUnica(), animalFazenda);
			}
		}
	}

	/**
	 * Remove os animais da Fazenda baseado na lista de animais em parametro.</br>
	 * Para remover os animais da lista defina o parametro removerAnimaisDiferentes
	 * como <code>false</code>, para remover os animais que não estão na lista
	 * defina como <code>true</code>
	 * 
	 * @param fazenda                  {@link Fazenda}
	 * @param animais                  {@link List<Animal>}
	 * @param removerAnimaisDiferentes Define o comportamnto de teste da lista
	 */
	private void removerAnimais(Fazenda fazenda, List<AnimalForm> animais, boolean removerAnimaisDiferentes) {

		Map<String, Boolean> mapStringAnimal = animais.stream()
				.collect(Collectors.toMap(AnimalForm::getIdentificacaoUnica, a -> Boolean.TRUE));

		fazenda.getAnimais().removeIf(animal -> {

			if (removerAnimaisDiferentes) {
				return !mapStringAnimal.containsKey(animal.getIdentificacaoUnica());
			} else {
				return mapStringAnimal.containsKey(animal.getIdentificacaoUnica());
			}
		});
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

	private void validarIdentificacaoUnicaAnimal(String identificacaoUnica) throws IdentificarAnimalDuplicadaException {

		List<Animal> animal = animalRepository.findByIdentificacaoUnica(identificacaoUnica);

		if (animal != null && animal.size() > 0) {
			throw new IdentificarAnimalDuplicadaException();
		}
	}

	private void validarListaAnimais(List<AnimalForm> animais) throws ListaAnimaisException {
		if (animais == null || animais.isEmpty()) {
			throw new ListaAnimaisException("A lista de animais não pode ser vazia");
		}
	}

	private Fazenda criarFazenda(FazendaForm<AnimalForm> fazendaForm) throws IdentificarAnimalDuplicadaException {

		Fazenda fazenda = new Fazenda(fazendaForm.getNome());

		if (fazendaForm.getAnimais() != null && fazendaForm.getAnimais().size() > 0) {
			for (AnimalForm animal : fazendaForm.getAnimais()) {
				addAnimalFazenda(fazenda, animal);
			}
		}
		return fazenda;
	}

	private Animal addAnimalFazenda(Fazenda fazenda, AnimalForm animalForm) throws IdentificarAnimalDuplicadaException {

		validarIdentificacaoUnicaAnimal(animalForm.getIdentificacaoUnica());

		Animal animalFazenda = animalForm.toAnimal();

		fazenda.addAnimal(animalFazenda);

		return animalFazenda;
	}
}