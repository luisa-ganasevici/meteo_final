package br.com.fiap.meteo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.meteo.api.dto.request.BairroRequest;
import br.com.fiap.meteo.api.dto.response.BairroResponse;
import br.com.fiap.meteo.api.mapper.LocalizacaoMapper;
import br.com.fiap.meteo.core.exception.ResourceNotFoundException;
import br.com.fiap.meteo.domain.model.Bairro;
import br.com.fiap.meteo.domain.model.Cidade;
import br.com.fiap.meteo.domain.repository.BairroRepository;
import br.com.fiap.meteo.domain.repository.CidadeRepository;

@Service
@Transactional
public class BairroService {

    private final BairroRepository repository;
    private final CidadeRepository cidadeRepository;
    private final LocalizacaoMapper mapper;

    public BairroService(BairroRepository repository, CidadeRepository cidadeRepository, LocalizacaoMapper mapper) {
        this.repository = repository;
        this.cidadeRepository = cidadeRepository;
        this.mapper = mapper;
    }

    public Page<BairroResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public BairroResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }

    public BairroResponse create(BairroRequest request) {
        Cidade cidade = findCityById(request.cidadeId());
        return mapper.toResponse(repository.save(mapper.toEntity(request, cidade)));
    }

    public BairroResponse update(Long id, BairroRequest request) {
        Bairro bairro = findEntityById(id);
        Cidade cidade = findCityById(request.cidadeId());
        mapper.updateEntity(bairro, request, cidade);
        return mapper.toResponse(repository.save(bairro));
    }

    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    private Bairro findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bairro nao encontrado"));
    }

    private Cidade findCityById(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade nao encontrada"));
    }
}
