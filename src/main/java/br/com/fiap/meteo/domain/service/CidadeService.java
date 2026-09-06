package br.com.fiap.meteo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.meteo.api.dto.request.CidadeRequest;
import br.com.fiap.meteo.api.dto.response.CidadeResponse;
import br.com.fiap.meteo.api.mapper.LocalizacaoMapper;
import br.com.fiap.meteo.core.exception.ResourceNotFoundException;
import br.com.fiap.meteo.domain.model.Cidade;
import br.com.fiap.meteo.domain.model.Estado;
import br.com.fiap.meteo.domain.repository.CidadeRepository;
import br.com.fiap.meteo.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository repository;
    private final EstadoRepository estadoRepository;
    private final LocalizacaoMapper mapper;

    public Page<CidadeResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public CidadeResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }

    public CidadeResponse create(CidadeRequest request) {
        Estado estado = findStateById(request.estadoId());
        return mapper.toResponse(repository.save(mapper.toEntity(request, estado)));
    }

    public CidadeResponse update(Long id, CidadeRequest request) {
        Cidade cidade = findEntityById(id);
        Estado estado = findStateById(request.estadoId());
        mapper.updateEntity(cidade, request, estado);
        return mapper.toResponse(repository.save(cidade));
    }

    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    private Cidade findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade nao encontrada"));
    }

    private Estado findStateById(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado nao encontrado"));
    }
}