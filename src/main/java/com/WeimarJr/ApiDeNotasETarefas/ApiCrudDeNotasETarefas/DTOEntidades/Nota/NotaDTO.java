package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotaDTO(
        @NotNull(message = "O id da nota é obrigatório.")
        @JsonProperty("id_da_nota")
            Long id,
        @NotBlank(message = "O título da nota é obrigatório.")
        @JsonProperty("titulo_da_nota")
            String tituloNota,
        @NotBlank(message = "O texto da nota é obrigatório.")
        @JsonProperty("texto_da_nota")
            String nota,
        @NotBlank(message = "A tag da nota é obrigatória.")
        @JsonProperty("tag_da_nota")
            String tag
) {
}
