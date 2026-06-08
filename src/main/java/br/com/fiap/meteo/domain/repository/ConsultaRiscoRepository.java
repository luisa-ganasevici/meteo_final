package br.com.fiap.meteo.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.meteo.domain.model.ConsultaRisco;
import br.com.fiap.meteo.domain.model.ConsultaRiscoId;

public interface ConsultaRiscoRepository extends JpaRepository<ConsultaRisco, ConsultaRiscoId> {

    Page<ConsultaRisco> findByIdUsuarioId(Long usuarioId, Pageable pageable);

    Page<ConsultaRisco> findByRegiaoMonitoradaId(Long regiaoId, Pageable pageable);
}
