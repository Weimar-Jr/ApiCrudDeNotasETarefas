package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.NotaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AtualizarTarefaRequestDTO(
        @NotBlank(message = "O título da tarefa é obrigatório")
        String tituloTarefa,
        @NotBlank(message = "A descrição da tarefa é obrigatória")
        String descricaoTarefa,
        @NotBlank(message = "A prioridade da tarefa é obrigatória")
        @Min(value = 1, message = "A prioridade da tarefa deve ser um número inteiro positivo")
        @Max(value = 5, message = "A prioridade da tarefa deve ser um número inteiro entre 1 e 5")
        Integer prioridade,
        @NotBlank(message = "O status de conclusão da tarefa é obrigatório")
        Boolean concluida,
        @Valid
        NotaDTO nota) {
}
