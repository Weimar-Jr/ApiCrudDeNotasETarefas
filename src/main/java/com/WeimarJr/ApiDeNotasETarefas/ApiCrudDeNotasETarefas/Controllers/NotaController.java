package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Controllers;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services.NotaService;
import org.springframework.web.bind.annotation.*;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Controllers.ExceptionsNota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Controllers.ExceptionsTarefa;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Nota")
public class NotaController {
    private final NotaService notaService;
    public NotaController(NotaService notaService){
        this.notaService = notaService;
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
    public  List<Nota> atualizarNota( @RequestBody Nota nota) throws ExceptionsNota {
        notaService.editarNota(nota);
        return listarNotas();
    }

    @DeleteMapping("/{id}")
    public  List<Nota> deletarNota(@PathVariable("id") Long id) throws ExceptionsNota {
        notaService.deletarNota(id);
        return listarNotas();
    }

    @GetMapping("/pelo-id/{id}")
    public  Optional<Nota> notaPeloId(@PathVariable("id") Long id) throws ExceptionsNota {
        return notaService.mostrarNotaEspecificaPeloId(id);
    }

    @GetMapping("/pela-tag/{tag}")
    public  List<Nota> notasComATag(@PathVariable("tag") String tag) throws ExceptionsNota {
        return notaService.exibirNotasPelaTag(tag);
    }

    @DeleteMapping("/deletar-tarefa-da-nota/{idNota}/{idTarefa}")
    public  List<Nota> deletarTarefaDeNota(@PathVariable("idNota") Long idNota, @PathVariable("idTarefa") Long idTarefa) throws ExceptionsNota, ExceptionsTarefa {
         return notaService.deletarTarefaDeNota(idNota, idTarefa);
    }
}
