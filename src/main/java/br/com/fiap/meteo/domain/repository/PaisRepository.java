package br.com.fiap.meteo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.meteo.domain.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> {
}
