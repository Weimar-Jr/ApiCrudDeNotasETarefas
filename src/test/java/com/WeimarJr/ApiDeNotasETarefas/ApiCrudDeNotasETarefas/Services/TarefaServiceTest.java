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


    Tarefa tarefa1 = new Tarefa();
    Tarefa tarefa2 = new Tarefa();

    @BeforeEach
    void tarefaParaTestes()
    {

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
        when(tarefaRepository.save(tarefa2)).thenAnswer(invocation -> invocation.getArgument(0));
        Tarefa tarefaRetornada1 =  tarefaService.criarTarefa(tarefa1);
        Tarefa tarefaRetornada2 =  tarefaService.criarTarefa(tarefa2);
        assertEquals(tarefa1, tarefaRetornada1);
        assertEquals(tarefa2, tarefaRetornada2);

        verify(tarefaRepository, times(2)).save(any(Tarefa.class));
    }

    @Test
    void deveListarTarefaExcecaoTest()
    {
        when(tarefaRepository.findAll(any(Sort.class))).thenReturn(Collections.emptyList());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.listarTarefas());
        assertEquals("Sem tarefas criadas para exibir.", ex.getMessage());
        verify(tarefaRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void deveListaTarefasTest()
    {
        List<Tarefa> listaEsperada = List.of(tarefa1,tarefa2);
        when(tarefaRepository.findAll(any(Sort.class))).thenReturn(listaEsperada);

        List<Tarefa> listaRetornada = tarefaService.listarTarefas();

        assertEquals(listaEsperada, listaRetornada);
        verify(tarefaRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void deveAcharTarefaPeloIdExecaoTest()
    {
        when(tarefaRepository.findById(20L)).thenReturn(Optional.empty());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.acharPeloId(20L));
        assertEquals("tarefa não existe.", ex.getMessage());
        verify(tarefaRepository, times(1)).findById(20L);
    }

    @Test
    void deveAcharTarefaPeloIdTest()
    {
        Tarefa tarefaEsperada = tarefa1;
        when(tarefaRepository.findById(tarefa1.getId())).thenReturn(Optional.of(tarefa1));
        Optional<Tarefa> tarefaRetornada = tarefaService.acharPeloId(tarefa1.getId());
        assertEquals(tarefaEsperada, tarefaRetornada.get());
        verify(tarefaRepository, times(1)).findById(tarefa1.getId());

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
    }

    @Test
    void deveEditartarefaTest()
    {
        Tarefa tarefaAntiga = new Tarefa();
        tarefaAntiga.setId(22L);
        tarefaAntiga.setNomeTarefa("teste");
        String nomeTarefaAntiga = tarefaAntiga.getNomeTarefa();
        Tarefa tarefaEditada = tarefaAntiga;
        tarefaEditada.setNomeTarefa("nome diferente");
        String nomeTarefaAtual = tarefaEditada.getNomeTarefa();
        when(tarefaRepository.findById(tarefaAntiga.getId())).thenReturn(Optional.of(tarefaAntiga));
        when(tarefaRepository.save(tarefaEditada)).thenReturn(tarefaEditada);

        Tarefa tarefaRetornada = tarefaService.editarTarefa(tarefaEditada);
        assertNotEquals(nomeTarefaAntiga, nomeTarefaAtual);
        verify(tarefaRepository,times(1)).findById(tarefaAntiga.getId());
        verify(tarefaRepository,times(1)).save(tarefaEditada);

    }

    @Test
    void deveDeletarTarefaExecaoTest()
    {
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.deletarTarefa(99L));
        assertEquals("tarefa não existe.", ex.getMessage());
        verify(tarefaRepository, times(1)).findById(99L);
    }

    @Test
    void deveDeletarTarefaTest()
    {
        when(tarefaRepository.findById(tarefa1.getId())).thenReturn(Optional.of(tarefa1));
        tarefaService.deletarTarefa(tarefa1.getId());
        verify(tarefaRepository, times(1)).findById(tarefa1.getId());
        verify(tarefaRepository, times(1)).deleteById(tarefa1.getId());

    }

    @Test
    void deveMostrarTarefasPelaPrioridadeExecaoTest()
    {
        when(tarefaRepository.findAllByPrioridade(10)).thenReturn(Collections.emptyList());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.mostrarTarefasPelaPrioridade(10));
        assertEquals("Nenhuma tarefa com a prioridade inserida", ex.getMessage());
        verify(tarefaRepository, times(1)).findAllByPrioridade(10);
    }

    @Test
    void deveMostrarTarefasPelaPrioridadeTest()
    {
        tarefa1.setPrioridade(3);
        tarefa2.setPrioridade(5);
        List<Tarefa> listaEsperada = List.of(tarefa1);
        when(tarefaRepository.findAllByPrioridade(3)).thenReturn(listaEsperada);
        List<Tarefa> listaRetornada = tarefaService.mostrarTarefasPelaPrioridade(3);
        assertEquals(listaEsperada, listaRetornada);
        verify(tarefaRepository, times(1)).findAllByPrioridade(3);
    }

    @Test
    void deveMostrarTarefasConcluidasOuNaoConcluidasExcecaoTest()
    {
        when(tarefaRepository.findAllByConcluida(true)).thenReturn(Collections.emptyList());
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.mostrarTarefasConcluidasOuNao(true));
        assertEquals("Nenhuma tarefa encontrada com o status inserido", ex.getMessage());
        verify(tarefaRepository, times(1)).findAllByConcluida(true);

    }

    @Test
    void deveMostrarTarefasConcluidasOuNaoConcluidasTest()
    {
        tarefa1.setConcluida(false);
        tarefa2.setConcluida(true);

        List<Tarefa> listaEsperadaNaoConcluida = List.of(tarefa1);
        when(tarefaRepository.findAllByConcluida(false)).thenReturn(listaEsperadaNaoConcluida);
        List<Tarefa> listaRetornadaNaoConcluida = tarefaService.mostrarTarefasConcluidasOuNao(false);
        assertEquals(listaEsperadaNaoConcluida, listaRetornadaNaoConcluida);
        verify(tarefaRepository, times(2)).findAllByConcluida(false);

        List<Tarefa> listaEsperadaConcluida = List.of(tarefa2);
        when(tarefaRepository.findAllByConcluida(true)).thenReturn(listaEsperadaConcluida);
        List<Tarefa> listaRetornada = tarefaService.mostrarTarefasConcluidasOuNao(true);
        assertEquals(listaEsperadaConcluida, listaRetornada);
        verify(tarefaRepository, times(2)).findAllByConcluida(true);

    }

}
