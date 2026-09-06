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

import br.com.fiap.meteo.api.dto.request.PaisRequest;
import br.com.fiap.meteo.api.dto.response.PaisResponse;
import br.com.fiap.meteo.domain.service.PaisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/paises")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService service;

    @GetMapping
    public ResponseEntity<Page<PaisResponse>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PaisResponse>> findById(@PathVariable Long id) {
        PaisResponse response = service.findById(id);
        return ResponseEntity.ok(model(response));
    }

    @PostMapping
    public ResponseEntity<EntityModel<PaisResponse>> create(@RequestBody @Valid PaisRequest request) {
        PaisResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(model(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PaisResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid PaisRequest request
    ) {
        return ResponseEntity.ok(model(service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<PaisResponse> model(PaisResponse response) {
        return EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaisController.class)
                        .findById(response.id())).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaisController.class)
                        .findAll(Pageable.unpaged())).withRel("paises"));
    }
}