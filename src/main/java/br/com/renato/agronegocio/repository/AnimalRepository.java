package br.com.renato.agronegocio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.renato.agronegocio.model.entity.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

	public List<Animal> findByIdentificacaoUnica(String identificacaoUnica);
}