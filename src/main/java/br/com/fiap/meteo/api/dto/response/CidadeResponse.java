package br.com.fiap.meteo.api.dto.response;

public record CidadeResponse(
        Long id,
        String nome,
        Double latitude,
        Double longitude,
        Long estadoId,
        String estado
) {
}
