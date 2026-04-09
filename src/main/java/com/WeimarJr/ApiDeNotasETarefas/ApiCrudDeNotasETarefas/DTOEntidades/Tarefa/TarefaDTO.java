package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TarefaDTO() {
    @NotBlank(message = "O id da tarefa é obrigatório.")
    @JsonProperty("id da tarefa")
    private static Long id;
    @NotBlank(message = "O título da tarefa é obrigatório.")
    @JsonProperty("titulo da tarefa")
    private static String tituloTarefa;
    @NotBlank(message = "A descrição da tarefa é obrigatória.")
    @JsonProperty("descrição da tarefa")
    private static String descricaoTarefa;
    @NotBlank(message = "A prioridade da tarefa é obrigatória.")
    @Min(value = 1, message = "A prioridade da tarefa deve ser um número inteiro positivo.")
    @Max(value = 5, message = "A prioridade da tarefa deve ser um número inteiro entre 1 e 5.")
    @JsonProperty("prioridade da tarefa")
    private static int prioridade;
    @NotBlank(message = "A prioridade da tarefa é obrigatória.")
    @JsonProperty("conclusão da tarefa")
    private static boolean concluida;


}
