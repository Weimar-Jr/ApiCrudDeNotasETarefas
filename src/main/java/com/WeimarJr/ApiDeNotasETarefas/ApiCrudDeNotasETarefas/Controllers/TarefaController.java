package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Controllers;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.ExceptionsTarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services.TarefaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    final TarefaService tarefaService;
    public TarefaController( TarefaService tarefaService)
    {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    List<Tarefa> listarTarefas() throws ExceptionsTarefa {
        return tarefaService.listarTarefas();
    }

    @GetMapping("/prioridade/{prioridade}")
    List<Tarefa> mostrarTarefasDePrioridadeEspecifica(@PathVariable("prioridade") int id) throws ExceptionsTarefa {
        return tarefaService.mostrarTarefasPelaPrioridade(id);
    }

    @GetMapping("/concluidas/{sim-ou-nao}")
    List<Tarefa> mostrarTarefasConcluidasOuNao(@PathVariable("sim-ou-nao") String simOuNao) throws ExceptionsTarefa {
        return tarefaService.mostrarTarefasConcluidasOuNao(simOuNao);
    }

    @PostMapping
    List<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) throws ExceptionsTarefa {
        tarefaService.criarTarefa(tarefa);
        return listarTarefas();
    }

    @PutMapping
    List<Tarefa> atualizarTarefa(@RequestBody Tarefa tarefa) throws ExceptionsTarefa {
        tarefaService.editarTarefa(tarefa);
        return listarTarefas();
    }

    @DeleteMapping("/{id}")
    List<Tarefa> apagarTarefa(@PathVariable("id") Long id) throws ExceptionsTarefa {
        tarefaService.deletarTarefa(id);
        return listarTarefas();
    }
    @PutMapping("/atribuir-tarefa-a-nota/{idTarefa}/{idNota}")
    List<Tarefa> atribuirTarefaANota(@PathVariable("idTarefa") Long idTarefa, @PathVariable("IdNota") Long idNota) throws ExceptionsTarefa {
        tarefaService.atribuirTarefaANota(idTarefa, idNota);
        return listarTarefas();
    }
}
