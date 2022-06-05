package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.model.StatusProduto;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class InativarProdutosPresentesControllerTest {
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
    @DisplayName("Deve devolver uma lista vazia caso não existam produtos a serem inativados")
    void deveDevolverListaVaziaCasoNaoExistamProdutosASeremInativados() throws Exception {

        Produto produtoOleo = new Produto("Óleo", "De soja", new BigDecimal("5"), StatusProduto.ATIVO, LocalDateTime.now());
        Produto produtoArroz = new Produto("Arroz", "Branco", new BigDecimal("7"), StatusProduto.ATIVO, LocalDateTime.now());

        produtoRepository.saveAll(List.of(produtoOleo,produtoArroz));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/produtos/inativar")
                .contentType(MediaType.APPLICATION_JSON);

        String payload = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<Long> idsProdutos = mapper.readValue(payload, typeFactory.constructCollectionType(
                List.class,
                Long.class
        ));

        assertTrue(idsProdutos.isEmpty());
    }

    @Test
    @DisplayName("Deve devolver uma lista com ids dos produtos que foram inativados")
    void deveDevolverListaComIdsDosProdutosQueForamInativados() throws Exception {

        Produto produtoOleo = new Produto("Óleo", "De soja", new BigDecimal("5"), StatusProduto.PENDENTE, LocalDateTime.now());
        Produto produtoArroz = new Produto("Arroz", "Branco", new BigDecimal("7"), StatusProduto.PENDENTE, LocalDateTime.now());

        produtoRepository.saveAll(List.of(produtoOleo, produtoArroz));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/produtos/inativar")
                .contentType(MediaType.APPLICATION_JSON);

        String payload = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<Long> idsProdutos = mapper.readValue(payload, typeFactory.constructCollectionType(
                List.class,
                Long.class
        ));

        assertEquals(2,idsProdutos.size());

        Optional<Produto> possivelProduto1= produtoRepository.findById(idsProdutos.get(0));
        assertTrue(possivelProduto1.isPresent());
        Optional<Produto> possivelProduto2= produtoRepository.findById(idsProdutos.get(1));
        assertTrue(possivelProduto2.isPresent());

        assertEquals(StatusProduto.INATIVO,possivelProduto1.get().getStatus());
        assertEquals(StatusProduto.INATIVO,possivelProduto2.get().getStatus());
    }

}