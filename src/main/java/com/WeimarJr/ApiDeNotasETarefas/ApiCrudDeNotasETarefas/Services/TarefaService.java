package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;


import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository  tarefaRepository)
    {
        this.tarefaRepository = tarefaRepository;
    }


    public List<Tarefa> criarTarefa(Tarefa tarefa)
    {
        tarefaRepository.save(tarefa);
        return listarTarefas();
    }

    public List<Tarefa> listarTarefas()
    {
        Sort prioridade = Sort.by(Sort.Direction.ASC, "prioridadeTarefa");
        return tarefaRepository.findAll(prioridade);
    }

    public List<Tarefa> editarTarefa( Tarefa tarefa)
    {
        tarefaRepository.save(tarefa);
        return listarTarefas();
    }

    public List<Tarefa> deletarTarefa(Long id)
    {
        tarefaRepository.deleteById(id);
        return listarTarefas();
    }

    public List<Tarefa> mostrarTarefasPelaPrioridade( int prioridade)
    {
        return tarefaRepository.findByPrioridade(prioridade);
    }

    public List<Tarefa> mostrarTarefasConcluidasOuNao(Boolean concluida)
    {
        return tarefaRepository.findByConcluido(concluida);
    }

}
