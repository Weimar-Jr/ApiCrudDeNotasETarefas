package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.TarefaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CriarNotaRequestDTO(
        @NotBlank(message = "O título da nota é obrigatório.")
        String tituloNota,
        @NotBlank(message = "O texto da nota é obrigatório.")
        String nota,
        @NotBlank(message = "A tag da nota é obrigatória.")
        String tag,
        @Valid List<TarefaDTO> tarefasRelacionadas) {

}
