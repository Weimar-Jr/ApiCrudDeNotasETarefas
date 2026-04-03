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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
        verify(notaRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(notaRepository);
    }

    @Test
    void deveDarExcecaoTarefaJaAtribuidaTest()
    {
        nota1.adicionarTarefa(tarefa1);
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        when(notaRepository.findById(1L)).thenReturn(Optional.of(nota1));
        NotaException ex = assertThrows(NotaException.class, () -> atribuicaoEDesatribuicaoTarefaNotaService.atribuirTarefaANota(1L,1L));
        assertEquals("tarefa já atribuida a nota", ex.getMessage());
        verify(tarefaRepository, times(1)).findById(1L);
        verify(notaRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(tarefaRepository);
    }


    @Test
    void deveAtribuirTarefaANotaTest() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        when(notaRepository.findById(1L)).thenReturn(Optional.of(nota1));
        when(notaRepository.save(any(Nota.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        atribuicaoEDesatribuicaoTarefaNotaService.atribuirTarefaANota(1L, 1L);
        ArgumentCaptor<Tarefa> captorTarefa = ArgumentCaptor.forClass(Tarefa.class);
        ArgumentCaptor<Nota> captorNota = ArgumentCaptor.forClass(Nota.class);
        verify(tarefaRepository, times(1)).save(captorTarefa.capture());
        verify(notaRepository, times(1)).save(captorNota.capture());
        Nota notaSalva = captorNota.getValue();
        Tarefa tarefaSalva = captorTarefa.getValue();


        assertEquals(tarefaSalva.getNota(), notaSalva);
        assertEquals(notaSalva.getTarefasRelacionadas().get(0), tarefaSalva);
        verify(tarefaRepository, times(1)).findById(1L);
        verify(notaRepository, times(1)).findById(1L);



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
        when(notaRepository.save(any(Nota.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));
        atribuicaoEDesatribuicaoTarefaNotaService.deletarTarefaDeNota(1L,1L);

         ArgumentCaptor<Tarefa> captorTarefa = ArgumentCaptor.forClass(Tarefa.class);
        ArgumentCaptor<Nota> captorNota = ArgumentCaptor.forClass(Nota.class);
        verify(tarefaRepository, times(1)).save(captorTarefa.capture());
        verify(notaRepository, times(1)).save(captorNota.capture());
        Nota notaSalva = captorNota.getValue();
        Tarefa tarefaSalva = captorTarefa.getValue();
        assertEquals(tarefaSalva.getNota(), null);
        assert(!notaSalva.getTarefasRelacionadas().contains(tarefaSalva));
        verify(notaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
        verify(notaRepository, times(1)).save(any(Nota.class));
        verifyNoMoreInteractions(notaRepository);
    }

}
