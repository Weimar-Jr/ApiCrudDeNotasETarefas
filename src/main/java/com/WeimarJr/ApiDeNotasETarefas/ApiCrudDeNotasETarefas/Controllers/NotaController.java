package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Controllers;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services.AtribuicaoEDesatribuicaoTarefaNotaService;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services.NotaService;
import org.springframework.web.bind.annotation.*;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.NotaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.TarefaException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Nota")
public class NotaController {
    private final NotaService notaService;
    private final AtribuicaoEDesatribuicaoTarefaNotaService atribuicaoEDesaTribuicaoTarefaNotaService;
    public NotaController(NotaService notaService, AtribuicaoEDesatribuicaoTarefaNotaService desAtribuicao){
        this.notaService = notaService;
        this.atribuicaoEDesaTribuicaoTarefaNotaService = desAtribuicao;
    }

    @GetMapping
    public List<Nota> listarNotas()
    {
        return notaService.listarNotas();
    }

    @PostMapping
    public  List<Nota> criarNota(@RequestBody Nota nota)
    {
        notaService.criarNota(nota);
        return listarNotas();
    }

    @PutMapping
    public  List<Nota> atualizarNota( @RequestBody Nota nota) throws NotaException {
        notaService.editarNota(nota);
        return listarNotas();
    }

    @DeleteMapping("/{id}")
    public  List<Nota> deletarNota(@PathVariable("id") Long id) throws NotaException {
        notaService.deletarNota(id);
        return listarNotas();
    }

    @GetMapping("/pelo-id/{id}")
    public  Optional<Nota> notaPeloId(@PathVariable("id") Long id) throws NotaException {
        return notaService.mostrarNotaEspecificaPeloId(id);
    }

    @GetMapping("/pela-tag/{tag}")
    public  List<Nota> notasComATag(@PathVariable("tag") String tag) throws NotaException {
        return notaService.exibirNotasPelaTag(tag);
    }

    @DeleteMapping("/deletar-tarefa-da-nota/{idNota}/{idTarefa}")
    public  void deletarTarefaDeNota(@PathVariable("idNota") Long idNota, @PathVariable("idTarefa") Long idTarefa) throws NotaException, TarefaException {
        atribuicaoEDesaTribuicaoTarefaNotaService.deletarTarefaDeNota(idNota, idTarefa);
    }
}
