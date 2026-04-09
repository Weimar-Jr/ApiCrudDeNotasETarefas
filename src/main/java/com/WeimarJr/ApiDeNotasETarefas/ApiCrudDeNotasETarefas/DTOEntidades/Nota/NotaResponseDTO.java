package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.TarefaDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NotaResponseDTO(
        @JsonProperty("id da nota")
        Long id,
        @JsonProperty("titulo da nota")
        String tituloNota,
        @JsonProperty("texto da nota")
        String nota,
        @JsonProperty("tag da nota")
        String tag,
        @JsonProperty("tarefas relacionadas")
        List<TarefaDTO> tarefasRelacionadas
) {
}
