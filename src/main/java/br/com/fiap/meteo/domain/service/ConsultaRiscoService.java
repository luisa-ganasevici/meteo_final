package br.com.fiap.meteo.domain.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.meteo.api.dto.request.ConsultaRiscoRequest;
import br.com.fiap.meteo.api.dto.response.ConsultaRiscoResponse;
import br.com.fiap.meteo.core.exception.BusinessException;
import br.com.fiap.meteo.core.exception.ResourceNotFoundException;
import br.com.fiap.meteo.domain.model.AppUser;
import br.com.fiap.meteo.domain.model.ConsultaRisco;
import br.com.fiap.meteo.domain.model.ConsultaRiscoId;
import br.com.fiap.meteo.domain.model.Coordenada;
import br.com.fiap.meteo.domain.model.RegiaoMonitorada;
import br.com.fiap.meteo.domain.repository.AppUserRepository;
import br.com.fiap.meteo.domain.repository.ConsultaRiscoRepository;
import br.com.fiap.meteo.domain.repository.RegiaoMonitoradaRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultaRiscoService {

    private static final double DELTA_LOCALIZACAO = 0.05;

    private final RegiaoMonitoradaRepository regiaoRepository;
    private final AppUserRepository userRepository;
    private final ConsultaRiscoRepository consultaRepository;

    public ConsultaRiscoResponse create(ConsultaRiscoRequest request) {
        AppUser user = userRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));

        RegiaoMonitorada regiao = regiaoRepository
                .findFirstByLatitudeBetweenAndLongitudeBetween(
                        request.latitude() - DELTA_LOCALIZACAO,
                        request.latitude() + DELTA_LOCALIZACAO,
                        request.longitude() - DELTA_LOCALIZACAO,
                        request.longitude() + DELTA_LOCALIZACAO
                )
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma regiao monitorada encontrada para a coordenada"));

        String promptNormalizado = request.prompt().trim().replaceAll("\\s{2,}", " ");
        if (promptNormalizado.isBlank()) {
            throw new BusinessException("Prompt nao pode ser vazio");
        }

        ConsultaRisco consulta = new ConsultaRisco();
        consulta.setId(new ConsultaRiscoId(user.getId(), UUID.randomUUID().toString()));
        consulta.setUsuario(user);
        consulta.setRegiaoMonitorada(regiao);
        consulta.setPrompt(promptNormalizado);
        consulta.setCoordenada(new Coordenada(request.latitude(), request.longitude()));
        consulta.setRuaRegiao(regiao.getBairro().getNome());
        consulta.setNivelRisco(defineRiskLevel(regiao));
        consulta.setRespostaIa(generateMockedResponse(regiao, consulta.getNivelRisco()));

        return toResponse(consultaRepository.save(consulta));
    }

    public ConsultaRiscoResponse findByUserIdAndProtocol(Long usuarioId, String protocolo) {
        ConsultaRiscoId id = new ConsultaRiscoId(usuarioId, protocolo);
        ConsultaRisco consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta nao encontrada"));
        return toResponse(consulta);
    }

    public Page<ConsultaRiscoResponse> findHistoryByUser(Long usuarioId, Pageable pageable) {
        if (!userRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario nao encontrado");
        }
        return consultaRepository.findByIdUsuarioId(usuarioId, pageable)
                .map(this::toResponse);
    }

    public Page<ConsultaRiscoResponse> findHistoryByRegion(Long regiaoId, Pageable pageable) {
        if (!regiaoRepository.existsById(regiaoId)) {
            throw new ResourceNotFoundException("Regiao nao encontrada");
        }
        return consultaRepository.findByRegiaoMonitoradaId(regiaoId, pageable)
                .map(this::toResponse);
    }

    private String defineRiskLevel(RegiaoMonitorada regiao) {
        double pontos = 0.0;
        if (regiao.getAltitudeMedia() != null && regiao.getAltitudeMedia() < 760) {
            pontos += 2.0;
        }
        if (regiao.getDeclividadePercentual() != null && regiao.getDeclividadePercentual() < 5) {
            pontos += 2.0;
        }
        if (regiao.getImpermeabilizacaoPercentual() != null && regiao.getImpermeabilizacaoPercentual() > 50) {
            pontos += 2.0;
        }
        if (regiao.getDistanciaRioMetros() != null && regiao.getDistanciaRioMetros() < 500) {
            pontos += 2.0;
        }
        if (pontos >= 6) {
            return "ALTO";
        }
        if (pontos >= 3) {
            return "MEDIO";
        }
        return "BAIXO";
    }

    private String generateMockedResponse(RegiaoMonitorada regiao, String nivelRisco) {
        return "Resposta mockada da IA: a regiao " + regiao.getNome()
                + " apresenta risco " + nivelRisco
                + " de alagamento conforme dados geograficos cadastrados.";
    }

    private ConsultaRiscoResponse toResponse(ConsultaRisco consulta) {
        return new ConsultaRiscoResponse(
                consulta.getUsuario().getId(),
                consulta.getId().getProtocolo(),
                consulta.getPrompt(),
                consulta.getRegiaoMonitorada().getId(),
                consulta.getRegiaoMonitorada().getNome(),
                consulta.getRuaRegiao(),
                consulta.getCoordenada().getLatitude(),
                consulta.getCoordenada().getLongitude(),
                consulta.getCriadoEm(),
                consulta.getNivelRisco(),
                consulta.getRespostaIa()
        );
    }
}