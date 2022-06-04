package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.controller.response.ProdutoResponse;
import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class ListarProdutosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve listar os produtos cadastrados")
    void test1() throws Exception {
        Produto bolacha = new Produto("Bolacha", "água e sal", new BigDecimal("2.50"));
        Produto manteiga = new Produto("Manteiga", "light", new BigDecimal("5.50"));

        produtoRepository.saveAll(List.of(bolacha,manteiga));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/produtos").contentType(MediaType.APPLICATION_JSON);

        String payload = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<ProdutoResponse> produtos = mapper.readValue(payload, typeFactory.constructCollectionType(
                List.class,
                ProdutoResponse.class
        ));

        assertThat(produtos)
                .hasSize(2)
                .extracting("titulo","descricao","preco")
                .contains(
                        new Tuple(bolacha.getTitulo(), bolacha.getDescricao(), bolacha.getPreco()),
                        new Tuple(manteiga.getTitulo(), manteiga.getDescricao(), manteiga.getPreco())
                );
    }

    @Test
    @DisplayName("Deve devolver uma lista de produtos vazia")
    void test2() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/produtos").contentType(MediaType.APPLICATION_JSON);

        String payload = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<ProdutoResponse> produtos = mapper.readValue(payload, typeFactory.constructCollectionType(
                List.class,
                ProdutoResponse.class
        ));

        assertTrue(produtos.isEmpty());
    }
}