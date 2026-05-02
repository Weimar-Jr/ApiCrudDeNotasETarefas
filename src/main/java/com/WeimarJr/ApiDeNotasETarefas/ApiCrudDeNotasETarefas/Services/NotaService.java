package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Mapper.NotaMapper;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.AtualizarNotaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.CriarNotaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.NotaResponseDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.NotaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaService {
    private final NotaRepository notaRepository;
    private final NotaMapper notaMapper;
    public NotaService( NotaRepository notaRepository, NotaMapper notaMapper)
    {
        this.notaRepository = notaRepository;
        this.notaMapper = notaMapper;
    }

    @Transactional
    public NotaResponseDTO criarNota( CriarNotaRequestDTO criarNotaDTO)
    {
        Nota nota = notaMapper.toNota(criarNotaDTO);
        notaRepository.save(nota);
        return notaMapper.toNotaResponseDTO(nota);

    }

    public List<NotaResponseDTO> listarNotas()
    {
        List<Nota> notas = notaRepository.findAll();
        if(!notas.isEmpty()) {
            return notas.stream().map(notaMapper::toNotaResponseDTO).toList();
        }else {
            throw new NotaException("não tem notas cadastradas no sistema.");
        }
    }

    @Transactional
    public NotaResponseDTO editarNota(AtualizarNotaRequestDTO nota) throws NotaException {
        Nota notaAtualizada = notaMapper.toNota(nota);
        mostrarNotaEspecificaPeloId(notaAtualizada.getId());
        notaRepository.save(notaAtualizada);
        return notaMapper.toNotaResponseDTO(notaAtualizada);
    }

    public void deletarNota(Long id) throws NotaException {
       if(existeNotaPeloId(id)){
           notaRepository.deleteById(id);
       }

    }

    public Boolean existeNotaPeloId(Long id) {
        if(!notaRepository.existsById(id)) {
            throw new NotaException("não existe esta nota no sistema.");
        }else {
            return true;
        }
    }

    public NotaResponseDTO mostrarNotaEspecificaPeloId(Long id) throws NotaException {
        Optional<Nota> nota = notaRepository.findById(id);
        if(nota.isPresent()) {
            return notaMapper.toNotaResponseDTO(nota.get());
        }else{
            throw new NotaException("não existe esta nota no sistema.");
        }
    }

    public List<NotaResponseDTO> exibirNotasPelaTag(String tag ) throws NotaException {
        List<Nota> notas = notaRepository.findAllByTag(tag);
        if(!notas.isEmpty()) {
            return notas.stream().map(notaMapper::toNotaResponseDTO).toList();
        }else{
            throw new NotaException("não tem notas com essa tag.");
        }
    }

}
