package br.com.fiap.meteo.api.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RegiaoMonitoradaRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitude,
        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitude,
        Double altitudeMedia,
        Double declividadePercentual,
        Double coberturaVegetalPercentual,
        Double impermeabilizacaoPercentual,
        Double distanciaRioMetros,
        @Size(max = 50) String tipoSolo,
        @Size(max = 20) String nivelUrbanizacao,
        @Size(max = 1) String ativa,
        @NotNull @Positive Long bairroId
) {
}
