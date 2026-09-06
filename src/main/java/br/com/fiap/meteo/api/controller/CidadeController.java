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

import br.com.fiap.meteo.api.dto.request.CidadeRequest;
import br.com.fiap.meteo.api.dto.response.CidadeResponse;
import br.com.fiap.meteo.domain.service.CidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

    private final CidadeService service;

    @GetMapping
    public ResponseEntity<Page<CidadeResponse>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CidadeResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(model(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<CidadeResponse>> create(@RequestBody @Valid CidadeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(model(service.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CidadeResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid CidadeRequest request
    ) {
        return ResponseEntity.ok(model(service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<CidadeResponse> model(CidadeResponse response) {
        return EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
                        .findById(response.id())).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
                        .findAll(Pageable.unpaged())).withRel("cidades"));
    }
}