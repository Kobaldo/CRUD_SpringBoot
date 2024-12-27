package com.estudo.projeto1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModeloTest {

    private Modelo modelo;

    @BeforeEach
    void setUp() {
        // Inicializa o modelo com dados de exemplo
        modelo = new Modelo(1L, "Produto A", "Descrição do produto A", 10);
    }

    @Test
    void testConstrutorComTodosParametros() {
        Modelo modelo = new Modelo(1L, "Produto A", "Descrição do produto A", 10);

        assertEquals(1L, modelo.getId());
        assertEquals("Produto A", modelo.getNome());
        assertEquals("Descrição do produto A", modelo.getDescricao());
        assertEquals(10, modelo.getQuantidade());
    }

    @Test
    void testConstrutorComIdENome() {
        Modelo modelo = new Modelo(2L, "Produto B");

        assertEquals(2L, modelo.getId());
        assertEquals("Produto B", modelo.getNome());
        assertNull(modelo.getDescricao());
        assertEquals(0, modelo.getQuantidade());
    }

    @Test
    void testGettersESetters() {
        modelo.setId(2L);
        modelo.setNome("Produto C");
        modelo.setDescricao("Descrição do produto C");
        modelo.setQuantidade(20);

        assertEquals(2L, modelo.getId());
        assertEquals("Produto C", modelo.getNome());
        assertEquals("Descrição do produto C", modelo.getDescricao());
        assertEquals(20, modelo.getQuantidade());
    }

    @Test
    void testSetDescricaoNull() {
        modelo.setDescricao(null);
        assertNull(modelo.getDescricao());
    }

    @Test
    void testSetQuantidadeNegativa() {
        modelo.setQuantidade(-5);
        assertEquals(-5, modelo.getQuantidade());
    }

    @Test
    void testSetQuantidadePositiva() {
        modelo.setQuantidade(15);
        assertEquals(15, modelo.getQuantidade());
    }
}
