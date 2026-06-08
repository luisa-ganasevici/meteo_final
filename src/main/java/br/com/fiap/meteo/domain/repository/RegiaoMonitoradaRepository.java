package br.com.fiap.meteo.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.meteo.domain.model.RegiaoMonitorada;

public interface RegiaoMonitoradaRepository extends JpaRepository<RegiaoMonitorada, Long> {

    Optional<RegiaoMonitorada> findFirstByLatitudeBetweenAndLongitudeBetween(
            Double latMin, Double latMax, Double lonMin, Double lonMax
    );
}
