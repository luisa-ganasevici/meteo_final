package br.com.fiap.meteo.domain.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ConsultaRiscoId implements Serializable {

    @Column(name = "USUARIO_ID")
    private Long usuarioId;

    @Column(name = "PROTOCOLO", length = 36)
    private String protocolo;

    public ConsultaRiscoId() {
    }

    public ConsultaRiscoId(Long usuarioId, String protocolo) {
        this.usuarioId = usuarioId;
        this.protocolo = protocolo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultaRiscoId that)) {
            return false;
        }
        return Objects.equals(usuarioId, that.usuarioId)
                && Objects.equals(protocolo, that.protocolo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, protocolo);
    }
}
