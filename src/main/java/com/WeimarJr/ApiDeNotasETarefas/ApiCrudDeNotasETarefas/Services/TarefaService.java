package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;


import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.TarefaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;


    public TarefaService(TarefaRepository  tarefaRepository)
    {
        this.tarefaRepository = tarefaRepository;
    }


    public Tarefa criarTarefa(Tarefa tarefa) throws TarefaException {
        tarefaRepository.save(tarefa);
        return tarefa;
    }

    public List<Tarefa> listarTarefas() throws TarefaException {
        Sort prioridade = Sort.by(Sort.Direction.ASC, "prioridade");
        List<Tarefa> lista = tarefaRepository.findAll(prioridade);
        if(!lista.isEmpty())
        {
            return lista;
        }
        else{
            throw new TarefaException("Sem tarefas criadas para exibir.");
        }
    }

    public Optional<Tarefa> acharPeloId(Long id) throws TarefaException {
        Optional <Tarefa> tarefa1= tarefaRepository.findById(id);
        if(!tarefa1.isEmpty())
        {
            return tarefa1;
        }
        else {
            throw new TarefaException("tarefa não existe.");
        }
    }

    public Tarefa editarTarefa( Tarefa tarefa) throws TarefaException {


        acharPeloId(tarefa.getId());
        return tarefaRepository.save(tarefa);

    }

    public void deletarTarefa(Long id) throws TarefaException {
        acharPeloId(id);
        tarefaRepository.deleteById(id);

    }

    public List<Tarefa> mostrarTarefasPelaPrioridade( int prioridade) throws TarefaException {
        List<Tarefa> lista = tarefaRepository.findAllByPrioridade(prioridade);
        if(!lista.isEmpty())
        {
            return lista;
        }else{
            throw new TarefaException("Nenhuma tarefa com a prioridade inserida");
        }
    }

    public List<Tarefa> mostrarTarefasConcluidasOuNao(Boolean simOuNao) throws TarefaException {

        List<Tarefa> tarefas = tarefaRepository.findAllByConcluida(simOuNao);
        if(!tarefas.isEmpty())
        {
            return tarefas;
        }

        else
        {
            throw new TarefaException("Nenhuma tarefa encontrada com o status inserido");
        }
    }

}
