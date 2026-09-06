package br.com.fiap.meteo.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.meteo.api.dto.request.AppUserRequest;
import br.com.fiap.meteo.api.dto.response.AppUserResponse;
import br.com.fiap.meteo.domain.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService service;

    @GetMapping
    public ResponseEntity<Page<AppUserResponse>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AppUserResponse>> findById(@PathVariable Long id) {
        AppUserResponse response = service.findById(id);
        EntityModel<AppUserResponse> model = EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppUserController.class).findById(id)).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppUserController.class).findAll(Pageable.unpaged())).withRel("usuarios"));
        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel<AppUserResponse>> create(@RequestBody @Valid AppUserRequest request) {
        AppUserResponse response = service.create(request);
        EntityModel<AppUserResponse> model = EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppUserController.class).findById(response.id())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AppUserResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid AppUserRequest request
    ) {
        AppUserResponse response = service.update(id, request);
        EntityModel<AppUserResponse> model = EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppUserController.class).findById(response.id())).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}