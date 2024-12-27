package com.estudo.projeto1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ControllerTest {

    private MockMvc mockMvc;
    private EntidadeService entidadeService;

    @BeforeEach
    void setUp() {
        entidadeService = Mockito.mock(EntidadeService.class);
        Controller controller = new Controller(entidadeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testSalvar() throws Exception {
        Modelo entidade = new Modelo(1L, "Entidade Teste", "Descrição Teste", 10);
        when(entidadeService.salvar(any(Modelo.class))).thenReturn(entidade);

        mockMvc.perform(post("/entidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"nome\":\"Entidade Teste\",\"descricao\":\"Descrição Teste\",\"quantidade\":10}"))
                .andExpect(status().isOk());


        verify(entidadeService, times(1)).salvar(any(Modelo.class));
    }

    @Test
    void testBuscarTodas() throws Exception {
        List<Modelo> entidades = Arrays.asList(
                new Modelo(1L, "Entidade 1"),
                new Modelo(2L, "Entidade 2")
        );
        when(entidadeService.buscarTodas()).thenReturn(entidades);

        mockMvc.perform(get("/entidades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value("Entidade 1"))
                .andExpect(jsonPath("$[1].nome").value("Entidade 2"));

        verify(entidadeService, times(1)).buscarTodas();
    }

    @Test
    void testBuscarPorId() throws Exception {
        Modelo entidade = new Modelo(1L, "Entidade Teste");
        when(entidadeService.buscarPorId(1L)).thenReturn(Optional.of(entidade));

        mockMvc.perform(get("/entidades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Entidade Teste"));

        verify(entidadeService, times(1)).buscarPorId(1L);
    }

    @Test
    void testExcluir() throws Exception {
        doNothing().when(entidadeService).excluir(1L);

        mockMvc.perform(delete("/entidades/1"))
                .andExpect(status().isOk());

        verify(entidadeService, times(1)).excluir(1L);
    }

    @Test
    void testBuscarPorNome() throws Exception {
        List<Modelo> entidades = Arrays.asList(
                new Modelo(1L, "Entidade Teste")
        );
        when(entidadeService.buscarPorNome("Teste")).thenReturn(entidades);

        mockMvc.perform(get("/entidades/buscar")
                        .param("nome", "Teste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Entidade Teste"));

        verify(entidadeService, times(1)).buscarPorNome("Teste");
    }
}
