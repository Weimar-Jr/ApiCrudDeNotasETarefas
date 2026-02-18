package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Nota;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.NotaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.TarefaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.NotaRepository;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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

    Nota nota1;
    Tarefa tarefa1;
    @BeforeEach
    void inicio()
    {
            nota1 = new Nota();
            tarefa1 = new Tarefa();

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
    void deveDarExcecaoAtribuirTarefaTest()
    {
       when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());
       TarefaException ex = assertThrows(TarefaException.class, () -> atribuicaoEDesatribuicaoTarefaNotaService.atribuirTarefaANota(1L,1L));
       assertEquals("não foi achada a tarefa pelo id", ex.getMessage());
       verify(tarefaRepository, times(1)).findById(1L);
       verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveDarExcecaoAtribuirNotaTest()
    {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        when(notaRepository.findById(1L)).thenReturn(Optional.empty());
        NotaException ex = assertThrows(NotaException.class, () -> atribuicaoEDesatribuicaoTarefaNotaService.atribuirTarefaANota(1L,1L));
        assertEquals("não foi achada a nota pelo id", ex.getMessage());
        verify(tarefaRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(notaRepository);
    }

    @Test
    void deveAtribuirTarefaANotaTest() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        when(notaRepository.findById(1L)).thenReturn(Optional.of(nota1));
        when(notaRepository.listarTarefasDaNota(1L)).thenReturn(List.of(tarefa1));

        List<Tarefa> tarefasDaNota = atribuicaoEDesatribuicaoTarefaNotaService.atribuirTarefaANota(1L, 1L);
        assertEquals(1, tarefasDaNota.size());
        assertEquals(tarefa1, tarefasDaNota.get(0));
        verify(tarefaRepository, times(1)).findById(1L);
        verify(notaRepository, times(1)).findById(1L);
        verify(notaRepository, times(1)).listarTarefasDaNota(1L);
        verifyNoMoreInteractions(tarefaRepository);

    }

    @Test
    void deveDarExcecaoDesatribuicaoTarefaTest()
    {
        when(notaRepository.findById(1L)).thenReturn(Optional.of(nota1));
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());
        TarefaException ex = assertThrows(TarefaException.class, () -> atribuicaoEDesatribuicaoTarefaNotaService.deletarTarefaDeNota(1L,1L));
        assertEquals("não existe tarefa com esse id", ex.getMessage());
        verify(notaRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveDarExcecaoDesatribuicaoNotaTest()
    {
        when(notaRepository.findById(1L)).thenReturn(Optional.empty());
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        NotaException ex = assertThrows(NotaException.class, () -> atribuicaoEDesatribuicaoTarefaNotaService.deletarTarefaDeNota(1L,1L));
        assertEquals("não existe nota com esse id", ex.getMessage());
        verify(notaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(notaRepository);
    }

    @Test
    void deveDesatribuicaoTarefaDeNotaTest()
    {
        when(notaRepository.findById(1L)).thenReturn(Optional.of(nota1));
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));

        atribuicaoEDesatribuicaoTarefaNotaService.deletarTarefaDeNota(1L,1L);

        verify(notaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(notaRepository);
    }

}
