package com.estudo.projeto1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Controller.class)
@ContextConfiguration(classes = ControllerTest.TestConfig.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntidadeService entidadeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Modelo modelo;

    @BeforeEach
    void setUp() {
        modelo = new Modelo(1L, "teste", "teste mock",10); // Preencha aqui com os valores da sua classe Modelo
    }

    @Test
    void salvar_deveRetornarEntidadeCriada() throws Exception {
        when(entidadeService.salvar(any(Modelo.class))).thenReturn(modelo);

        mockMvc.perform(post("/entidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(modelo.getId()))
                .andExpect(jsonPath("$.nome").value(modelo.getNome()))
                .andExpect(jsonPath("$.descricao").value(modelo.getDescricao()))
                .andExpect(jsonPath("$.quantidade").value(modelo.getQuantidade()));
    }

    @Test
    void buscarTodas_deveRetornarListaDeEntidades() throws Exception {
        List<Modelo> modelos = Collections.singletonList(modelo);
        when(entidadeService.buscarTodas()).thenReturn(modelos);

        mockMvc.perform(get("/entidades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(modelo.getId()))
                .andExpect(jsonPath("$[0].nome").value(modelo.getNome()))
                .andExpect(jsonPath("$[0].descricao").value(modelo.getDescricao()))
                .andExpect(jsonPath("$[0].quantidade").value(modelo.getQuantidade()));
    }

    @Test
    void buscarPorId_deveRetornarEntidade() throws Exception {
        when(entidadeService.buscarPorId(anyLong())).thenReturn(Optional.of(modelo));

        mockMvc.perform(get("/entidades/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(modelo.getId()))
                .andExpect(jsonPath("$.nome").value(modelo.getNome()))
                .andExpect(jsonPath("$.descricao").value(modelo.getDescricao()))
                .andExpect(jsonPath("$.quantidade").value(modelo.getQuantidade()));;
    }

    @Test
    void excluir_deveRetornarNoContent() throws Exception {
        Mockito.doNothing().when(entidadeService).excluir(anyLong());

        mockMvc.perform(delete("/entidades/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorNome_deveRetornarListaDeEntidades() throws Exception {
        List<Modelo> modelos = Arrays.asList(modelo);
        when(entidadeService.buscarPorNome("Teste")).thenReturn(modelos);

        mockMvc.perform(get("/entidades/buscar").param("nome", "Teste", "teste mock","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(modelo.getId()))
                .andExpect(jsonPath("$[0].nome").value(modelo.getNome()))
                .andExpect(jsonPath("$[0].descricao").value(modelo.getDescricao()))
                .andExpect(jsonPath("$[0].quantidade").value(modelo.getQuantidade()));;
    }

    @Configuration
    static class TestConfig {
        @Bean
        public EntidadeService entidadeService() {
            return Mockito.mock(EntidadeService.class);
        }
    }
}
