package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.TarefaDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NotaResponseDTO(
        @JsonProperty("id_da_nota")
        Long id,
        @JsonProperty("titulo_da_nota")
        String tituloNota,
        @JsonProperty("texto_da_nota")
        String nota,
        @JsonProperty("tag_da_nota")
        String tag,
        @JsonProperty("tarefas_relacionadas")
        List<TarefaDTO> tarefasRelacionadas
) {
}
