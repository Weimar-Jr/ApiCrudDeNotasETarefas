package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;


import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Mapper.TarefaMapper;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.AtualizarTarefaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.CriarTarefaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.TarefaResponseDTO;
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
    private final TarefaMapper tarefaMapper;


    public TarefaService(TarefaRepository  tarefaRepository, TarefaMapper tarefaMapper)
    {
        this.tarefaRepository = tarefaRepository;
        this.tarefaMapper = tarefaMapper;
    }


    public TarefaResponseDTO criarTarefa(CriarTarefaRequestDTO tarefa) throws TarefaException {
        Tarefa tarefa1 = tarefaMapper.toTarefa(tarefa);
        tarefaRepository.save(tarefa1);
        return tarefaMapper.toTarefaResponseDTO(tarefa1);
    }

    public List<TarefaResponseDTO> listarTarefas() throws TarefaException {
        Sort prioridade = Sort.by(Sort.Direction.ASC, "prioridade");
        List<Tarefa> lista = tarefaRepository.findAll(prioridade);
        if(!lista.isEmpty())
        {
            return lista.stream().map(tarefaMapper::toTarefaResponseDTO).toList();
        }
        else{
            throw new TarefaException("Sem tarefas criadas para exibir.");
        }
    }

    public Boolean existeTarefaPeloId(Long id) throws TarefaException {
        Optional<Tarefa> tarefa1 = tarefaRepository.findById(id);
        if(!tarefa1.isEmpty())
        {
            return true;
        }
        else {
            throw new TarefaException("tarefa não existe.");
        }
    }

    public TarefaResponseDTO acharPeloId(Long id) throws TarefaException {
        Optional <Tarefa> tarefa1= tarefaRepository.findById(id);
        if(!tarefa1.isEmpty())
        {
            return tarefaMapper.toTarefaResponseDTO(tarefa1.get());
        }
        else {
            throw new TarefaException("tarefa não existe.");
        }
    }

    public TarefaResponseDTO editarTarefa(AtualizarTarefaRequestDTO tarefa) throws TarefaException {
        Tarefa tarefaAtualizada = tarefaMapper.toTarefa(tarefa);
        existeTarefaPeloId(tarefaAtualizada.getId());
        tarefaRepository.save(tarefaAtualizada);
        return tarefaMapper.toTarefaResponseDTO(tarefaAtualizada);

    }

    public void deletarTarefa(Long id) throws TarefaException {
        acharPeloId(id);
        tarefaRepository.deleteById(id);

    }

    public List<TarefaResponseDTO > mostrarTarefasPelaPrioridade( int prioridade) throws TarefaException {
        List<Tarefa> lista = tarefaRepository.findAllByPrioridade(prioridade);
        if(!lista.isEmpty())
        {
            return lista.stream().map(tarefaMapper::toTarefaResponseDTO).toList();
        }else{
            throw new TarefaException("Nenhuma tarefa com a prioridade inserida");
        }
    }

    public List<TarefaResponseDTO > mostrarTarefasConcluidasOuNao(Boolean simOuNao) throws TarefaException {

        List<Tarefa> tarefas = tarefaRepository.findAllByConcluida(simOuNao);
        if(!tarefas.isEmpty())
        {
            return tarefas.stream().map(tarefaMapper::toTarefaResponseDTO).toList();
        }

        else
        {
            throw new TarefaException("Nenhuma tarefa encontrada com o status inserido");
        }
    }

}
