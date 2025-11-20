package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.ExceptionsNota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.ExceptionsTarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtribuicaoEDesaTribuicaoTarefaNotaService {
    private final TarefaRepository tarefaRepository;
    private final NotaRepository notaRepository;

    public AtribuicaoEDesaTribuicaoTarefaNotaService( TarefaRepository tarefaRepository1, NotaRepository notaRepository1)
    {
        this.notaRepository = notaRepository1;
        this.tarefaRepository = tarefaRepository1;
    }

    public List<Tarefa> atribuirTarefaANota(Long idTarefa, Long idNota) throws ExceptionsTarefa {
        Optional<Tarefa> tarefa = tarefaRepository.findById(idTarefa);
        if(tarefa.isPresent())
        {
            Optional<Nota> nota = notaRepository.findById(idNota);
            if(nota.isPresent())
            {
                nota.get().setTarefa(tarefa.get());
                tarefa.get().setNota(nota.get());
                System.out.println("Tarefa atribuida a nota");
                return notaRepository.listarTarefasDaNota(idNota);
            }else{
                throw new ExceptionsTarefa("não foi achada a nota pelo id");
            }
        }else{
            throw new ExceptionsTarefa("não foi achada a tarefa pelo id");
        }
    }

    public List<Tarefa> deletarTarefaDeNota(Long idNota, Long idTarefa) throws ExceptionsNota, ExceptionsTarefa {
        Optional<Nota> nota = notaRepository.findById(idNota);
        Optional<Tarefa> tarefa = tarefaRepository.findById(idTarefa);
        if(nota.isPresent())
        {
            if(tarefa.isPresent())
            {
                nota.get().deletarTarefa(tarefa.get());
                return notaRepository.listarTarefasDaNota(idNota);
            }else{
                throw new ExceptionsTarefa("não existe tarefa com esse id");
            }
        }else{
            throw new ExceptionsNota("não existe nota com esse id");
        }
    }
}
