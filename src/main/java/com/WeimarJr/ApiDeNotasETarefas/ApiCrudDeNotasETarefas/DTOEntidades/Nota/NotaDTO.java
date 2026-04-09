package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record NotaDTO(
        @NotBlank(message = "O id da nota é obrigatório.")
        @JsonProperty("id da nota")
            Long id,
        @NotBlank(message = "O título da nota é obrigatório.")
        @JsonProperty("titulo da nota")
            String tituloNota,
        @NotBlank(message = "O texto da nota é obrigatório.")
        @JsonProperty("texto da nota")
            String nota,
        @NotBlank(message = "A tag da nota é obrigatória.")
        @JsonProperty("tag da nota")
            String tag
) {
}
