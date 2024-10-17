package io.github.publications.publicationsapi.aplication.publications;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PublicationDTO {
    private String id;
    private String orgao;
    private String unidade;
    private String localidade;
    private String tipo;
    private String numeroProcesso;
    private String descricao;
    private String documento;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataPublicacao;
}
