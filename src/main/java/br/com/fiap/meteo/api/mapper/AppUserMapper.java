package br.com.fiap.meteo.api.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.meteo.api.dto.request.AppUserRequest;
import br.com.fiap.meteo.api.dto.response.AppUserResponse;
import br.com.fiap.meteo.domain.model.AppUser;

@Component
public class AppUserMapper {

    public AppUserResponse toResponse(AppUser user) {
        return new AppUserResponse(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getTipoUsuario(),
                user.getAtivo(),
                user.getCriadoEm()
        );
    }

    public AppUser toEntity(AppUserRequest request) {
        AppUser user = new AppUser();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setSenha(request.senha());
        user.setTipoUsuario(request.tipoUsuario());
        user.setAtivo(request.ativo());
        return user;
    }

    public void updateEntity(AppUser user, AppUserRequest request) {
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setSenha(request.senha());
        user.setTipoUsuario(request.tipoUsuario());
        user.setAtivo(request.ativo());
    }
}
