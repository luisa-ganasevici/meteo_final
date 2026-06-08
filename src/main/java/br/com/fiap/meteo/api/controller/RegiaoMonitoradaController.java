package br.com.fiap.meteo.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.meteo.api.dto.request.RegiaoMonitoradaRequest;
import br.com.fiap.meteo.api.dto.response.RegiaoMonitoradaResponse;
import br.com.fiap.meteo.domain.service.RegiaoMonitoradaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/regioes")
public class RegiaoMonitoradaController {

    private final RegiaoMonitoradaService service;

    public RegiaoMonitoradaController(RegiaoMonitoradaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<RegiaoMonitoradaResponse>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RegiaoMonitoradaResponse>> findById(@PathVariable Long id) {
        RegiaoMonitoradaResponse response = service.findById(id);
        EntityModel<RegiaoMonitoradaResponse> model = EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegiaoMonitoradaController.class).findById(id)).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegiaoMonitoradaController.class).findAll(Pageable.unpaged())).withRel("regioes"))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaRiscoController.class)
                        .findHistoryByRegion(id, Pageable.unpaged())).withRel("historicoConsultas"));
        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel<RegiaoMonitoradaResponse>> create(
            @RequestBody @Valid RegiaoMonitoradaRequest request
    ) {
        RegiaoMonitoradaResponse response = service.create(request);
        EntityModel<RegiaoMonitoradaResponse> model = EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegiaoMonitoradaController.class)
                        .findById(response.id())).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegiaoMonitoradaController.class)
                        .findAll(Pageable.unpaged())).withRel("regioes"));
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RegiaoMonitoradaResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid RegiaoMonitoradaRequest request
    ) {
        RegiaoMonitoradaResponse response = service.update(id, request);
        EntityModel<RegiaoMonitoradaResponse> model = EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegiaoMonitoradaController.class)
                        .findById(response.id())).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegiaoMonitoradaController.class)
                        .findAll(Pageable.unpaged())).withRel("regioes"));
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
