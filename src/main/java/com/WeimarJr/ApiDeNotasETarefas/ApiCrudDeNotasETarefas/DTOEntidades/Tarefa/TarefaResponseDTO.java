package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TarefaResponseDTO(
        @JsonProperty("id da tarefa")
        Long id,
        @JsonProperty("titulo da tarefa")
        String tituloTarefa,
        @JsonProperty("descrição da tarefa")
        String descricaoTarefa,
        @JsonProperty("prioridade da tarefa")
        Integer prioridade,
        @JsonProperty("conclusão da tarefa")
        Boolean concluida
) {
}
