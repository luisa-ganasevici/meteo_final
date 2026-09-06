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

import br.com.fiap.meteo.api.dto.request.EstadoRequest;
import br.com.fiap.meteo.api.dto.response.EstadoResponse;
import br.com.fiap.meteo.domain.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService service;

    @GetMapping
    public ResponseEntity<Page<EstadoResponse>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EstadoResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(model(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<EstadoResponse>> create(@RequestBody @Valid EstadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(model(service.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EstadoResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid EstadoRequest request
    ) {
        return ResponseEntity.ok(model(service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<EstadoResponse> model(EstadoResponse response) {
        return EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
                        .findById(response.id())).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
                        .findAll(Pageable.unpaged())).withRel("estados"));
    }
}