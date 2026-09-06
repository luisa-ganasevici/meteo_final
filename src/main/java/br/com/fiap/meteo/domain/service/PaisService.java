package br.com.fiap.meteo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.meteo.api.dto.request.PaisRequest;
import br.com.fiap.meteo.api.dto.response.PaisResponse;
import br.com.fiap.meteo.api.mapper.LocalizacaoMapper;
import br.com.fiap.meteo.core.exception.ResourceNotFoundException;
import br.com.fiap.meteo.domain.model.Pais;
import br.com.fiap.meteo.domain.repository.PaisRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaisService {

    private final PaisRepository repository;
    private final LocalizacaoMapper mapper;

    public Page<PaisResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public PaisResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }

    public PaisResponse create(PaisRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    public PaisResponse update(Long id, PaisRequest request) {
        Pais pais = findEntityById(id);
        mapper.updateEntity(pais, request);
        return mapper.toResponse(repository.save(pais));
    }

    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    private Pais findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pais nao encontrado"));
    }
}