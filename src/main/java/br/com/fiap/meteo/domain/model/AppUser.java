package br.com.fiap.meteo.domain.model;

import java.time.LocalDateTime;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USUARIO")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "SENHA", nullable = false, length = 100)
    private String senha;

    public enum TipoUsuario {
        CLIENT, ADMIN
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_USUARIO", nullable = false, length = 30)
    private TipoUsuario tipoUsuario;


    @Column(name = "ATIVO")
    private String ativo;

    @Column(name = "CRIADO_EM")
    private LocalDateTime criadoEm;

    @PrePersist
    void prePersist() {
        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }
        if (ativo == null) {
            ativo = "S";
        }
        if (tipoUsuario == null) {
            tipoUsuario.CLIENT;
        }
    }
}
