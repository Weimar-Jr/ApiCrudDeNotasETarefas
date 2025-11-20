package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findAllByTag(String tag);
    List<Tarefa> listarTarefasDaNota(Long idNota);

}
