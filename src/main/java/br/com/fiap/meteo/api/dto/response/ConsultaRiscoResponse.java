package br.com.fiap.meteo.api.dto.response;

import java.time.LocalDateTime;

public record ConsultaRiscoResponse(
        Long usuarioId,
        String protocolo,
        String prompt,
        Long regiaoId,
        String regiao,
        String ruaOuBairro,
        Double latitude,
        Double longitude,
        LocalDateTime consultadoEm,
        String nivelRisco,
        String resposta
) {
}
