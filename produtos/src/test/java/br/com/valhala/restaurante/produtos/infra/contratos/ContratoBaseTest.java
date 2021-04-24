package br.com.valhala.restaurante.produtos.infra.contratos;

import br.com.valhala.restaurante.produtos.ProdutosApplication;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.consulta.ConsultaProdutoService;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.infra.produto.rest.controller.ProdutoRestController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest(classes = ProdutosApplication.class)
@ActiveProfiles("test")
public abstract class ContratoBaseTest {

    @Autowired
    ProdutoRestController produtoRestController;

    @MockBean
    ConsultaProdutoService consultaService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(produtoRestController);
        Produto produto = Produto
                .builder()
                .guid("1")
                .nome("Heineken 0 Alcool")
                .descricao("Cerveja Puro Malte sem Alcool")
                .dataCadastro(LocalDate.of(2021, 1, 1))
                .valor(new BigDecimal("5.90"))
                .fabricante("Heineken")
                .build();
        Mockito.when(consultaService.buscaPorGuid("1")).thenReturn(produto);
    }

}
