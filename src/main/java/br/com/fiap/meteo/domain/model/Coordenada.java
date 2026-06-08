package br.com.fiap.meteo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Coordenada {

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    public Coordenada() {
    }

    public Coordenada(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
