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

import br.com.fiap.meteo.api.dto.request.BairroRequest;
import br.com.fiap.meteo.api.dto.response.BairroResponse;
import br.com.fiap.meteo.domain.service.BairroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bairros")
public class BairroController {

    private final BairroService service;

    public BairroController(BairroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<BairroResponse>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BairroResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(model(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<BairroResponse>> create(@RequestBody @Valid BairroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(model(service.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BairroResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid BairroRequest request
    ) {
        return ResponseEntity.ok(model(service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<BairroResponse> model(BairroResponse response) {
        return EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BairroController.class)
                        .findById(response.id())).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BairroController.class)
                        .findAll(Pageable.unpaged())).withRel("bairros"));
    }
}
