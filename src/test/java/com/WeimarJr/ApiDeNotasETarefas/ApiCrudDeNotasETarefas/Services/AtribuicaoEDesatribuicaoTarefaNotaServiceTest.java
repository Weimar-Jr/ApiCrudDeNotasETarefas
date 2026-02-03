package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.ExceptionsTarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtribuicaoEDesatribuicaoTarefaNotaServiceTest {
    @InjectMocks
    private AtribuicaoEDesatribuicaoTarefaNotaService atribuicaoEDesatribuicaoTarefaNotaService;
    @Mock
    private TarefaRepository tarefaRepository;
    @Mock
    private NotaRepository notaRepository;

    Nota nota1 = new Nota();
    Tarefa tarefa1 = new Tarefa();
    @BeforeEach
    void inicio()
    {
        nota1.setId(1L);
        nota1.setTituloNota("nota teste");
        nota1.setNota("usuario teste");

        tarefa1.setId(1L);
        tarefa1.setNomeTarefa("tarefa teste");
        tarefa1.setDescricaoTarefa("descricao teste");
        tarefa1.setPrioridade(3);
        tarefa1.setConcluida(false);

    }

    @Test
    void deveAtribuirTarefaANotaExcecaoTarefaTest()
    {
        when(tarefaRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        ExceptionsTarefa ex = assertThrows(ExceptionsTarefa.class, () -> atribuicaoEDesatribuicaoTarefaNotaService.atribuirTarefaANota(1L,2L));
        assertEquals("não foi achada a tarefa pelo id", ex.getMessage());
        verify(tarefaRepository).findById(1L);
    }

    @Test
    void deveAtribuirTarefaANotaExcecaoNotaTest()
    {
        when(tarefaRepository.findById(1L)).thenReturn(java.util.Optional.of(tarefa1));
        when(notaRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        ExceptionsTarefa ex = assertThrows(ExceptionsTarefa.class, () -> atribuicaoEDesatribuicaoTarefaNotaService.atribuirTarefaANota(1L,2L));
        assertEquals("não foi achada a nota pelo id", ex.getMessage());
        verify(tarefaRepository).findById(1L);
        verify(notaRepository).findById(2L);
    }

    @Test
    void deveAtribuirTarefaANotaTest()
    {
        when(tarefaRepository.findById(1L)).thenReturn(java.util.Optional.of(tarefa1));
        when(notaRepository.findById(1L)).thenReturn(java.util.Optional.of(nota1));
        when(notaRepository.listarTarefasDaNota(1L)).thenReturn(java.util.List.of(tarefa1));

        atribuicaoEDesatribuicaoTarefaNotaService.atribuirTarefaANota(1L,1L);

        assertEquals(1, notaRepository.listarTarefasDaNota(1L).size());
        assertEquals(tarefa1, notaRepository.listarTarefasDaNota(1L).getFirst());
        verify(tarefaRepository, times(1)).findById(1L);
        verify(notaRepository, times(1)).findById(1L);
        verify(notaRepository, times(3)).listarTarefasDaNota(1L);

    }

}
