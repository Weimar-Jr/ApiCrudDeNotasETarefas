package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByPrioridade(int prioridade);
    List<Tarefa> findByConcluido(Boolean concluido);
}
