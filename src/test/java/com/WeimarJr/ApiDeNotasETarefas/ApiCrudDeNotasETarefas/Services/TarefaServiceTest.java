package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Mapper.TarefaMapper;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.AtualizarTarefaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.CriarTarefaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Tarefa.TarefaResponseDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades.Tarefa;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions.TarefaException;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @Mock
    private TarefaMapper tarefaMapper;


    Tarefa tarefa1;
    Tarefa tarefa2;
    Tarefa tarefaEditada;
    AtualizarTarefaRequestDTO atualizarTarefaRequestDTO1;
    CriarTarefaRequestDTO criarTarefaRequestDTO1;
    TarefaResponseDTO tarefaResponseDTO1;
    TarefaResponseDTO tarefaResponseDTO2;
    TarefaResponseDTO tarefaResponseDTOEditada;


    @BeforeEach
    void tarefaParaTestes()
    {
            tarefaEditada = new Tarefa(20L, "teste editado", "descrição editada", 2, true, null);


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

            criarTarefaRequestDTO1 = new CriarTarefaRequestDTO("primeiro teste", "descrição", 5);
            atualizarTarefaRequestDTO1 = new AtualizarTarefaRequestDTO(123L, "primeiro teste", "", 1, true, null );
            tarefaResponseDTO1 = new TarefaResponseDTO(123L, "primeiro teste", "descrição", 5, false, null);
            tarefaResponseDTO2 = new TarefaResponseDTO(456L, "segundo teste", "", 1, true, null);
            tarefaResponseDTOEditada = new TarefaResponseDTO(123L, "teste editado", "descrição editada", 2, true, null);

    }

    @Test
    void deveSalvarTarefaTest()
    {
        when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(tarefaMapper.toTarefa(any(CriarTarefaRequestDTO.class))).thenReturn(tarefa1);
        when(tarefaMapper.toTarefaResponseDTO(any(Tarefa.class))).thenReturn(tarefaResponseDTO1);

        TarefaResponseDTO tarefaRetornada1 =  tarefaService.criarTarefa(criarTarefaRequestDTO1);
        ArgumentCaptor<Tarefa> captor = ArgumentCaptor.forClass(Tarefa.class);
        verify(tarefaRepository, times(1)).save(captor.capture());
        Tarefa salvo = captor.getValue();
        assertEquals(criarTarefaRequestDTO1.tituloTarefa(), salvo.getNomeTarefa());
        assertEquals(criarTarefaRequestDTO1.descricaoTarefa(), salvo.getDescricaoTarefa());
        assertEquals(criarTarefaRequestDTO1.prioridade(), salvo.getPrioridade());
        assertEquals(tarefaResponseDTO1, tarefaRetornada1);
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
        List<Tarefa> listaEsperadaEntidades = List.of(tarefa1,tarefa2);
        List<TarefaResponseDTO> listaEsperada = List.of(tarefaResponseDTO1, tarefaResponseDTO2);
        when(tarefaRepository.findAll(any(Sort.class))).thenReturn(listaEsperadaEntidades);
        when(tarefaMapper.toTarefaResponseDTO(any(Tarefa.class))).thenReturn(tarefaResponseDTO1).thenReturn(tarefaResponseDTO2);
        List<TarefaResponseDTO> listaRetornada = tarefaService.listarTarefas();
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
        TarefaResponseDTO tarefaEsperada = tarefaResponseDTO1;
        when(tarefaRepository.findById(tarefa1.getId())).thenReturn(Optional.of(tarefa1));
        when(tarefaMapper.toTarefaResponseDTO(any(Tarefa.class))).thenReturn(tarefaResponseDTO1);
        TarefaResponseDTO tarefaRetornada = tarefaService.acharPeloId(tarefa1.getId());
        assertEquals(tarefaEsperada, tarefaRetornada);
        verify(tarefaRepository, times(1)).findById(tarefa1.getId());
        verifyNoMoreInteractions(tarefaRepository);

    }

    @Test
    void deveEditarTarefaExecaoTest()
    {
        AtualizarTarefaRequestDTO tarefaAtualizada = new AtualizarTarefaRequestDTO(20L, "teste editado", "descrição editada", 2, true, null);

        when(tarefaRepository.findById(tarefaEditada.getId())).thenReturn(Optional.empty());
        when(tarefaMapper.toTarefa(any(AtualizarTarefaRequestDTO.class))).thenReturn(tarefaEditada);
        TarefaException ex = assertThrows(TarefaException.class, () -> tarefaService.editarTarefa(tarefaAtualizada));
        assertEquals("tarefa não existe.", ex.getMessage());
        verify(tarefaRepository, times(1)).findById(tarefaAtualizada.id());
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void deveEditartarefaTest()
    {
        AtualizarTarefaRequestDTO tarefaEditada = new AtualizarTarefaRequestDTO(123L, "teste editado", "descrição editada", 2, true, null);
        Tarefa tarefaAtualizada = new Tarefa();
        tarefaAtualizada.setId(tarefaEditada.id());
        tarefaAtualizada.setNomeTarefa(tarefaEditada.tituloTarefa());
        tarefaAtualizada.setDescricaoTarefa(tarefaEditada.descricaoTarefa());
        tarefaAtualizada.setPrioridade(tarefaEditada.prioridade());
        tarefaAtualizada.setConcluida(tarefaEditada.concluida());

        when(tarefaRepository.findById(tarefaEditada.id())).thenReturn(Optional.of(tarefa1));
        when(tarefaMapper.toTarefa(any(AtualizarTarefaRequestDTO.class))).thenReturn(tarefaAtualizada);
        when(tarefaMapper.toTarefaResponseDTO(any(Tarefa.class))).thenReturn(tarefaResponseDTOEditada);

        TarefaResponseDTO resultado = tarefaService.editarTarefa(tarefaEditada);
        ArgumentCaptor<Tarefa> captor = ArgumentCaptor.forClass(Tarefa.class);
        verify(tarefaRepository, times(1)).findById(tarefaEditada.id());
        verify(tarefaRepository, times(1)).save(captor.capture());
        Tarefa salvo = captor.getValue();
        assertEquals(tarefaEditada.id(), salvo.getId());
        assertEquals(tarefaEditada.tituloTarefa(), salvo.getNomeTarefa());
        assertEquals(tarefaEditada.descricaoTarefa(), salvo.getDescricaoTarefa());
        assertEquals(tarefaEditada.prioridade(), salvo.getPrioridade());
        assertEquals(tarefaEditada.concluida(), salvo.getConcluida());
        assertEquals(tarefaResponseDTOEditada, resultado);
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
        List<Tarefa> listaEsperadaEntidades = List.of(tarefa1);
        List<TarefaResponseDTO> listaEsperada = List.of(tarefaResponseDTO1);
        when(tarefaRepository.findAllByPrioridade(5)).thenReturn(listaEsperadaEntidades);
        when(tarefaMapper.toTarefaResponseDTO(any(Tarefa.class))).thenReturn(tarefaResponseDTO1);
        List<TarefaResponseDTO> listaRetornada = tarefaService.mostrarTarefasPelaPrioridade(5);
        assertEquals(listaEsperada, listaRetornada);
        verify(tarefaRepository, times(1)).findAllByPrioridade(5);
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
        List<Tarefa> listaEsperada = List.of(tarefa1);
        List<TarefaResponseDTO> listaEsperadaDTO = List.of(tarefaResponseDTO1);
        when(tarefaRepository.findAllByConcluida(false)).thenReturn(listaEsperada);
        when(tarefaMapper.toTarefaResponseDTO(any(Tarefa.class))).thenReturn(tarefaResponseDTO1);
        List<TarefaResponseDTO> resultado = tarefaService.mostrarTarefasConcluidasOuNao(false);
        assertEquals(listaEsperadaDTO, resultado);
        verify(tarefaRepository, times(1)).findAllByConcluida(false);
        verifyNoMoreInteractions(tarefaRepository);
    }
    @Test void deveMostrarTarefasConcluidas()
    {
        List<Tarefa> listaEsperada = List.of(tarefa2);
        List<TarefaResponseDTO> listaEsperadaDTO = List.of(tarefaResponseDTO2);
        when(tarefaRepository.findAllByConcluida(true)).thenReturn(listaEsperada);
        when(tarefaMapper.toTarefaResponseDTO(any(Tarefa.class))).thenReturn(tarefaResponseDTO2);
        List<TarefaResponseDTO> resultado = tarefaService.mostrarTarefasConcluidasOuNao(true);
        assertEquals(listaEsperadaDTO, resultado);
        verify(tarefaRepository, times(1)).findAllByConcluida(true);
        verifyNoMoreInteractions(tarefaRepository);
    }

}
