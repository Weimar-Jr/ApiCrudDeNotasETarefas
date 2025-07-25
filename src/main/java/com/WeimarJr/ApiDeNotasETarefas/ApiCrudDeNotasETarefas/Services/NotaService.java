package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaService {
    private final NotaRepository notaRepository;
    public NotaService( NotaRepository notaRepository)
    {
        this.notaRepository = notaRepository;
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

    public List<Nota> editarNota(Nota nota )
    {
        notaRepository.save(nota);
        return listarNotas();
    }

    public List<Nota> deletarNota(Long id)
    {
        notaRepository.deleteById(id);
        return  listarNotas();
    }

    public Optional<Nota> mostrarNotaEspecificaPeloId(Long id)
    {
        return notaRepository.findById(id);
    }

    public List<Nota> exibirNotasPelaTag(List<String> tag)
    {
        return notaRepository.findAllByTag(tag);
    }
}
