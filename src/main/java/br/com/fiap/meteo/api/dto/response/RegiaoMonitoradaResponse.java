package br.com.fiap.meteo.api.dto.response;

public record RegiaoMonitoradaResponse(
        Long id,
        String nome,
        Double latitude,
        Double longitude,
        Double altitudeMedia,
        String nivelUrbanizacao,
        String bairro
) {
}
