package br.com.fiap.meteo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.meteo.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
}
