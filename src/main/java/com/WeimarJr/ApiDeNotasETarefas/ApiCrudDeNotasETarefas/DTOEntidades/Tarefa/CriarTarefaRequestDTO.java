package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarTarefaRequestDTO(
            @NotBlank(message = "O título da tarefa é obrigatório")
            String tituloTarefa,
            @NotBlank(message = "A descrição da tarefa é obrigatória")
            String descricaoTarefa,
            @NotNull(message = "A prioridade da tarefa é obrigatória")
            @Min(value = 1, message = "A prioridade da tarefa deve ser um número inteiro positivo")
            @Max(value = 5, message = "A prioridade da tarefa deve ser um número inteiro entre 1 e 5")
            int prioridade
) {
}
