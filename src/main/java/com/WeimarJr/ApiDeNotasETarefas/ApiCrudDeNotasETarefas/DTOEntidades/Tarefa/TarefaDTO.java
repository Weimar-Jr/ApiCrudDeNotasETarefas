package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TarefaDTO(@NotNull(message = "O id da tarefa é obrigatório.")
                        @JsonProperty("id_da_tarefa")
                        @Min(value = 1, message = "O id da tarefa deve ser um número inteiro positivo.")
                        Long id,
                        @NotBlank(message = "O título da tarefa é obrigatório.")
                        @JsonProperty("titulo_da_tarefa")
                        String tituloTarefa,
                        @NotBlank(message = "A descrição da tarefa é obrigatória.")
                        @JsonProperty("descrição_da_tarefa")
                        String descricaoTarefa,
                        @NotNull(message = "A prioridade da tarefa é obrigatória.")
                        @Min(value = 1, message = "A prioridade da tarefa deve ser um número inteiro positivo.")
                        @Max(value = 5, message = "A prioridade da tarefa deve ser um número inteiro entre 1 e 5.")
                        @JsonProperty("prioridade_da_tarefa")
                        int prioridade,
                        @JsonProperty("conclusão_da_tarefa")
                        boolean concluida) {



}
