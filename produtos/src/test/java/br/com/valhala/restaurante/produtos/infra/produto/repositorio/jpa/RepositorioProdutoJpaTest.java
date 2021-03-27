package br.com.valhala.restaurante.produtos.infra.produto.repositorio.jpa;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloNaoEncontradoException;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.infra.test_containers.PostgresExtension;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@ExtendWith(PostgresExtension.class)
class RepositorioProdutoJpaTest {

    @Autowired
    RepositorioProdutoJpa repositorioProdutoJpa;

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    @ExpectedDataSet(value = "datasets/produto/produto-pos-inclusao.yml", ignoreCols = {"data_cadastro"})
    void deveIncluirProdutoComSucesso() {
        Produto produto = Produto
                .builder()
                .guid("7de0b077875c45bcb4b6fd19f7d46f31")
                .nome("Teste-2")
                .descricao("Teste-2")
                .valor(new BigDecimal("100.00"))
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .fabricante("Teste-2")
                .build();
        repositorioProdutoJpa.cria(produto);
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    @ExpectedDataSet(value = "datasets/produto/produto-pos-edicao.yml", ignoreCols = {"data_cadastro"})
    void deveEditarProdutoComSucesso() {
        Produto produto = Produto
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8765")
                .nome("Teste-edicao")
                .descricao("Teste-edicao")
                .valor(new BigDecimal("101.00"))
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .fabricante("Teste-edicao")
                .build();
        repositorioProdutoJpa.edita(produto);
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    void deveDispararModeloNaoEncontradoExceptionQuandoInformadoGuidNaoCadastradoNaEdicao() {
        Produto produto = Produto
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8999")
                .nome("Teste-edicao")
                .descricao("Teste-edicao")
                .valor(new BigDecimal("101.00"))
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .fabricante("Teste-edicao")
                .build();

        assertThrows(ModeloNaoEncontradoException.class, () -> repositorioProdutoJpa.edita(produto));
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    void deveExcluirProdutoComSucesso() {
        Produto produto = repositorioProdutoJpa.buscaPorGuid("8fa845b01c134bcf9de0de1ec2ff8765");
        assertThat(produto, is(notNullValue()));
        repositorioProdutoJpa.exclui(produto);
        assertThrows(ModeloNaoEncontradoException.class, () -> repositorioProdutoJpa.edita(produto));
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    void deveDispararModeloNaoEncontradoExceptionQuandoInformadoGuidNaoCadastradoNaExclusao() {
        Produto produto = Produto.builder().guid("8fa845b01c134bcf9de0de1ec2ff8999").build();
        assertThrows(ModeloNaoEncontradoException.class, () -> repositorioProdutoJpa.exclui(produto));
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", cleanAfter = true)
    void deveRetornaProdutoCadastradoComGuidInformado() {
        String guid = "8fa845b01c134bcf9de0de1ec2ff8765";
        Produto produto = repositorioProdutoJpa.buscaPorGuid(guid);
        assertThat(produto, is(notNullValue()));
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", cleanAfter = true)
    void deveDispararModeloNaoEncontradoExceptionQuandoInformadoGuidNaoCadastrado() {
        String guid = "8fa845b01c134bcf9de0de1ec2ff8784";
        assertThrows(ModeloNaoEncontradoException.class, () -> repositorioProdutoJpa.buscaPorGuid(guid));
    }

    @Test
    @DataSet(value = "datasets/produto/produto-lista.yml", cleanAfter = true)
    void deveListarTodosProdutosCadastrados() {
        Collection<Produto> produtos = repositorioProdutoJpa.lista();
        assertThat(produtos, is(not(empty())));
        assertThat(produtos, hasSize(2));
        List<String> guids = produtos.stream().map(p -> p.getGuid()).collect(Collectors.toList());
        assertThat(guids, hasItems("8fa845b01c134bcf9de0de1ec2ff8765", "7de0b077875c45bcb4b6fd19f7d46f31"));
    }

}