package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Controllers;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.AtualizarNotaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.CriarNotaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.NotaResponseDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services.AtribuicaoEDesatribuicaoTarefaNotaService;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services.NotaService;
import org.springframework.web.bind.annotation.*;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.NotaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.TarefaException;
import java.util.List;

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
    public List<NotaResponseDTO> listarNotas()
    {
        return notaService.listarNotas();
    }

    @PostMapping
    public  NotaResponseDTO criarNota(@RequestBody CriarNotaRequestDTO nota)
    {
        return notaService.criarNota(nota);

    }

    @PutMapping
    public  NotaResponseDTO atualizarNota( @RequestBody AtualizarNotaRequestDTO nota) throws NotaException {
        return notaService.editarNota(nota);
    }

    @DeleteMapping("/{id}")
    public  void deletarNota(@PathVariable("id") Long id) throws NotaException {
        notaService.deletarNota(id);
    }

    @GetMapping("/pelo-id/{id}")
    public  NotaResponseDTO  notaPeloId(@PathVariable("id") Long id) throws NotaException {
        return notaService.mostrarNotaEspecificaPeloId(id);
    }

    @GetMapping("/pela-tag/{tag}")
    public  List<NotaResponseDTO> notasComATag(@PathVariable("tag") String tag) throws NotaException {
        return notaService.exibirNotasPelaTag(tag);
    }

    @DeleteMapping("/deletar-tarefa-da-nota/{idNota}/{idTarefa}")
    public  void deletarTarefaDeNota(@PathVariable("idNota") Long idNota, @PathVariable("idTarefa") Long idTarefa) throws NotaException, TarefaException {
        atribuicaoEDesaTribuicaoTarefaNotaService.deletarTarefaDeNota(idNota, idTarefa);
    }
}
