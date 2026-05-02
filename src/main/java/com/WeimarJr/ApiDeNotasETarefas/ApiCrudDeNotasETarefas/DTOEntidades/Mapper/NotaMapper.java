package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Mapper;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.AtualizarNotaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.CriarNotaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.NotaResponseDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TarefaMapper.class})
public interface NotaMapper {
    NotaResponseDTO toNotaResponseDTO(Nota nota);

    Nota toNota(CriarNotaRequestDTO criarNotaRequestDTO);
    Nota toNota(AtualizarNotaRequestDTO atualizarNotaRequestDTO);
}
