package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.NotaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.TarefaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtribuicaoEDesatribuicaoTarefaNotaService {
    private final TarefaRepository tarefaRepository;
    private final NotaRepository notaRepository;

    public AtribuicaoEDesatribuicaoTarefaNotaService(TarefaRepository tarefaRepository1, NotaRepository notaRepository1) {
        this.notaRepository = notaRepository1;
        this.tarefaRepository = tarefaRepository1;
    }

    public List<Tarefa> atribuirTarefaANota(Long idTarefa, Long idNota) throws TarefaException, NotaException {
        Optional<Tarefa> tarefa = tarefaRepository.findById(idTarefa);
        if (!tarefa.isEmpty()) {
            Optional<Nota> nota = notaRepository.findById(idNota);
            if (nota.isPresent()) {
                nota.get().adicionarTarefa(tarefa.get());
                tarefa.get().setNota(nota.get());
                return notaRepository.listarTarefasDaNota(idNota);
            } else {
                throw new NotaException("não foi achada a nota pelo id");
            }
        } else {
            throw new TarefaException("não foi achada a tarefa pelo id");
        }
    }

    public void deletarTarefaDeNota(Long idNota, Long idTarefa) throws NotaException, TarefaException {
        Optional<Nota> nota = notaRepository.findById(idNota);
        Optional<Tarefa> tarefa = tarefaRepository.findById(idTarefa);
        if (nota.isPresent()) {
            if (tarefa.isPresent()) {
                nota.get().removerTarefa(tarefa.get());
            } else {
                throw new TarefaException("não existe tarefa com esse id");
            }
        } else {
            throw new NotaException("não existe nota com esse id");
        }
    }
}
