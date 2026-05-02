package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Services;

import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Mapper.NotaMapper;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.AtualizarNotaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.CriarNotaRequestDTO;
import com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.DTOEntidades.Nota.NotaResponseDTO;
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
    @Mock
    NotaMapper notaMapper;

   private Nota nota1;
   private Nota nota2;
   private Nota notaEditada;
   private NotaResponseDTO notaResponseDTO1;
   private NotaResponseDTO notaResponseDTO2;
   private CriarNotaRequestDTO criarNotaRequestDTO1;
   private AtualizarNotaRequestDTO atualizarNotaRequestDTO1;


    @BeforeEach
    void objetosParaTeste()
    {
        nota1 = new Nota();
        nota2 = new Nota();
        notaEditada = new Nota();


        nota1.setId(12L);
        nota1.setTituloNota("primeria nota para teste");
        nota1.setTextoNota("essa é a primeira vez que eu faço um teste unitario");
        nota1.setTag("teste");

        nota2.setId(22L);
        nota2.setTituloNota("segunda nota para teste");
        nota2.setTextoNota("espero aprender bem");
        nota2.setTag("teste");

        notaEditada.setId(12L);
        notaEditada.setTituloNota("nota editada");
        notaEditada.setTextoNota("conteudo");
        notaEditada.setTag("teste");

        notaResponseDTO1 = new NotaResponseDTO(12L, "primeria nota para teste", "essa é a primeira vez que eu faço um teste unitario", "teste", new ArrayList<>());
        notaResponseDTO2 = new NotaResponseDTO(22L, "segunda nota para teste", "espero aprender bem", "teste", new ArrayList<>());

        criarNotaRequestDTO1 = new CriarNotaRequestDTO("primeria nota para teste", "essa é a primeira vez que eu faço um teste unitario", "teste", new ArrayList<>());

        atualizarNotaRequestDTO1 = new AtualizarNotaRequestDTO(12L, "primeria nota para teste", "essa é a primeira vez que eu faço um teste unitario", "teste", new ArrayList<>());
    }

    @Test
    void deveDarExecaoRetornarNenhumaNota() {
        when(notaRepository.findAll()).thenReturn(Collections.emptyList());
        NotaException excecao = assertThrows(NotaException.class, () -> notaService.listarNotas());
        assertEquals("não tem notas cadastradas no sistema.", excecao.getMessage());
        verify(notaRepository, times(1)).findAll();
        verifyNoMoreInteractions(notaRepository);
    }

    @Test
    void deveListarNotas()
    {
        List<Nota> listaEsperadaEntidade = new ArrayList<>();
        listaEsperadaEntidade.add(nota1);
        listaEsperadaEntidade.add(nota2);
        List<NotaResponseDTO> listaEsperada = new ArrayList<>();
        listaEsperada.add(notaResponseDTO1);
        listaEsperada.add(notaResponseDTO2);

        when(notaRepository.findAll()).thenReturn(listaEsperadaEntidade);
        when(notaMapper.toNotaResponseDTO(nota1)).thenReturn(notaResponseDTO1);
        when(notaMapper.toNotaResponseDTO(nota2)).thenReturn(notaResponseDTO2);
        List<NotaResponseDTO > listaResultado = notaService.listarNotas();

        assertEquals(listaEsperada, listaResultado);
        verify(notaRepository, times(1)).findAll();
        verifyNoMoreInteractions(notaRepository);

    }

    @Test
    void deveSalvarAsNotas() {
        when(notaRepository.save(any(Nota.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(notaMapper.toNota(criarNotaRequestDTO1)).thenReturn(nota1);
        when(notaMapper.toNotaResponseDTO(nota1)).thenReturn(notaResponseDTO1);
        NotaResponseDTO notaRetornada = notaService.criarNota(criarNotaRequestDTO1);
        ArgumentCaptor<Nota> captor = ArgumentCaptor.forClass(Nota.class);
        verify(notaRepository, times(1)).save(captor.capture());
        Nota salvo = captor.getValue();
        assertEquals(criarNotaRequestDTO1.tituloNota(), salvo.getTituloNota());
        assertEquals(criarNotaRequestDTO1.textoNota(), salvo.getTextoNota());
        assertEquals(criarNotaRequestDTO1.tag(), salvo.getTag());
        assertEquals(notaResponseDTO1, notaRetornada);

        verifyNoMoreInteractions(notaRepository);

    }

    @Test
    void deveDarErroEmEditarNota()
    {

        when(notaRepository.findById(atualizarNotaRequestDTO1.id())).thenReturn(Optional.empty());
        when(notaMapper.toNota(any(AtualizarNotaRequestDTO.class))).thenReturn(nota1);
        NotaException excecao = assertThrows(NotaException.class, () -> notaService.editarNota(atualizarNotaRequestDTO1));
        assertEquals("não existe esta nota no sistema.", excecao.getMessage());

        verify(notaRepository, never()).save(any(Nota.class));
        verify(notaRepository, times(1)).findById(nota1.getId());
        verifyNoMoreInteractions(notaRepository);

    }

    @Test
    void deveEditarNota() {

        AtualizarNotaRequestDTO notaAtualizada = new AtualizarNotaRequestDTO(12L, "nota editada", "conteudo", "teste", new ArrayList<>());

        when(notaRepository.findById(atualizarNotaRequestDTO1.id())).thenReturn(Optional.of(notaEditada));
        when(notaRepository.save(any(Nota.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(notaMapper.toNota(any(AtualizarNotaRequestDTO.class))).thenReturn(notaEditada);
        when(notaMapper.toNotaResponseDTO(any(Nota.class))).thenReturn(new NotaResponseDTO(12L, "nota editada", "conteudo", "teste", new ArrayList<>()));
        NotaResponseDTO resultado = notaService.editarNota(notaAtualizada);
        ArgumentCaptor<Nota> captor = ArgumentCaptor.forClass(Nota.class);
        verify(notaRepository, times(1)).findById(notaAtualizada.id());
        verify(notaRepository, times(1)).save(captor.capture());
        Nota salvo = captor.getValue();
        assertEquals(resultado.id(), salvo.getId());
        assertEquals(resultado.tituloNota(), salvo.getTituloNota());
        assertEquals(resultado.textoNota(), salvo.getTextoNota());
        assertEquals(resultado.tag(), salvo.getTag());
        verifyNoMoreInteractions(notaRepository);

    }

    @Test
    void deveDarExcecaoEmDeletarNota()
    {
        Nota notaNova = new Nota();
        notaNova.setId(123L);

        when(notaRepository.existsById(notaNova.getId())).thenReturn(false);

        NotaException execao = assertThrows(NotaException.class, () -> notaService.deletarNota(notaNova.getId()));

        assertEquals("não existe esta nota no sistema.", execao.getMessage());
        verify(notaRepository, times(0)).deleteById(notaNova.getId());
        verifyNoMoreInteractions(notaRepository);

    }

    @Test
    void deveDeletarANota()
    {
        when(notaRepository.existsById(nota1.getId())).thenReturn(true);

        notaService.deletarNota(nota1.getId());
        verify(notaRepository, times(1)).deleteById(nota1.getId());
        verifyNoMoreInteractions(notaRepository);

    }

    @Test
    void deveDarExcecaoEmAcharNotasPelaTag()
    {
        String tagTeste = "teste";
        when(notaRepository.findAllByTag(tagTeste)).thenReturn(Collections.emptyList());
        NotaException execao = assertThrows(NotaException.class, () -> notaService.exibirNotasPelaTag(tagTeste));
        assertEquals("não tem notas com essa tag.", execao.getMessage());

        verify(notaRepository, times(1)).findAllByTag(tagTeste);
        verifyNoMoreInteractions(notaRepository);
    }

    @Test
    void deveAcharNotasPelaTag()
    {
        String tagTeste = "teste";
        List<Nota> listaEsperada = new ArrayList<>();
        listaEsperada.add(nota1);
        listaEsperada.add(nota2);
        List<NotaResponseDTO> listaEsperadaDTO = new ArrayList<>();
        listaEsperadaDTO.add(notaResponseDTO1);
        listaEsperadaDTO.add(notaResponseDTO2);
        when(notaRepository.findAllByTag(tagTeste)).thenReturn(listaEsperada);
        when(notaMapper.toNotaResponseDTO(nota1)).thenReturn(notaResponseDTO1);
        when(notaMapper.toNotaResponseDTO(nota2)).thenReturn(notaResponseDTO2);

        List<NotaResponseDTO> listaResultado = notaService.exibirNotasPelaTag(tagTeste);
        assertEquals(listaEsperadaDTO, listaResultado);

        verify(notaRepository, times(1)).findAllByTag(tagTeste);
        verifyNoMoreInteractions(notaRepository);

    }



}