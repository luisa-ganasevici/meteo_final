package br.com.fiap.meteo.api.dto.response;

public record EstadoResponse(
        Long id,
        String nome,
        String sigla,
        Long paisId,
        String pais
) {
}
