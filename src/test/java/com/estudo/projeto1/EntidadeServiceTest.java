package com.estudo.projeto1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntidadeServiceTest {

    private EntidadeRepository entidadeRepository;
    private EntidadeService entidadeService;

    @BeforeEach
    void setUp() {
        entidadeRepository = Mockito.mock(EntidadeRepository.class);
        entidadeService = new EntidadeService(entidadeRepository);
    }

    @Test
    void testSalvar() {
        Modelo entidade = new Modelo(1L, "Entidade Teste", "Descrição Teste", 10);
        when(entidadeRepository.save(entidade)).thenReturn(entidade);

        Modelo resultado = entidadeService.salvar(entidade);

        assertNotNull(resultado);
        assertEquals("Entidade Teste", resultado.getNome());
        verify(entidadeRepository, times(1)).save(entidade);
    }

    @Test
    void testBuscarTodas() {
        List<Modelo> entidades = Arrays.asList(
                new Modelo(1L, "Entidade 1", "Descrição 1", 10),
                new Modelo(2L, "Entidade 2", "Descrição 2", 20)
        );
        when(entidadeRepository.findAll()).thenReturn(entidades);

        List<Modelo> resultado = entidadeService.buscarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Entidade 1", resultado.get(0).getNome());
        verify(entidadeRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        Modelo entidade = new Modelo(1L, "Entidade Teste", "Descrição Teste", 10);
        when(entidadeRepository.findById(1L)).thenReturn(Optional.of(entidade));

        Optional<Modelo> resultado = entidadeService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Entidade Teste", resultado.get().getNome());
        verify(entidadeRepository, times(1)).findById(1L);
    }

    @Test
    void testExcluir() {
        doNothing().when(entidadeRepository).deleteById(1L);

        entidadeService.excluir(1L);

        verify(entidadeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testBuscarPorNome() {
        List<Modelo> entidades = Arrays.asList(
                new Modelo(1L, "Teste 1", "Descrição Teste 1", 5),
                new Modelo(2L, "Teste 2", "Descrição Teste 2", 15)
        );
        when(entidadeRepository.findByNomeContaining("Teste")).thenReturn(entidades);

        List<Modelo> resultado = entidadeService.buscarPorNome("Teste");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Teste 1", resultado.get(0).getNome());
        verify(entidadeRepository, times(1)).findByNomeContaining("Teste");
    }
}
