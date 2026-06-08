package br.com.fiap.meteo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.meteo.domain.model.Bairro;

public interface BairroRepository extends JpaRepository<Bairro, Long> {
}
