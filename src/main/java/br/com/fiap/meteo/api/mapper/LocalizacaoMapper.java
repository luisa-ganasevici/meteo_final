package br.com.fiap.meteo.api.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.meteo.api.dto.request.BairroRequest;
import br.com.fiap.meteo.api.dto.request.CidadeRequest;
import br.com.fiap.meteo.api.dto.request.EstadoRequest;
import br.com.fiap.meteo.api.dto.request.PaisRequest;
import br.com.fiap.meteo.api.dto.response.BairroResponse;
import br.com.fiap.meteo.api.dto.response.CidadeResponse;
import br.com.fiap.meteo.api.dto.response.EstadoResponse;
import br.com.fiap.meteo.api.dto.response.PaisResponse;
import br.com.fiap.meteo.domain.model.Bairro;
import br.com.fiap.meteo.domain.model.Cidade;
import br.com.fiap.meteo.domain.model.Coordenada;
import br.com.fiap.meteo.domain.model.Estado;
import br.com.fiap.meteo.domain.model.Pais;

@Component
public class LocalizacaoMapper {

    public PaisResponse toResponse(Pais pais) {
        return new PaisResponse(pais.getId(), pais.getNome(), pais.getCodigoIso());
    }

    public EstadoResponse toResponse(Estado estado) {
        return new EstadoResponse(
                estado.getId(),
                estado.getNome(),
                estado.getSigla(),
                estado.getPais().getId(),
                estado.getPais().getNome()
        );
    }

    public CidadeResponse toResponse(Cidade cidade) {
        return new CidadeResponse(
                cidade.getId(),
                cidade.getNome(),
                cidade.getCoordenada().getLatitude(),
                cidade.getCoordenada().getLongitude(),
                cidade.getEstado().getId(),
                cidade.getEstado().getNome()
        );
    }

    public BairroResponse toResponse(Bairro bairro) {
        return new BairroResponse(
                bairro.getId(),
                bairro.getNome(),
                bairro.getPopulacao(),
                bairro.getAreaKm2(),
                bairro.getCidade().getId(),
                bairro.getCidade().getNome()
        );
    }

    public Pais toEntity(PaisRequest request) {
        Pais pais = new Pais();
        updateEntity(pais, request);
        return pais;
    }

    public void updateEntity(Pais pais, PaisRequest request) {
        pais.setNome(request.nome());
        pais.setCodigoIso(request.codigoIso());
    }

    public Estado toEntity(EstadoRequest request, Pais pais) {
        Estado estado = new Estado();
        updateEntity(estado, request, pais);
        return estado;
    }

    public void updateEntity(Estado estado, EstadoRequest request, Pais pais) {
        estado.setNome(request.nome());
        estado.setSigla(request.sigla());
        estado.setPais(pais);
    }

    public Cidade toEntity(CidadeRequest request, Estado estado) {
        Cidade cidade = new Cidade();
        updateEntity(cidade, request, estado);
        return cidade;
    }

    public void updateEntity(Cidade cidade, CidadeRequest request, Estado estado) {
        cidade.setNome(request.nome());
        cidade.setCoordenada(new Coordenada(request.latitude(), request.longitude()));
        cidade.setEstado(estado);
    }

    public Bairro toEntity(BairroRequest request, Cidade cidade) {
        Bairro bairro = new Bairro();
        updateEntity(bairro, request, cidade);
        return bairro;
    }

    public void updateEntity(Bairro bairro, BairroRequest request, Cidade cidade) {
        bairro.setNome(request.nome());
        bairro.setPopulacao(request.populacao());
        bairro.setAreaKm2(request.areaKm2());
        bairro.setCidade(cidade);
    }
}
