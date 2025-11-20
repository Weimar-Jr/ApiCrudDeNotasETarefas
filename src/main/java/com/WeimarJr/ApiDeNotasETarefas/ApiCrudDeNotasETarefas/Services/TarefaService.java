package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;


import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.ExceptionsTarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;


    public TarefaService(TarefaRepository  tarefaRepository, NotaRepository notaRepository)
    {
        this.tarefaRepository = tarefaRepository;
    }


    public List<Tarefa> criarTarefa(Tarefa tarefa) throws ExceptionsTarefa {
        tarefaRepository.save(tarefa);
        return listarTarefas();
    }

    public List<Tarefa> listarTarefas() throws ExceptionsTarefa {
        Sort prioridade = Sort.by(Sort.Direction.ASC, "prioridade");
        List<Tarefa> lista = tarefaRepository.findAll(prioridade);
        if(!lista.isEmpty())
        {
            return lista;
        }
        else{
            throw new ExceptionsTarefa("Sem tarefas criadas para exibir.");
        }
    }

    public Optional<Tarefa> acharPeloId(Long id) throws ExceptionsTarefa {
        Optional <Tarefa> tarefa1= tarefaRepository.findById(id);
        if(tarefa1.isPresent())
        {
            return tarefa1;
        }
        else {
            throw new ExceptionsTarefa("tarefa não existe.");
        }
    }

    public List<Tarefa> editarTarefa( Tarefa tarefa) throws ExceptionsTarefa {


        acharPeloId(tarefa.getId());
        tarefaRepository.save(tarefa);
        return listarTarefas();
    }

    public List<Tarefa> deletarTarefa(Long id) throws ExceptionsTarefa {
        tarefaRepository.deleteById(id);
        return listarTarefas();
    }

    public List<Tarefa> mostrarTarefasPelaPrioridade( int prioridade) throws ExceptionsTarefa {
        List<Tarefa> lista = tarefaRepository.findAllByPrioridade(prioridade);
        if(!lista.isEmpty())
        {
            return lista;
        }else{
            throw new ExceptionsTarefa("Nenhuma tarefa com a prioridade inserida");
        }
    }

    public List<Tarefa> mostrarTarefasConcluidasOuNao(String simOuNao) throws ExceptionsTarefa {
        if(simOuNao.equalsIgnoreCase("sim"))
        {
           return tarefaRepository.findAllByConcluida(true);
        } else if (simOuNao.equalsIgnoreCase("nao")) {

           return tarefaRepository.findAllByConcluida(false);
        }
        else{
            throw new ExceptionsTarefa("Nenhuma nota para o filtro aplicado");
        }
    }

}
