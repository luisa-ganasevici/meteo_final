package br.com.fiap.meteo.api.dto.response;

public record BairroResponse(
        Long id,
        String nome,
        Integer populacao,
        Double areaKm2,
        Long cidadeId,
        String cidade
) {
}
