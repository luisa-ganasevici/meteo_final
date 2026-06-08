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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.meteo.api.dto.request.ConsultaRiscoRequest;
import br.com.fiap.meteo.api.dto.response.ConsultaRiscoResponse;
import br.com.fiap.meteo.domain.service.ConsultaRiscoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaRiscoController {

    private final ConsultaRiscoService service;

    public ConsultaRiscoController(ConsultaRiscoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityModel<ConsultaRiscoResponse>> create(
            @RequestBody @Valid ConsultaRiscoRequest request
    ) {
        ConsultaRiscoResponse response = service.create(request);
        EntityModel<ConsultaRiscoResponse> model = EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaRiscoController.class)
                        .findByUserIdAndProtocol(response.usuarioId(), response.protocolo())).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaRiscoController.class)
                        .findHistoryByUser(response.usuarioId(), Pageable.unpaged())).withRel("historicoUsuario"))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaRiscoController.class)
                        .findHistoryByRegion(response.regiaoId(), Pageable.unpaged())).withRel("historicoRegiao"));

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @GetMapping("/{usuarioId}/{protocolo}")
    public ResponseEntity<EntityModel<ConsultaRiscoResponse>> findByUserIdAndProtocol(
            @PathVariable Long usuarioId,
            @PathVariable String protocolo
    ) {
        ConsultaRiscoResponse response = service.findByUserIdAndProtocol(usuarioId, protocolo);
        EntityModel<ConsultaRiscoResponse> model = EntityModel.of(response)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaRiscoController.class)
                        .findByUserIdAndProtocol(usuarioId, protocolo)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/usuarios/{usuarioId}/historico")
    public ResponseEntity<Page<ConsultaRiscoResponse>> findHistoryByUser(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 10, sort = "criadoEm", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findHistoryByUser(usuarioId, pageable));
    }

    @GetMapping("/regioes/{regiaoId}/historico")
    public ResponseEntity<Page<ConsultaRiscoResponse>> findHistoryByRegion(
            @PathVariable Long regiaoId,
            @PageableDefault(size = 10, sort = "criadoEm", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.findHistoryByRegion(regiaoId, pageable));
    }
}
