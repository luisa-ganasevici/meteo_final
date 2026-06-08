package br.com.fiap.meteo.api.dto.response;

public record PaisResponse(
        Long id,
        String nome,
        String codigoIso
) {
}
