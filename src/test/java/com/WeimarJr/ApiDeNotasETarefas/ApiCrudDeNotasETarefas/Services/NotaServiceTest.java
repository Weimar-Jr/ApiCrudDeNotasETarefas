package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.ExceptionsNota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class NotaServiceTest {

    @InjectMocks
    NotaService notaService;

    @InjectMocks
    TarefaService tarefaService;

    @Mock
    NotaRepository notaRepository;
    @Mock
    TarefaRepository tarefaRepository;

    private Nota nota1;
    private Nota nota2;


    @BeforeEach
    void objetosParaTeste()
    {
        nota1 = new Nota();
        nota1.setId(12l);
        nota1.setTituloNota("primeria nota para teste");
        nota1.setNota("essa é a primeira vez que eu faço um teste unitario");
        nota1.setTag("teste");

        nota2 = new Nota();
        nota2.setId(22l);
        nota2.setTituloNota("segunda nota para teste");
        nota2.setNota("espero aprender bem");
        nota2.setTag("teste");
    }

    @Test
    void deveRetornarNenhumaNota() {
        when(notaRepository.findAll()).thenReturn(Collections.emptyList());
        List<Nota> notas = notaService.listarNotas();
        assertTrue(notas.isEmpty(), " a lista deve estar vazia.");
        verify(notaRepository, times(1)).findAll();
        System.out.println("a lista esta vazia");
    }

    @Test
    void deveSalvarAsNotas() {
        when(notaRepository.save(any(Nota.class))).thenReturn(nota1);
        notaService.criarNota(nota1);
        verify(notaRepository, times(1)).save(nota1);
        System.out.println("foi salvo o objeto: " );
    }

    @Test
    void deveListarNotas()
    {
        List<Nota> listaEsperada = new ArrayList<>();
        listaEsperada.add(nota1);
        listaEsperada.add(nota2);
        
        when(notaRepository.findAll()).thenReturn(listaEsperada);

        notaService.criarNota(nota1);
        notaService.criarNota(nota2);

        List<Nota> listaResultado = notaService.listarNotas();

        assertEquals(listaEsperada, listaResultado);
        verify(notaRepository, times(1)).save(nota1);
        verify(notaRepository, times(1)).save(nota2);
        verify(notaRepository, times(3)).findAll();
        System.out.println("lista de objetos retornada com sucesso");

    }


    @Test
    void deveEditarNota() {

        notaService.criarNota(nota2);
        Nota nota2Editada = nota2;
        String tituloEsperado = "nota editada";
        nota2Editada.setTituloNota(tituloEsperado);
        List<Nota> listaComOObjetoEsperado = List.of(nota2Editada);
        when(notaRepository.findById(nota2.getId())).thenReturn(Optional.of(nota2));
        when(notaRepository.findAll()).thenReturn(listaComOObjetoEsperado);

        notaService.editarNota(nota2Editada);



        assertEquals(tituloEsperado, listaComOObjetoEsperado.get(0).getTituloNota());

        verify(notaRepository, times(2)).findAll();
        verify(notaRepository, times(2)).save(nota2Editada);
        verify(notaRepository, times(2)).findById(nota2.getId());

    }

    @Test
    void deveDarErroEmEditarNota()
    {
        Nota notaNova = new Nota();
        notaNova.setId(123l);
        when(notaRepository.findById(notaNova.getId())).thenReturn(Optional.empty());

        ExceptionsNota excecao = assertThrows(ExceptionsNota.class, () -> {
            notaService.editarNota(notaNova);
        });

        assertEquals("não existe nota com esse id.", excecao.getMessage());

        verify(notaRepository, never()).save(any(Nota.class));
        verify(notaRepository, times(1)).findById(notaNova.getId());

    }

    @Test
    void deveApagarANota()
    {
        notaService.criarNota(nota1);
        notaService.criarNota(nota2);
        List<Nota> listaEsperada = new ArrayList<>();
        listaEsperada.add(nota2);

        when(notaRepository.findById(nota1.getId())).thenReturn(Optional.of(nota1));
        when(notaService.deletarNota(nota1.getId())).thenReturn(listaEsperada);

        notaService.deletarNota(nota1.getId());
        List<Nota> listaResultado = notaService.listarNotas();
        assertEquals(listaEsperada, listaResultado);
        assertEquals(nota2.getId(), listaResultado.get(0).getId());

        verify(notaRepository, times(4)).findAll();
        verify(notaRepository, times(2)).deleteById(nota1.getId());

    }

    @Test
    void deveDarExcecaoEmDeletarNota()
    {
        Nota notaNova = new Nota();
        notaNova.setId(123l);

        when(notaRepository.findById(notaNova.getId())).thenReturn(Optional.empty());

        ExceptionsNota execao = assertThrows(ExceptionsNota.class, () -> {
            notaService.deletarNota(notaNova.getId());
        });

        verify(notaRepository, times(1)).findById(notaNova.getId());

    }

    @Test
    void deveAcharNotasPelaTag()
    {
        String tagTeste = "teste";
        Nota novaNota = new Nota();
        novaNota.setTag(" outra tag");
        notaService.criarNota(novaNota);
        notaService.criarNota(nota2);
        notaService.criarNota(nota1);
        List<Nota> listaEsperada = new ArrayList<>();
        listaEsperada.add(nota1);
        listaEsperada.add(nota2);

        when(notaRepository.findAllByTag(tagTeste)).thenReturn(listaEsperada);

        List<Nota> listaResultado = notaService.exibirNotasPelaTag(tagTeste);
        assertEquals(listaEsperada, listaResultado);

        verify(notaRepository, times(3)).save(any(Nota.class));
        verify(notaRepository, times(2)).findAllByTag(tagTeste);

    }

    @Test
    void deveDarExcecaoEmAcharNotasPelaTag()
    {
        String tagTeste = "teste";
        when(notaRepository.findAllByTag(tagTeste)).thenReturn(Collections.emptyList());
        assertThrows(ExceptionsNota.class, () ->{
            notaService.exibirNotasPelaTag(tagTeste);
        });

        verify(notaRepository, times(1)).findAllByTag(tagTeste);
    }

    @Test
    void deveDeletarTarefaDaNota()
    {
        List<Tarefa> listaEsperada = new ArrayList<>();
        Tarefa tarefaTeste = new Tarefa();
        tarefaTeste.setId(123l);
        nota1.setTarefa(tarefaTeste);
        notaService.criarNota(nota1);
        when(notaRepository.findById(nota1.getId())).thenReturn(Optional.of(nota1));

        notaService.deletarTarefaDeNota(nota1.getId(), tarefaTeste.getId());
        List<Tarefa> listaResultado = nota1.getTarefasRelacionadas();
        assertEquals(listaEsperada, listaResultado);

        verify(notaRepository, times(2)).save(nota1);
        verify(tarefaRepository, times(1)).save(tarefaTeste);

    }

}