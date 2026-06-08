package br.com.fiap.meteo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.meteo.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
