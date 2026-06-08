package br.com.fiap.meteo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CONSULTA_RISCO")
public class ConsultaRisco extends BaseEntity {

    @EmbeddedId
    private ConsultaRiscoId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("usuarioId")
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private AppUser usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REGIAO_MONITORADA_ID", nullable = false)
    private RegiaoMonitorada regiaoMonitorada;

    @Column(name = "PROMPT", nullable = false, length = 500)
    private String prompt;

    @Embedded
    private Coordenada coordenada;

    @Column(name = "RUA_REGIAO", length = 120)
    private String ruaRegiao;

    @Column(name = "NIVEL_RISCO", length = 20)
    private String nivelRisco;

    @Column(name = "RESPOSTA_IA", length = 500)
    private String respostaIa;
}
