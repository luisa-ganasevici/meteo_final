package br.com.fiap.meteo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.meteo.api.dto.request.AppUserRequest;
import br.com.fiap.meteo.api.dto.response.AppUserResponse;
import br.com.fiap.meteo.api.mapper.AppUserMapper;
import br.com.fiap.meteo.core.exception.ResourceNotFoundException;
import br.com.fiap.meteo.domain.model.AppUser;
import br.com.fiap.meteo.domain.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;
    private final AppUserMapper mapper;

    public Page<AppUserResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public AppUserResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }

    public AppUserResponse create(AppUserRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    public AppUserResponse update(Long id, AppUserRequest request) {
        AppUser user = findEntityById(id);
        mapper.updateEntity(user, request);
        return mapper.toResponse(repository.save(user));
    }

    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    private AppUser findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));
    }
}