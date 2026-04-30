package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota;

import java.util.List;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.TarefaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarNotaRequestDTO(
        @NotNull(message = "O ID da nota é obrigatório.")
        Long id,
        @NotBlank(message = "O título da nota é obrigatório.")
        String tituloNota,
        @NotBlank(message = "O texto da nota é obrigatório.")
        String nota,
        @NotBlank(message = "A tag da nota é obrigatória.")
        String tag,
        @Valid
        List<TarefaDTO> tarefasRelacionadas
) {
}
