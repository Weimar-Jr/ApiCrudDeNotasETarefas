package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.ExceptionsNota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.ExceptionsTarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaService {
    private final NotaRepository notaRepository;
    private final TarefaRepository tarefaRepository;
    public NotaService( NotaRepository notaRepository, TarefaRepository tarefaRepository)
    {
        this.notaRepository = notaRepository;
        this.tarefaRepository = tarefaRepository;
    }

    public List<Nota> criarNota( Nota nota)
    {
        notaRepository.save(nota);
        return  listarNotas();

    }

    public List<Nota> listarNotas()
    {
        return notaRepository.findAll();
    }

    public List<Nota> editarNota(Nota nota ) throws ExceptionsNota {
        Optional<Nota> seNotaExiste= mostrarNotaEspecificaPeloId(nota.getId());
        if(seNotaExiste.isPresent()){
        notaRepository.save(nota);
        }
        return listarNotas();
    }

    public List<Nota> deletarNota(Long id) throws ExceptionsNota {
       if(mostrarNotaEspecificaPeloId(id).isPresent()){
           notaRepository.deleteById(id);
           System.out.println("nota deletada");
           return  listarNotas();
       }
       else {
           throw new ExceptionsNota("não existe nota com esse id para poder ser deletada.");
       }
    }

    public Optional<Nota> mostrarNotaEspecificaPeloId(Long id) throws ExceptionsNota {
        if(notaRepository.findById(id).isPresent()) {
            return notaRepository.findById(id);
        }else{
            throw new ExceptionsNota("não existe nota com esse id.");
        }
    }

    public List<Nota> exibirNotasPelaTag(String tag ) throws ExceptionsNota {
        List<Nota> notas = notaRepository.findAllByTag(tag);
        if(!notas.isEmpty()) {
            return notaRepository.findAllByTag(tag);
        }else{
            throw new ExceptionsNota("não tem notas com essa tag.");
        }
    }

    public List<Nota> deletarTarefaDeNota(Long idNota, Long idTarefa) throws ExceptionsNota, ExceptionsTarefa {
        Optional<Nota> nota = notaRepository.findById(idNota);
        Optional<Tarefa> tarefa = tarefaRepository.findById(idTarefa);
        if(nota.isPresent())
        {
            if(tarefa.isPresent())
            {
                nota.get().deletarTarefa(tarefa.get());
                return listarNotas();
            }else{
                throw new ExceptionsTarefa("não existe tarefa com esse id");
            }
        }else{
            throw new ExceptionsNota("não existe nota com esse id");
        }
    }
}
