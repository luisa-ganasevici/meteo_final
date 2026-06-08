package br.com.fiap.meteo.api.dto.response;

import java.time.LocalDateTime;

public record AppUserResponse(
        Long id,
        String nome,
        String email,
        String tipoUsuario,
        String ativo,
        LocalDateTime criadoEm
) {
}
