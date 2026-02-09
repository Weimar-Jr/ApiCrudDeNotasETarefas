package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Controllers;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.TarefaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
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
    List<Tarefa> listarTarefas() throws TarefaException {
        return tarefaService.listarTarefas();
    }

    @GetMapping("/prioridade/{prioridade}")
    List<Tarefa> mostrarTarefasDePrioridadeEspecifica(@PathVariable("prioridade") int id) throws TarefaException {
        return tarefaService.mostrarTarefasPelaPrioridade(id);
    }

    @GetMapping("/concluidas/{sim-ou-nao}")
    List<Tarefa> mostrarTarefasConcluidasOuNao(@PathVariable("sim-ou-nao") boolean simOuNao) throws TarefaException {
        return tarefaService.mostrarTarefasConcluidasOuNao(simOuNao);
    }

    @PostMapping
    List<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) throws TarefaException {
        tarefaService.criarTarefa(tarefa);
        return listarTarefas();
    }

    @PutMapping
    List<Tarefa> atualizarTarefa(@RequestBody Tarefa tarefa) throws TarefaException {
        tarefaService.editarTarefa(tarefa);
        return listarTarefas();
    }

    @DeleteMapping("/{id}")
    List<Tarefa> apagarTarefa(@PathVariable("id") Long id) throws TarefaException {
        tarefaService.deletarTarefa(id);
        return listarTarefas();
    }
    @PutMapping("/atribuir-tarefa-a-nota/{idTarefa}/{idNota}")
    List<Tarefa> atribuirTarefaANota(@PathVariable("idTarefa") Long idTarefa, @PathVariable("IdNota") Long idNota) throws TarefaException {
        atribuicaoEDesaTribuicaoTarefaNotaService.atribuirTarefaANota(idTarefa, idNota);
        return listarTarefas();
    }
}
