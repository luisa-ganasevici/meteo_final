package br.com.fiap.meteo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PaisRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Size(max = 5) String codigoIso
) {
}
