package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TarefaResponseDTO(
        @JsonProperty("id_da_tarefa")
        Long id,
        @JsonProperty("titulo_da_tarefa")
        String tituloTarefa,
        @JsonProperty("descrição_da_tarefa")
        String descricaoTarefa,
        @JsonProperty("prioridade_da_tarefa")
        Integer prioridade,
        @JsonProperty("conclusão_da_tarefa")
        Boolean concluida
) {
}
