package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.TarefaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {
    @InjectMocks
    private TarefaService tarefaService;
    @Mock
    private TarefaRepository tarefaRepository;


    Tarefa tarefa1;
    Tarefa tarefa2;

    @BeforeEach
    void tarefaParaTestes()
    {
            tarefa1 = new Tarefa();
            tarefa2 = new Tarefa();
            tarefa1.setId(123L);
            tarefa1.setNomeTarefa("primeiro teste");
            tarefa1.setDescricaoTarefa("descrição");
            tarefa1.setPrioridade(5);
            tarefa1.setConcluida(false);

            tarefa2.setId(456L);
            tarefa2.setNomeTarefa("segundo teste");
            tarefa2.setDescricaoTarefa("");
            tarefa2.setPrioridade(1);
            tarefa2.setConcluida(true);


    }

    @Test
    void deveSalvarTarefaTest()
    {
        when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Tarefa tarefaRetornada1 =  tarefaService.criarTarefa(tarefa1);
        ArgumentCaptor<Tarefa> captor = ArgumentCaptor.forClass(Tarefa.class);
        verify(tarefaRepository, times(1)).save(captor.capture());
        Tarefa salvo = captor.getValue();
        assertEquals(tarefa1.getId(), salvo.getId());
        assertEquals(tarefa1.getNomeTarefa(), salvo.getNomeTarefa());
        assertEquals(tarefa1.getDescricaoTarefa(), salvo.getDescricaoTarefa());
        assertEquals(tarefa1.getPrioridade(), salvo.getPrioridade());
        assertEquals(tarefa1.getConcluida(), salvo.getConcluida());
        assertEquals(tarefa1.getId(), tarefaRetornada1.getId());
        assertEquals(tarefa1.getNomeTarefa(), tarefaRetornada1.getNomeTarefa());
        assertEquals(tarefa1.getDescricaoTarefa(), tarefaRetornada1.getDescricaoTarefa());
        assertEquals(tarefa1.getPrioridade(), tarefaRetornada1.getPrioridade());
        assertEquals(tarefa1.getConcluida(), tarefaRetornada1.getConcluida());
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveListarTarefaExcecaoTest()
    {
        when(tarefaRepository.findAll(any(Sort.class))).thenReturn(Collections.emptyList());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.listarTarefas());
        assertEquals("Sem tarefas criadas para exibir.", ex.getMessage());
        verify(tarefaRepository, times(1)).findAll(any(Sort.class));
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveListaTarefasTest()
    {
        List<Tarefa> listaEsperada = List.of(tarefa1,tarefa2);
        when(tarefaRepository.findAll(any(Sort.class))).thenReturn(listaEsperada);
        List<Tarefa> listaRetornada = tarefaService.listarTarefas();
        assertEquals(listaEsperada, listaRetornada);
        verify(tarefaRepository, times(1)).findAll(any(Sort.class));
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveAcharTarefaPeloIdExecaoTest()
    {
        when(tarefaRepository.findById(20L)).thenReturn(Optional.empty());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.acharPeloId(20L));
        assertEquals("tarefa não existe.", ex.getMessage());
        verify(tarefaRepository, times(1)).findById(20L);
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveAcharTarefaPeloIdTest()
    {
        Tarefa tarefaEsperada = tarefa1;
        when(tarefaRepository.findById(tarefa1.getId())).thenReturn(Optional.of(tarefa1));
        Optional<Tarefa> tarefaRetornada = tarefaService.acharPeloId(tarefa1.getId());
        assertEquals(tarefaEsperada, tarefaRetornada.get());
        verify(tarefaRepository, times(1)).findById(tarefa1.getId());
        verifyNoMoreInteractions(tarefaRepository);

    }

    @Test
    void deveEditarTarefaExecaoTest()
    {
        Tarefa tarefaEditada = new Tarefa();
        tarefaEditada.setId(55L);
        when(tarefaRepository.findById(tarefaEditada.getId())).thenReturn(Optional.empty());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.editarTarefa(tarefaEditada));
        assertEquals("tarefa não existe.", ex.getMessage());
        verify(tarefaRepository, times(1)).findById(tarefaEditada.getId());
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveEditartarefaTest()
    {
        Long id = 22L;
        Tarefa tarefaAntiga = new Tarefa();
        tarefaAntiga.setId(id);
        tarefaAntiga.setNomeTarefa("teste");
        tarefaAntiga.setDescricaoTarefa("descrição");
        tarefaAntiga.setPrioridade(4);
        tarefaAntiga.setConcluida(false);

        Tarefa tarefaEditada = new Tarefa();
        tarefaEditada.setId(id);
        tarefaEditada.setNomeTarefa("teste editado");
        tarefaEditada.setDescricaoTarefa("descrição editada");
        tarefaEditada.setPrioridade(2);
        tarefaEditada.setConcluida(true);

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaAntiga));
        when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Tarefa resultado = tarefaService.editarTarefa(tarefaEditada);
        ArgumentCaptor<Tarefa> captor = ArgumentCaptor.forClass(Tarefa.class);
        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository, times(1)).save(captor.capture());
        Tarefa salvo = captor.getValue();
        assertEquals(id, salvo.getId());
        assertEquals("teste editado", salvo.getNomeTarefa());
        assertEquals("descrição editada", salvo.getDescricaoTarefa());
        assertEquals(2, salvo.getPrioridade());
        assertEquals(true, salvo.getConcluida());
        assertEquals("teste editado", resultado.getNomeTarefa());
        assertEquals("descrição editada", resultado.getDescricaoTarefa());
        assertEquals(2, resultado.getPrioridade());
        assertEquals(true, resultado.getConcluida());
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveDeletarTarefaExecaoTest()
    {
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.deletarTarefa(99L));
        assertEquals("tarefa não existe.", ex.getMessage());
        verify(tarefaRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveDeletarTarefaTest()
    {
        when(tarefaRepository.findById(tarefa1.getId())).thenReturn(Optional.of(tarefa1));
        tarefaService.deletarTarefa(tarefa1.getId());
        verify(tarefaRepository, times(1)).findById(tarefa1.getId());
        verify(tarefaRepository, times(1)).deleteById(tarefa1.getId());
        verifyNoMoreInteractions(tarefaRepository);

    }

    @Test
    void deveMostrarTarefasPelaPrioridadeExecaoTest()
    {
        when(tarefaRepository.findAllByPrioridade(10)).thenReturn(Collections.emptyList());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.mostrarTarefasPelaPrioridade(10));
        assertEquals("Nenhuma tarefa com a prioridade inserida", ex.getMessage());
        verify(tarefaRepository, times(1)).findAllByPrioridade(10);
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveMostrarTarefasPelaPrioridadeTest()
    {
        tarefa1.setPrioridade(3);
        tarefa2.setPrioridade(5);
        List<Tarefa> listaEsperada = List.of(tarefa1);
        when(tarefaRepository.findAllByPrioridade(3)).thenReturn(listaEsperada);
        List<Tarefa> listaRetornada = tarefaService.mostrarTarefasPelaPrioridade(3);
        assertEquals(listaEsperada.size(), listaRetornada.size());
        assertEquals(listaEsperada.get(0), listaRetornada.get(0));
        verify(tarefaRepository, times(1)).findAllByPrioridade(3);
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveMostrarTarefasConcluidasOuNaoConcluidasExcecaoTest()
    {
        when(tarefaRepository.findAllByConcluida(true)).thenReturn(Collections.emptyList());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.mostrarTarefasConcluidasOuNao(true));
        assertEquals("Nenhuma tarefa encontrada com o status inserido", ex.getMessage());
        verify(tarefaRepository, times(1)).findAllByConcluida(true);
        verifyNoMoreInteractions(tarefaRepository);

    }

    @Test void deveMostrarTarefasNaoConcluidas()
    {
        tarefa1.setConcluida(false);
        List<Tarefa> listaEsperada = List.of(tarefa1);
        when(tarefaRepository.findAllByConcluida(false)).thenReturn(listaEsperada);
        List<Tarefa> resultado = tarefaService.mostrarTarefasConcluidasOuNao(false);
        assertEquals(listaEsperada, resultado); verify(tarefaRepository, times(1)).findAllByConcluida(false);
        verifyNoMoreInteractions(tarefaRepository);
    }
    @Test void deveMostrarTarefasConcluidas()
    {
        tarefa2.setConcluida(true);
        List<Tarefa> listaEsperada = List.of(tarefa2);
        when(tarefaRepository.findAllByConcluida(true)).thenReturn(listaEsperada);
        List<Tarefa> resultado = tarefaService.mostrarTarefasConcluidasOuNao(true);
        assertEquals(listaEsperada, resultado);
        verify(tarefaRepository, times(1)).findAllByConcluida(true);
        verifyNoMoreInteractions(tarefaRepository);
    }

}
