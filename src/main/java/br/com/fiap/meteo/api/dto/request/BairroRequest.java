package br.com.fiap.meteo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BairroRequest(
        @NotBlank @Size(max = 100) String nome,
        Integer populacao,
        Double areaKm2,
        @NotNull @Positive Long cidadeId
) {
}
