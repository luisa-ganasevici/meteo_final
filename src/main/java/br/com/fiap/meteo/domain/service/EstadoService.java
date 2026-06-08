package br.com.fiap.meteo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.meteo.api.dto.request.EstadoRequest;
import br.com.fiap.meteo.api.dto.response.EstadoResponse;
import br.com.fiap.meteo.api.mapper.LocalizacaoMapper;
import br.com.fiap.meteo.core.exception.ResourceNotFoundException;
import br.com.fiap.meteo.domain.model.Estado;
import br.com.fiap.meteo.domain.model.Pais;
import br.com.fiap.meteo.domain.repository.EstadoRepository;
import br.com.fiap.meteo.domain.repository.PaisRepository;

@Service
@Transactional
public class EstadoService {

    private final EstadoRepository repository;
    private final PaisRepository paisRepository;
    private final LocalizacaoMapper mapper;

    public EstadoService(EstadoRepository repository, PaisRepository paisRepository, LocalizacaoMapper mapper) {
        this.repository = repository;
        this.paisRepository = paisRepository;
        this.mapper = mapper;
    }

    public Page<EstadoResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public EstadoResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }

    public EstadoResponse create(EstadoRequest request) {
        Pais pais = findCountryById(request.paisId());
        return mapper.toResponse(repository.save(mapper.toEntity(request, pais)));
    }

    public EstadoResponse update(Long id, EstadoRequest request) {
        Estado estado = findEntityById(id);
        Pais pais = findCountryById(request.paisId());
        mapper.updateEntity(estado, request, pais);
        return mapper.toResponse(repository.save(estado));
    }

    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    private Estado findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado nao encontrado"));
    }

    private Pais findCountryById(Long id) {
        return paisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pais nao encontrado"));
    }
}
