package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Controllers;


import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services.TarefaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    final TarefaService tarefaService;
    TarefaController( TarefaService tarefaService)
    {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    @ResponseBody
    List<Tarefa> listarTarefas() throws ExceptionsTarefa {
        return tarefaService.listarTarefas();
    }

    @GetMapping("/prioridade/{prioridade}")
    @ResponseBody
    List<Tarefa> mostrarTarefasDePrioridadeEspecifica(@PathVariable("prioridade") int id)
    {
        return tarefaService.mostrarTarefasPelaPrioridade(id);
    }

    @GetMapping("/concluidas/{sim/nao}")
    @ResponseBody
    List<Tarefa> mostrarTarefasConcluidasOuNao(@PathVariable("sim/nao") String simOuNao) throws ExceptionsTarefa {
        return tarefaService.mostrarTarefasConcluidasOuNao(simOuNao);
    }

    @PostMapping
    List<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) throws ExceptionsTarefa {
        tarefaService.criarTarefa(tarefa);
        return listarTarefas();
    }

    @PutMapping("/atualizar")
    List<Tarefa> atualizarTarefa(@RequestBody Tarefa tarefa) throws ExceptionsTarefa {
        tarefaService.editarTarefa(tarefa);
        return listarTarefas();
    }

    @DeleteMapping("/{id}")
    List<Tarefa> apagarTarefa(@PathVariable("id") Long id) throws ExceptionsTarefa {
        tarefaService.deletarTarefa(id);
        return listarTarefas();
    }
}
