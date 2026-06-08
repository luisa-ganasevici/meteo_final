package br.com.fiap.meteo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "REGIAO_MONITORADA")
public class RegiaoMonitorada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "ALTITUDE_MEDIA")
    private Double altitudeMedia;

    @Column(name = "DECLIVIDADE_PERCENTUAL")
    private Double declividadePercentual;

    @Column(name = "COBERTURA_VEGETAL_PERCENTUAL")
    private Double coberturaVegetalPercentual;

    @Column(name = "IMPERMEABILIZACAO_PERCENTUAL")
    private Double impermeabilizacaoPercentual;

    @Column(name = "DISTANCIA_RIO_METROS")
    private Double distanciaRioMetros;

    @Column(name = "TIPO_SOLO", length = 50)
    private String tipoSolo;

    @Column(name = "NIVEL_URBANIZACAO", length = 20)
    private String nivelUrbanizacao;

    @Column(name = "ATIVA")
    private String ativa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BAIRRO_ID", nullable = false)
    private Bairro bairro;
}
