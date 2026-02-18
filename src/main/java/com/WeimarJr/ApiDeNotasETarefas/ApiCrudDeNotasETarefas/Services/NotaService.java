package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.NotaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaService {
    private final NotaRepository notaRepository;
    public NotaService( NotaRepository notaRepository, TarefaRepository tarefaRepository)
    {
        this.notaRepository = notaRepository;
    }

    public Nota criarNota( Nota nota)
    {
        notaRepository.save(nota);
        return  nota;

    }

    public List<Nota> listarNotas()
    {
        List<Nota> notas = notaRepository.findAll();
        if(!notas.isEmpty()) {
            return notas;
        }else {
            throw new NotaException("não tem notas cadastradas no sistema.");
        }
    }

    public Nota editarNota(Nota nota ) throws NotaException {
        Optional<Nota> seNotaExiste= mostrarNotaEspecificaPeloId(nota.getId());
        notaRepository.save(nota);
            return nota;

    }

    public void deletarNota(Long id) throws NotaException {
       if(mostrarNotaEspecificaPeloId(id).isPresent()){
           notaRepository.deleteById(id);
       }

    }

    public Optional<Nota> mostrarNotaEspecificaPeloId(Long id) throws NotaException {
        Optional<Nota> nota = notaRepository.findById(id);
        if(nota.isPresent()) {
            return nota;
        }else{
            throw new NotaException("não existe esta nota no sistema.");
        }
    }

    public List<Nota> exibirNotasPelaTag(String tag ) throws NotaException {
        List<Nota> notas = notaRepository.findAllByTag(tag);
        if(!notas.isEmpty()) {
            return notas;
        }else{
            throw new NotaException("não tem notas com essa tag.");
        }
    }

}
