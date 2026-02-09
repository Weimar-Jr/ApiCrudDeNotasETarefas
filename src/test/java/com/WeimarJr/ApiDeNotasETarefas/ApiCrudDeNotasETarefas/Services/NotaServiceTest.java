package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.NotaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @Mock
    NotaRepository notaRepository;

    private Nota nota1;
    private Nota nota2;


    @BeforeEach
    void objetosParaTeste()
    {
        nota1 = new Nota();
        nota1.setId(12L);
        nota1.setTituloNota("primeria nota para teste");
        nota1.setNota("essa é a primeira vez que eu faço um teste unitario");
        nota1.setTag("teste");

        nota2 = new Nota();
        nota2.setId(22L);
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
        verify(notaRepository, times(1)).findAll();

    }


    @Test
    void deveEditarNota() {

        Long id = 22L;
        Nota notaExistente = new Nota();
        notaExistente.setId(id);
        notaExistente.setTituloNota("titulo antigo");
        notaExistente.setNota("conteudo");
        Nota notaEditada = new Nota();
        notaEditada.setId(id);
        notaEditada.setTituloNota("nota editada");
        notaEditada.setNota("conteudo");

        when(notaRepository.findById(id)).thenReturn(Optional.of(notaExistente));
        when(notaRepository.save(any(Nota.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Nota resultado = notaService.editarNota(notaEditada);
        ArgumentCaptor<Nota> captor = ArgumentCaptor.forClass(Nota.class);
        verify(notaRepository, times(1)).findById(id);
        verify(notaRepository, times(1)).save(captor.capture());
        Nota salvo = captor.getValue(); assertEquals(id, salvo.getId()); assertEquals("nota editada", salvo.getTituloNota());
        assertEquals("conteudo", salvo.getNota());
        assertEquals("nota editada", resultado.getTituloNota()); verifyNoMoreInteractions(notaRepository);



    }

    @Test
    void deveDarErroEmEditarNota()
    {
        Nota notaNova = new Nota();
        notaNova.setId(123L);
        when(notaRepository.findById(notaNova.getId())).thenReturn(Optional.empty());

        NotaException excecao = assertThrows(NotaException.class, () -> notaService.editarNota(notaNova));

        assertEquals("não existe nota com esse id.", excecao.getMessage());

        verify(notaRepository, never()).save(any(Nota.class));
        verify(notaRepository, times(1)).findById(notaNova.getId());

    }

    @Test
    void deveApagarANota()
    {
        when(notaRepository.findById(nota1.getId())).thenReturn(Optional.of(nota1));
        notaService.deletarNota(nota1.getId());
        verify(notaRepository, times(1)).deleteById(nota1.getId());

    }

    @Test
    void deveDarExcecaoEmDeletarNota()
    {
        Nota notaNova = new Nota();
        notaNova.setId(123L);

        when(notaRepository.findById(notaNova.getId())).thenReturn(Optional.empty());

        NotaException execao = assertThrows(NotaException.class, () -> notaService.deletarNota(notaNova.getId()));

        assertEquals("não existe nota com esse id.", execao.getMessage());
        verify(notaRepository, times(0)).deleteById(notaNova.getId());
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
        assertThrows(NotaException.class, () -> notaService.exibirNotasPelaTag(tagTeste));

        verify(notaRepository, times(1)).findAllByTag(tagTeste);
    }


}