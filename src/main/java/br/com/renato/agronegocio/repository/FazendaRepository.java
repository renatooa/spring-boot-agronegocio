package br.com.renato.agronegocio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.renato.agronegocio.model.entity.Fazenda;

@Repository
public interface FazendaRepository extends JpaRepository<Fazenda, Long> {
}