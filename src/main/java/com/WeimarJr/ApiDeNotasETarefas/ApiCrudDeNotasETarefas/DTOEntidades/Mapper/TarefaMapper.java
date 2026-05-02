package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Mapper;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.AtualizarTarefaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.CriarTarefaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.TarefaResponseDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {NotaMapper.class})
public interface TarefaMapper {
    TarefaResponseDTO toTarefaResponseDTO(Tarefa tarefa);

    Tarefa toTarefa(CriarTarefaRequestDTO criarTarefaRequestDTO);
    Tarefa toTarefa(AtualizarTarefaRequestDTO atualizarTarefaRequestDTO);

}
