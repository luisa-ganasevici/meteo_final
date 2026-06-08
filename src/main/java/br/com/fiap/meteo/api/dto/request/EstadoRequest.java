package br.com.fiap.meteo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EstadoRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Size(max = 25) String sigla,
        @NotNull @Positive Long paisId
) {
}
