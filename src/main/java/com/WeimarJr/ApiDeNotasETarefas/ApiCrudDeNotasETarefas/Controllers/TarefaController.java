package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Controllers;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.AtualizarTarefaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.CriarTarefaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.TarefaResponseDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.TarefaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services.AtribuicaoEDesatribuicaoTarefaNotaService;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services.TarefaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaService tarefaService;
    private final AtribuicaoEDesatribuicaoTarefaNotaService atribuicaoEDesaTribuicaoTarefaNotaService;
    public TarefaController( TarefaService tarefaService, AtribuicaoEDesatribuicaoTarefaNotaService atribuicao)
    {
        this.tarefaService = tarefaService;
        this.atribuicaoEDesaTribuicaoTarefaNotaService = atribuicao;
    }

    @GetMapping
    List<TarefaResponseDTO> listarTarefas() throws TarefaException {
        return tarefaService.listarTarefas();
    }

    @GetMapping("/prioridade/{prioridade}")
    List<TarefaResponseDTO> mostrarTarefasDePrioridadeEspecifica(@PathVariable("prioridade") int id) throws TarefaException {
        return tarefaService.mostrarTarefasPelaPrioridade(id);
    }

    @GetMapping("/concluidas/{sim-ou-nao}")
    List<TarefaResponseDTO> mostrarTarefasConcluidasOuNao(@PathVariable("sim-ou-nao") boolean simOuNao) throws TarefaException {
        return tarefaService.mostrarTarefasConcluidasOuNao(simOuNao);
    }

    @PostMapping
    TarefaResponseDTO criarTarefa(@RequestBody CriarTarefaRequestDTO tarefa) throws TarefaException {
       return tarefaService.criarTarefa(tarefa);

    }

    @PutMapping
    TarefaResponseDTO atualizarTarefa(@RequestBody AtualizarTarefaRequestDTO tarefa) throws TarefaException {
        return tarefaService.editarTarefa(tarefa);
    }

    @DeleteMapping("/{id}")
    void apagarTarefa(@PathVariable("id") Long id) throws TarefaException {
        tarefaService.deletarTarefa(id);
    }
    @PutMapping("/atribuir-tarefa-a-nota/{idTarefa}/{idNota}")
    void atribuirTarefaANota(@PathVariable("idTarefa") Long idTarefa, @PathVariable("IdNota") Long idNota) throws TarefaException {
        atribuicaoEDesaTribuicaoTarefaNotaService.atribuirTarefaANota(idTarefa, idNota);
    }
}
