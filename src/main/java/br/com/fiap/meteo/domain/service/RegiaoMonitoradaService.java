package br.com.fiap.meteo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.meteo.api.dto.request.RegiaoMonitoradaRequest;
import br.com.fiap.meteo.api.dto.response.RegiaoMonitoradaResponse;
import br.com.fiap.meteo.api.mapper.RegiaoMonitoradaMapper;
import br.com.fiap.meteo.core.exception.ResourceNotFoundException;
import br.com.fiap.meteo.domain.model.Bairro;
import br.com.fiap.meteo.domain.model.RegiaoMonitorada;
import br.com.fiap.meteo.domain.repository.BairroRepository;
import br.com.fiap.meteo.domain.repository.RegiaoMonitoradaRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegiaoMonitoradaService {

    private final RegiaoMonitoradaRepository repository;
    private final BairroRepository bairroRepository;
    private final RegiaoMonitoradaMapper mapper;

    public Page<RegiaoMonitoradaResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public RegiaoMonitoradaResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }

    public RegiaoMonitoradaResponse create(RegiaoMonitoradaRequest request) {
        Bairro bairro = findNeighborhoodById(request.bairroId());
        return mapper.toResponse(repository.save(mapper.toEntity(request, bairro)));
    }

    public RegiaoMonitoradaResponse update(Long id, RegiaoMonitoradaRequest request) {
        RegiaoMonitorada regiao = findEntityById(id);
        Bairro bairro = findNeighborhoodById(request.bairroId());
        mapper.updateEntity(regiao, request, bairro);
        return mapper.toResponse(repository.save(regiao));
    }

    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    private RegiaoMonitorada findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Regiao nao encontrada"));
    }

    private Bairro findNeighborhoodById(Long id) {
        return bairroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bairro nao encontrado"));
    }
}