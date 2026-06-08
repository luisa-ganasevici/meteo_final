package br.com.fiap.meteo.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AppUserRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Email @Size(max = 100) String email,
        @NotBlank @Size(max = 100) String senha,
        @NotBlank @Size(max = 30) String tipoUsuario,
        @NotBlank @Size(max = 1) String ativo
) {
}
