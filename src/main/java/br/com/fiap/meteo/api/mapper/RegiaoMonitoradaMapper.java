package br.com.fiap.meteo.api.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.meteo.api.dto.request.RegiaoMonitoradaRequest;
import br.com.fiap.meteo.api.dto.response.RegiaoMonitoradaResponse;
import br.com.fiap.meteo.domain.model.Bairro;
import br.com.fiap.meteo.domain.model.RegiaoMonitorada;

@Component
public class RegiaoMonitoradaMapper {

    public RegiaoMonitoradaResponse toResponse(RegiaoMonitorada regiao) {
        return new RegiaoMonitoradaResponse(
                regiao.getId(),
                regiao.getNome(),
                regiao.getLatitude(),
                regiao.getLongitude(),
                regiao.getAltitudeMedia(),
                regiao.getNivelUrbanizacao(),
                regiao.getBairro().getNome()
        );
    }

    public RegiaoMonitorada toEntity(RegiaoMonitoradaRequest request, Bairro bairro) {
        RegiaoMonitorada regiao = new RegiaoMonitorada();
        updateEntity(regiao, request, bairro);
        return regiao;
    }

    public void updateEntity(RegiaoMonitorada regiao, RegiaoMonitoradaRequest request, Bairro bairro) {
        regiao.setNome(request.nome());
        regiao.setLatitude(request.latitude());
        regiao.setLongitude(request.longitude());
        regiao.setAltitudeMedia(request.altitudeMedia());
        regiao.setDeclividadePercentual(request.declividadePercentual());
        regiao.setCoberturaVegetalPercentual(request.coberturaVegetalPercentual());
        regiao.setImpermeabilizacaoPercentual(request.impermeabilizacaoPercentual());
        regiao.setDistanciaRioMetros(request.distanciaRioMetros());
        regiao.setTipoSolo(request.tipoSolo());
        regiao.setNivelUrbanizacao(request.nivelUrbanizacao());
        regiao.setAtiva(request.ativa() == null ? "S" : request.ativa());
        regiao.setBairro(bairro);
    }
}
